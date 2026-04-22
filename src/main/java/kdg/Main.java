package kdg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kdg.model.Game;
import kdg.model.GameBuilder;
import kdg.view.startscherm.StartschermPresenter;
import kdg.view.startscherm.StartschermView;

/**
 * @author Farok
 * @version 2.0 20/04/2026
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Game game = GameBuilder.buildGame();

        StartschermView view = new StartschermView();
        new StartschermPresenter(game, view);

        primaryStage.setScene(new Scene(view, 800, 600));
        primaryStage.setTitle("Bunker-17");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}