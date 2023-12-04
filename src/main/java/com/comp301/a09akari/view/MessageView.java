package com.comp301.a09akari.view;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MessageView implements FXComponent {
    private final Model game;
    private final ClassicMvcController cont;

    public MessageView(Model game, ClassicMvcController cont) {
        this.game = game;
        this.cont = cont;
    }

    @Override
    public Parent render() {
        StackPane message = new StackPane();
        Label text;
        if (game.isSolved()) {
            text =
                    new Label(
                            "You Ate and Slayed Puzzle #"
                                    + (game.getActivePuzzleIndex() + 1)
                                    + "/"
                                    + game.getPuzzleLibrarySize());
            text.getStyleClass().add("ate");
        } else {
            text =
                    new Label(
                            "You failed horribly at Puzzle #"
                                    + (game.getActivePuzzleIndex() + 1)
                                    + "/"
                                    + game.getPuzzleLibrarySize());
            text.getStyleClass().add("failed");
        }

        message.getChildren().add(text);

        return message;
    }
}

