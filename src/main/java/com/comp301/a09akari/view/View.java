package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class View implements FXComponent {
  private final ClassicMvcController cont;
  private final Model game;

  public View(Model game, ClassicMvcController cont) {
    this.game = game;
    this.cont = cont;
  }

  @Override
  public Parent render() {
    BorderPane layout = new BorderPane();
    MessageView msgView = new MessageView(game, cont);
    layout.setBottom(msgView.render());

    PuzzleView puzView = new PuzzleView(game, cont);
    layout.setCenter(puzView.render());

    ControlView contView = new ControlView(game, cont);
    layout.setTop(contView.render());

    return layout;
  }
}
