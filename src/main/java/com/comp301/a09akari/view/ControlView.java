package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class ControlView implements FXComponent {
  private final Model model;
  private final ClassicMvcController cont;

  public ControlView(Model model, ClassicMvcController cont) {
    this.model = model;
    this.cont = cont;
  }

  @Override
  public Parent render() {
    HBox controls = new HBox();

    Button randPuz = new Button("Random Puzzle af");
    randPuz.setStyle("-fx-font-size:10");
    randPuz.setOnAction((ActionEvent ae) -> cont.clickRandPuzzle());

    Button resetPuz = new Button("Clear that girly");
    resetPuz.setStyle("-fx-font-size:10");
    resetPuz.setOnAction((ActionEvent event) -> cont.clickResetPuzzle());

    Button newPuz = new Button("Gimme Next");
    newPuz.setStyle("-fx-font-size:10");
    newPuz.setOnAction((ActionEvent event) -> cont.clickNextPuzzle());

    Button last = new Button("Gimme Previous");
    last.setStyle("-fx-font-size:10");
    last.setOnAction((ActionEvent event) -> cont.clickPrevPuzzle());

    Button done = new Button("Want to check king?");
    done.setStyle("-fx-font-size:10");
    done.setOnAction(
        (ActionEvent event) -> {
          if (model.isSolved()) {
            done.getStyleClass().add("donebutt");
            done.setText("You ate that");
          } else {
            done.getStyleClass().add("notdonebutt");
            done.setText("You did not eat");
          }
        });

    controls.getStyleClass().add("conts");
    controls.getChildren().addAll(resetPuz, randPuz, last, newPuz, done);

    return controls;
  }
}
