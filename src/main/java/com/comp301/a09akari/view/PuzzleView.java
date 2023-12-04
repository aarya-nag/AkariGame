package com.comp301.a09akari.view;


import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class PuzzleView implements FXComponent {
  private final Model game;
  private final ClassicMvcController cont;


  public PuzzleView(Model game, ClassicMvcController cont) {
    this.game = game;
    this.cont = cont;
  }


  @Override
  public Parent render() {
    Puzzle curr = game.getActivePuzzle();
    GridPane grid = new GridPane();
    grid.setGridLinesVisible(true);
    grid.setHgap(5);
    grid.setVgap(5);
    grid.setPadding(new Insets(10, 10, 10, 10));


    Button box = new Button(" ");
    box.setPrefSize(50, 50);


    for (int r = 0; r < curr.getHeight(); r++) {
      for (int c = 0; c < curr.getWidth(); c++) {


        if (game.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
          box = new Button(" ");
          box.setPrefSize(50, 50);
          grid.add(box, r, c);
          final int x = r;
          final int y = c;
          box.setOnAction((ActionEvent ae) -> cont.clickCell(x, y));


          if (game.isLit(r, c)) {
            box.getStyleClass().add("litbox");
            box.setText("o");
          }
          if (game.isLamp(r, c)) {
            Image bulbImg = new Image("light-bulb.png");
            ImageView bulbImgView = new ImageView(bulbImg);
            bulbImgView.setFitHeight(30);
            bulbImgView.setFitWidth(20);
            box.setGraphic(bulbImgView);
          }
          if (game.isLamp(r, c) && game.isLampIllegal(r, c)) {
            box.getStyleClass().add("illegalbox");
            box.setText(" ");

          }
        } else if (game.getActivePuzzle().getCellType(r, c) == CellType.WALL) {
          box = new Button(" ");
          box.setPrefSize(50, 50);
          box.getStyleClass().add("wall");
          grid.add(box, r, c);


        } else if (game.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
          box = new Button(" ");
          box.setPrefSize(50, 50);
          int clue = game.getActivePuzzle().getClue(r, c);
          box.setText(Integer.toString(clue));
          box.getStyleClass().add("clue");
          grid.add(box, r, c);
        }
        box = new Button();
      }
    }
    return grid;
  }
}

