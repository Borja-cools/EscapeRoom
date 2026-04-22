package kdg.view.startscherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class StartschermView extends BorderPane {

    // Controls
    private Label titelLabel;
    private Label naamLabel;
    private TextField naamVeld;
    private Button nieuwSpelKnop;
    private Button highscoresKnop;
    private Button helpKnop;
    private Button overOnsKnop;

    public StartschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        titelLabel = new Label("BUNKER-17");
        titelLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #00ff41; -fx-font-family: monospace;");

        naamLabel = new Label("Jouw naam:");
        naamLabel.setStyle("-fx-text-fill: #00ff41; -fx-font-family: monospace; -fx-font-size: 14px;");

        naamVeld = new TextField();
        naamVeld.setPromptText("Voer je naam in...");
        naamVeld.setMaxWidth(250);
        naamVeld.setStyle("-fx-background-color: #2a2a2a; -fx-text-fill: #00ff41; -fx-border-color: #00ff41; -fx-font-family: monospace;");

        nieuwSpelKnop  = maakKnop("Nieuw Spel");
        highscoresKnop = maakKnop("Highscores");
        helpKnop       = maakKnop("Help");
        overOnsKnop    = maakKnop("Over ons");
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a1a1a;");

        // Titel bovenaan
        BorderPane.setAlignment(titelLabel, Pos.CENTER);
        BorderPane.setMargin(titelLabel, new Insets(40, 0, 0, 0));
        this.setTop(titelLabel);

        // Naam + knoppen in het midden
        VBox centrum = new VBox(16, naamLabel, naamVeld, nieuwSpelKnop, highscoresKnop, helpKnop, overOnsKnop);
        centrum.setAlignment(Pos.CENTER);
        centrum.setPadding(new Insets(40));
        this.setCenter(centrum);
    }

    private Button maakKnop(String tekst) {
        Button knop = new Button(tekst);
        knop.setMinWidth(200);
        knop.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 14px;
                -fx-cursor: hand;
                """);
        return knop;
    }

    // Package-private getters voor de Presenter
    Label getTitelLabel()      { return titelLabel; }
    TextField getNaamVeld()    { return naamVeld; }
    Button getNieuwSpelKnop()  { return nieuwSpelKnop; }
    Button getHighscoresKnop() { return highscoresKnop; }
    Button getHelpKnop()       { return helpKnop; }
    Button getOverOnsKnop()    { return overOnsKnop; }
}
