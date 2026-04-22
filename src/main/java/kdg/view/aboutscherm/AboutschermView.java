package kdg.view.aboutscherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class AboutschermView extends BorderPane {

    private Label titelLabel;
    private Label projectLabel;
    private Label makersLabel;
    private Label borjaLabel;
    private Label farokLabel;
    private Label schoolLabel;
    private Label versieLabel;
    private Button sluitKnop;

    public AboutschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        titelLabel   = maakLabel("BUNKER-17", "28px", true);
        projectLabel = maakLabel("Escape Room Project", "14px", false);
        makersLabel  = maakLabel("── Makers ──", "13px", false);
        borjaLabel   = maakLabel("Borja Cools  (model)", "13px", false);
        farokLabel   = maakLabel("Farok Bugri  (GUI/view)", "13px", false);
        schoolLabel  = maakLabel("Karel de Grote Hogeschool  ·  2025-2026", "12px", false);
        versieLabel  = maakLabel("Versie 1.0", "12px", false);

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

        VBox centrum = new VBox(12,
                titelLabel, projectLabel,
                new Label(""),
                makersLabel, borjaLabel, farokLabel,
                new Label(""),
                schoolLabel, versieLabel);
        centrum.setAlignment(Pos.CENTER);
        centrum.setPadding(new Insets(30, 24, 10, 24));
        centrum.setStyle("-fx-background-color: #1a1a1a;");

        // Stijl voor lege spacer-labels
        centrum.getChildren().stream()
                .filter(n -> n instanceof Label && ((Label) n).getText().isEmpty())
                .forEach(n -> ((Label) n).setMinHeight(8));

        this.setCenter(centrum);

        HBox onder = new HBox(sluitKnop);
        onder.setAlignment(Pos.CENTER_RIGHT);
        onder.setPadding(new Insets(8, 16, 16, 16));
        onder.setStyle("-fx-background-color: #1a1a1a;");
        this.setBottom(onder);
    }

    private Label maakLabel(String tekst, String fontSize, boolean bold) {
        Label label = new Label(tekst);
        String weight = bold ? "-fx-font-weight: bold;" : "";
        label.setStyle("-fx-text-fill: #00ff41; -fx-font-family: monospace; " +
                "-fx-font-size: " + fontSize + "; " + weight);
        return label;
    }

    // Package-private getter
    Button getSluitKnop() { return sluitKnop; }
}
