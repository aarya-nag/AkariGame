package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

import java.util.Random;
public class ControllerImpl implements ClassicMvcController{
    private final Model game;

    public ControllerImpl(Model game){
        if (game ==  null){
            throw new IllegalArgumentException();
        }
        this.game = game;
    }
    @Override
    public void clickNextPuzzle() {
        int current = game.getActivePuzzleIndex();
        if(current < game.getPuzzleLibrarySize() - 1){
            game.setActivePuzzleIndex(current + 1);
        }

    }

    @Override
    public void clickPrevPuzzle() {
        int current = game.getActivePuzzleIndex();
        if(current > 0){
            game.setActivePuzzleIndex(current - 1);
        }
    }

    @Override
    public void clickRandPuzzle() {
        int max = game.getPuzzleLibrarySize();
        Random random = new Random();
        int rand = random.nextInt(max);
        if(rand == game.getActivePuzzleIndex()){
            rand = random.nextInt(max);
        }
        game.setActivePuzzleIndex(rand);
    }

    @Override
    public void clickResetPuzzle() {
        game.resetPuzzle();

    }

    @Override
    public void clickCell(int r, int c) {
        if(game.getActivePuzzle().getCellType(r,c) == CellType.CORRIDOR){
            if (game.isLamp(r,c)){
                game.removeLamp(r,c);
            } else {
                game.addLamp(r,c);
            }

        }
    }
}
