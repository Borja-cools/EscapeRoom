package kdg.view.highscorescherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class HighscoreschermView extends BorderPane {

    private Label titelLabel;
    private ListView<String> scoreLijst;
    private Button sluitKnop;

    public HighscoreschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        titelLabel = new Label("HIGHSCORES");
        titelLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; " +
                "-fx-text-fill: #00ff41; -fx-font-family: monospace;");

        scoreLijst = new ListView<>();
        scoreLijst.setStyle("""
                -fx-control-inner-background: #2a2a2a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                """);

        sluitKnop = new Button("Sluiten");
        sluitKnop.setMinWidth(120);
        sluitKnop.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-cursor: hand;
                """);
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a1a1a;");

        HBox top = new HBox(titelLabel);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 16, 10, 16));
        top.setStyle("-fx-background-color: #1a1a1a;");
        this.setTop(top);

        this.setCenter(scoreLijst);
        BorderPane.setMargin(scoreLijst, new Insets(0, 16, 8, 16));

        HBox onder = new HBox(sluitKnop);
        onder.setAlignment(Pos.CENTER_RIGHT);
        onder.setPadding(new Insets(8, 16, 16, 16));
        onder.setStyle("-fx-background-color: #1a1a1a;");
        this.setBottom(onder);
    }

    // Package-private getters
    ListView<String> getScoreLijst() { return scoreLijst; }
    Button getSluitKnop()            { return sluitKnop; }
}
