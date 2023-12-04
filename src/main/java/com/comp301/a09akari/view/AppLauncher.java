package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {

    stage.setTitle("Akari Slay");

    PuzzleLibrary library = new PuzzleLibraryImpl();
    Puzzle p1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle p2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle p3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle p4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle p5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    library.addPuzzle(p1);
    library.addPuzzle(p2);
    library.addPuzzle(p3);
    library.addPuzzle(p4);
    library.addPuzzle(p5);

    Model game = new ModelImpl(library); // give game a library

    ClassicMvcController cont =
        new ControllerImpl(game); // give controller a game which is a library

    View view = new View(game, cont);
    Scene scene = new Scene(view.render(), 600, 600);
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);

    game.addObserver(
        (Model g) -> {
          scene.setRoot(view.render());
          stage.sizeToScene();
        });

    stage.show();
  }
}
