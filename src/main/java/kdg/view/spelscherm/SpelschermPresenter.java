package kdg.view.spelscherm;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;
import kdg.model.Door;
import kdg.model.Game;
import kdg.model.Item;
import kdg.model.Timer;
import kdg.model.HighscoreManager;
import kdg.view.helpscherm.HelpschermPresenter;
import kdg.view.helpscherm.HelpschermView;
import kdg.view.highscorescherm.HighscoreschermPresenter;
import kdg.view.highscorescherm.HighscoreschermView;

import java.util.List;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class SpelschermPresenter {

    private static final String EINDKAMER_NAAM = "Eindkamer";

    private final Game game;
    private final Timer timer;
    private final HighscoreManager hsManager;
    private final SpelschermView view;
    private Timeline timerTimeline;

    public SpelschermPresenter(Game game, Timer timer, HighscoreManager hsManager, SpelschermView view) {
        this.game      = game;
        this.timer     = timer;
        this.hsManager = hsManager;
        this.view      = view;
        this.addEventHandlers();
        this.updateView();
        this.startTimer();
    }

    private void addEventHandlers() {
        // Oppakken
        view.getOppakkenKnop().setOnAction(e -> {
            int index = view.getKamerItemsLijst().getSelectionModel().getSelectedIndex();
            if (index < 0) {
                toonMelding("Selecteer eerst een item om op te pakken.");
                return;
            }
            List<Item> items = game.getCurrentRoom().getItems();
            if (index >= items.size()) return;

            Item item = items.get(index);
            boolean gelukt = game.pickupItem(item);
            if (gelukt) {
                toonInfo("Je pakt op: " + item.getName());
            } else {
                toonMelding("Je hebt dit item al.");
            }
            updateView();
        });

        // Ga door deur — knop
        view.getGaDoorDeurKnop().setOnAction(e -> beweegDoorGeselecteerdeDeur());

        // Ga door deur — dubbelklik op uitgangenLijst
        view.getUitgangenLijst().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                beweegDoorGeselecteerdeDeur();
            }
        });

        // Gebruik item op deur
        view.getGebruikItemKnop().setOnAction(e -> {
            int itemIndex = view.getInventoryLijst().getSelectionModel().getSelectedIndex();
            int deurIndex = view.getUitgangenLijst().getSelectionModel().getSelectedIndex();

            if (itemIndex < 0) {
                toonMelding("Selecteer een item uit je inventory.");
                return;
            }
            if (deurIndex < 0) {
                toonMelding("Selecteer een uitgang.");
                return;
            }

            List<Item> inventory = game.getPlayer().getInventory().getItems();
            List<Door> deuren    = game.getCurrentRoom().getExits();

            if (itemIndex >= inventory.size() || deurIndex >= deuren.size()) return;

            Item item = inventory.get(itemIndex);
            Door deur = deuren.get(deurIndex);

            boolean gelukt = game.useItemOnDoor(item, deur);
            if (gelukt) {
                toonInfo("Deur geopend met: " + item.getName());
            } else {
                toonMelding("Dit item opent deze deur niet.");
            }
            updateView();
        });

        // Menu: Pauzeren
        view.getPauzerenItem().setOnAction(e -> {
            timer.pauze();
            timerTimeline.pause();
            game.pause();
        });

        // Menu: Stoppen
        view.getStoppenItem().setOnAction(e -> {
            timer.pauze();
            timerTimeline.stop();
            game.stop();
            // TODO: terug naar startscherm
        });

        // Menu: Spelregels
        view.getSpelregelsItem().setOnAction(e -> {
            HelpschermView helpView = new HelpschermView();
            new HelpschermPresenter(helpView);
            Stage helpStage = new Stage();
            helpStage.setTitle("Spelregels");
            helpStage.setScene(new Scene(helpView, 600, 400));
            helpStage.show();
        });
    }

    private void beweegDoorGeselecteerdeDeur() {
        int index = view.getUitgangenLijst().getSelectionModel().getSelectedIndex();
        if (index < 0) {
            toonMelding("Selecteer eerst een uitgang.");
            return;
        }
        List<Door> deuren = game.getCurrentRoom().getExits();
        if (index >= deuren.size()) return;

        Door deur = deuren.get(index);
        if (deur.isLocked()) {
            toonMelding("Deze deur is op slot. Gebruik het juiste item.");
            return;
        }
        game.moveThroughDoor(deur);
        updateView();
        controleerWinConditie();
    }

    private void controleerWinConditie() {
        if (!game.getCurrentRoom().getName().equals(EINDKAMER_NAAM)) return;

        timerTimeline.stop();
        timer.pauze();
        game.win();

        int verstreken = timer.getVerstrekenSeconden();
        hsManager.voegScoreToe(game.getPlayer().getName(), verstreken);

        int min = verstreken / 60;
        int sec = verstreken % 60;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ontsnapt!");
        alert.setHeaderText(null);
        alert.setContentText(String.format(
                "Gefeliciteerd! Je bent ontsnapt in %d minuten en %d seconden!", min, sec));
        alert.showAndWait();

        HighscoreschermView hsView = new HighscoreschermView();
        new HighscoreschermPresenter(hsManager, hsView);
        Stage hsStage = new Stage();
        hsStage.setTitle("Highscores");
        hsStage.setScene(new Scene(hsView, 500, 400));
        hsStage.show();
    }

    private void startTimer() {
        timer.start();
        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timer.tick();
            updateTimerLabel();
            if (timer.isAfgelopen()) {
                timerTimeline.stop();
                game.lose();
                toonMelding("Tijd is om! Je bent gevangen.");
            }
        }));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }

    private void updateTimerLabel() {
        int seconden = timer.getResterendeSeconden();
        int min = seconden / 60;
        int sec = seconden % 60;
        String tekst = String.format("%02d:%02d", min, sec);
        view.getTimerLabel().setText(tekst);

        // Rood als minder dan 60 seconden over
        if (seconden < 60) {
            view.getTimerLabel().setStyle(
                    "-fx-text-fill: #ff4444; -fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold;");
        } else {
            view.getTimerLabel().setStyle(
                    "-fx-text-fill: #00ff41; -fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold;");
        }
    }

    void updateView() {
        // Kamernaam en beschrijving
        view.getKamerNaamLabel().setText(game.getCurrentRoom().getName());
        view.getKamerBeschrijvingArea().setText(game.getCurrentRoom().getDescription());

        // Uitgangen
        view.getUitgangenLijst().getItems().clear();
        for (Door deur : game.getCurrentRoom().getExits()) {
            String slot = deur.isLocked() ? " [op slot]" : " [open]";
            String doelKamer = deur.getTargetRoom(game.getCurrentRoom()).getName();
            view.getUitgangenLijst().getItems().add("→ " + doelKamer + slot);
        }

        // Items in kamer
        view.getKamerItemsLijst().getItems().clear();
        for (Item item : game.getCurrentRoom().getItems()) {
            view.getKamerItemsLijst().getItems().add(item.getName());
        }

        // Inventory
        view.getInventoryLijst().getItems().clear();
        for (Item item : game.getPlayer().getInventory().getItems()) {
            view.getInventoryLijst().getItems().add(item.getName());
        }

        // Timer initieel vullen
        updateTimerLabel();
    }

    private void toonMelding(String bericht) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Let op");
        alert.setHeaderText(null);
        alert.setContentText(bericht);
        alert.showAndWait();
    }

    private void toonInfo(String bericht) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actie");
        alert.setHeaderText(null);
        alert.setContentText(bericht);
        alert.showAndWait();
    }
}
