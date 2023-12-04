package com.comp301.a09akari.model;

import java.util.*;

public class ModelImpl implements Model{
    private final List<ModelObserver> obs = new ArrayList<>();
    private PuzzleLibrary lib = new PuzzleLibraryImpl();
    private int index;
    private boolean[][] board;
    private Puzzle actPuz;
    public ModelImpl(PuzzleLibrary library) {
        this.lib = library;
        this.index = 0;
        Puzzle puz = library.getPuzzle(index);
        this.board = new boolean[puz.getHeight()][puz.getWidth()];
        this.actPuz = puz;
    }
@Override
    public void addLamp(int r, int c) {
    if (r > actPuz.getHeight() || c > actPuz.getWidth() || r < 0 || c < 0){
      throw new IndexOutOfBoundsException();
    }
    if(actPuz.getCellType(r,c) != CellType.CORRIDOR){
        throw new IllegalArgumentException();
    }
    board[r][c] = true;
    for(ModelObserver obs:obs){
        obs.update(this);
    }
    }

    @Override
    public void removeLamp(int r, int c) {
        if (r > actPuz.getHeight() || c > actPuz.getWidth() || r < 0 || c < 0){
            throw new IndexOutOfBoundsException();
        }
        if(actPuz.getCellType(r,c) != CellType.CORRIDOR){
            throw new IllegalArgumentException();
        }
        board[r][c] = false;
        for(ModelObserver obs:obs){
            obs.update(this);
        }
    }

    @Override
    public boolean isLit(int r, int c) {
        if (r > actPuz.getHeight() || c > actPuz.getWidth() || r < 0 || c < 0){
            throw new IndexOutOfBoundsException();
        }
        if(actPuz.getCellType(r,c) != CellType.CORRIDOR){
            throw new IllegalArgumentException();
        }
        return(isLamp(r,c) || isLitter(r,c));
    }

    private boolean isLitter(int r,int c){
        rcCheck(r,c);
        int x = r;
        int y = c;
        while ((x <= actPuz.getHeight() - 1)
                && (actPuz.getCellType(x, y) == CellType.CORRIDOR))
        {
            if (x == r) {
                x = x + 1;
                continue;
            }
            if (isLamp(x, y)) {
                return true;
            }
            x = x + 1;
        }

        x = r;
        y = c;
        while ((y <= actPuz.getWidth() - 1)
                && (actPuz.getCellType(x, y) == CellType.CORRIDOR))
        {
            if (y == c) {
                y = y + 1;
                continue;
            }
            if (isLamp(x, y)) {
                return true;
            }
            y = y + 1;
        }

        x = r;
        y = c;
        while ((x >= 0) && (actPuz.getCellType(x, y) == CellType.CORRIDOR)) {
            if (x == r) {
                x = x - 1;
                continue;
            }
            if (isLamp(x, y)) {
                return true;
            }
            x = x - 1;
        }

        x = r;
        y = c;
        while ((y >= 0) && (actPuz.getCellType(x, y) == CellType.CORRIDOR)) {
            if (y == c) {
                y = y - 1;
                continue;
            }
            if (isLamp(x, y)) {
                return true;
            }
            y = y - 1;
        }

        return false;
    }

    @Override
    public boolean isLamp(int r, int c) {
        rcCheck(r,c);
        return board[r][c];

    }

    @Override
    public boolean isLampIllegal(int r, int c) {
        if(!isLamp(r,c)){
            throw new IllegalArgumentException();
        }
        return isLitter(r,c);
    }

    @Override
    public Puzzle getActivePuzzle() {
        return actPuz;
    }

    @Override
    public int getActivePuzzleIndex() {
        return index;
    }

    @Override
    public void setActivePuzzleIndex(int index) {
        if(index < 0){
            throw new IndexOutOfBoundsException();
        }
        this.index = index;
        actPuz = lib.getPuzzle(index);
        board = new boolean[actPuz.getHeight()][actPuz.getWidth()];
        for(ModelObserver obs:obs){
            obs.update(this);
        }
    }

    @Override
    public int getPuzzleLibrarySize() {
        return lib.size();
    }

    @Override
    public void resetPuzzle() {
        board = new boolean[actPuz.getHeight()][actPuz.getWidth()];
        for(ModelObserver obs:obs){
            obs.update(this);
        }
    }

    @Override
    public boolean isSolved() {
        boolean solved = true;
        for (int i = 0; i < actPuz.getHeight(); i++) {
            for (int j = 0; j < actPuz.getWidth(); j++) {
                if (actPuz.getCellType(i, j) == CellType.CLUE) {
                    if (!isClueSatisfied(i, j)) {
                        solved = false;
                    }
                } else if (actPuz.getCellType(i, j) == CellType.CORRIDOR) {
                    if (isLamp(i, j)) {
                        if (isLampIllegal(i, j)) {
                            solved = false;
                        }
                    }
                    if (!isLit(i, j)) {
                        solved = false;
                    }
                }
            }
        }
        return solved;
    }

    @Override
    public boolean isClueSatisfied(int r, int c) {
        if(r>actPuz.getHeight() || c> actPuz.getWidth()){
            throw new IndexOutOfBoundsException();
        }
        if(actPuz.getCellType(r,c) != CellType.CLUE){
            throw new IllegalArgumentException();
        }
        int sum = 0;
        int clue_val = actPuz.getClue(r, c);
        try {
            if (isLamp(r + 1, c)) {
                sum++;
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
        }
        try {
            if (isLamp(r - 1, c)) {
                sum++;
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
        }
        try {
            if (isLamp(r, c + 1)) {
                sum++;
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
        }
        try {
            if (isLamp(r, c - 1)) {
                sum++;
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
        }

        return clue_val == sum;
    }

    @Override
    public void addObserver(ModelObserver observer) {
        obs.add(observer);
    }

    @Override
    public void removeObserver(ModelObserver observer) {
        obs.remove(observer);
    }

    public void rcCheck(int r, int c){
        if (r > actPuz.getHeight() || c > actPuz.getWidth() || r < 0 || c < 0){
            throw new IndexOutOfBoundsException();
        }
        if(actPuz.getCellType(r,c) != CellType.CORRIDOR){
            throw new IllegalArgumentException();
        }
    }
}
