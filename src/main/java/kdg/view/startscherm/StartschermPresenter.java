package kdg.view.startscherm;

import javafx.scene.Scene;
import javafx.stage.Stage;
import kdg.model.Game;
import kdg.model.GameBuilder;
import kdg.model.Timer;
import kdg.view.helpscherm.HelpschermPresenter;
import kdg.view.helpscherm.HelpschermView;
import kdg.model.HighscoreManager;
import kdg.view.aboutscherm.AboutschermPresenter;
import kdg.view.aboutscherm.AboutschermView;
import kdg.view.highscorescherm.HighscoreschermPresenter;
import kdg.view.highscorescherm.HighscoreschermView;
import kdg.view.spelscherm.SpelschermPresenter;
import kdg.view.spelscherm.SpelschermView;

/**
 * @author Farok
 * @version 2.0 20/04/2026
 */
public class StartschermPresenter {

    private static final int SPELTIJD_SECONDEN = 600; // 10 minuten

    private final Game model;
    private final StartschermView view;

    public StartschermPresenter(Game model, StartschermView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers() {
        view.getNieuwSpelKnop().setOnAction(e -> {
            String naam = view.getNaamVeld().getText().trim();
            if (naam.isEmpty()) {
                view.getNaamVeld().setStyle(
                        "-fx-background-color: #2a2a2a; -fx-text-fill: #ff4444; " +
                        "-fx-border-color: #ff4444; -fx-font-family: monospace;");
                view.getNaamVeld().setPromptText("Naam is verplicht!");
                return;
            }

            Game nieuwSpel = GameBuilder.buildGame(naam);
            Timer timer = new Timer(SPELTIJD_SECONDEN);
            HighscoreManager hsManager = new HighscoreManager();

            SpelschermView spelView = new SpelschermView();
            new SpelschermPresenter(nieuwSpel, timer, hsManager, spelView);

            Stage stage = (Stage) view.getScene().getWindow();
            stage.setScene(new Scene(spelView, 1000, 650));
            stage.setTitle("Bunker-17 — " + naam);
        });

        view.getNaamVeld().setOnKeyTyped(e -> {
            // Reset rode rand zodra gebruiker begint te typen
            view.getNaamVeld().setStyle(
                    "-fx-background-color: #2a2a2a; -fx-text-fill: #00ff41; " +
                    "-fx-border-color: #00ff41; -fx-font-family: monospace;");
        });

        view.getHighscoresKnop().setOnAction(e -> {
            HighscoreschermView hsView = new HighscoreschermView();
            new HighscoreschermPresenter(new kdg.model.HighscoreManager(), hsView);
            Stage hsStage = new Stage();
            hsStage.setTitle("Highscores");
            hsStage.setScene(new Scene(hsView, 500, 400));
            hsStage.show();
        });

        view.getHelpKnop().setOnAction(e -> openHelpscherm());

        view.getOverOnsKnop().setOnAction(e -> {
            AboutschermView aboutView = new AboutschermView();
            new AboutschermPresenter(aboutView);
            Stage aboutStage = new Stage();
            aboutStage.setTitle("Over ons");
            aboutStage.setScene(new Scene(aboutView, 400, 300));
            aboutStage.show();
        });
    }

    private void openHelpscherm() {
        HelpschermView helpView = new HelpschermView();
        new HelpschermPresenter(helpView);

        Stage helpStage = new Stage();
        helpStage.setTitle("Spelregels");
        helpStage.setScene(new Scene(helpView, 600, 400));
        helpStage.show();
    }

    private void updateView() {
        // Startscherm heeft geen dynamische data bij opstarten
    }
}
