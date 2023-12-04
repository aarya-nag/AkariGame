package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  public int getWidth() {
    return board[0].length;
  }

  public int getHeight() {
    return board.length;
  }

  public CellType getCellType(int r, int c) {
    if (r > board.length || c > board[0].length || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (board[r][c] >= 0 && board[r][c] <= 4) {
      return CellType.CLUE;
    } else if (board[r][c] == 5) {
      return CellType.WALL;
    } else {
      return CellType.CORRIDOR;
    }
  }

  public int getClue(int r, int c) {
    if (r > board.length || c > board[0].length || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (board[r][c] > 4) {
      throw new IllegalArgumentException();
    }
    return board[r][c];
  }
}
