package kdg.view.highscorescherm;

import javafx.stage.Stage;
import kdg.model.HighscoreManager;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class HighscoreschermPresenter {

    private final HighscoreManager manager;
    private final HighscoreschermView view;

    public HighscoreschermPresenter(HighscoreManager manager, HighscoreschermView view) {
        this.manager = manager;
        this.view    = view;
        this.updateView();
        this.addEventHandlers();
    }

    private void updateView() {
        view.getScoreLijst().getItems().clear();

        var scores = manager.getScores();
        if (scores.isEmpty()) {
            view.getScoreLijst().getItems().add("Nog geen scores opgeslagen.");
            return;
        }

        for (int i = 0; i < scores.size(); i++) {
            HighscoreManager.Highscore score = scores.get(i);
            view.getScoreLijst().getItems().add(
                    String.format("%2d.  %-20s  %s", i + 1, score.getSpelerNaam(), score.getTijdAlsTekst())
            );
        }
    }

    private void addEventHandlers() {
        view.getSluitKnop().setOnAction(e -> {
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
        });
    }
}
