package kdg.view.helpscherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class HelpschermView extends BorderPane {

    private TextArea spelregelsArea;
    private Button sluitKnop;

    public HelpschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        spelregelsArea = new TextArea();
        spelregelsArea.setEditable(false);
        spelregelsArea.setWrapText(true);
        spelregelsArea.setStyle("""
                -fx-control-inner-background: #1a1a1a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                """);

        sluitKnop = new Button("Sluiten");
        sluitKnop.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-cursor: hand;
                """);
        sluitKnop.setMinWidth(120);
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a1a1a;");
        this.setCenter(spelregelsArea);
        BorderPane.setMargin(spelregelsArea, new Insets(16, 16, 8, 16));

        HBox onder = new HBox(sluitKnop);
        onder.setAlignment(Pos.CENTER_RIGHT);
        onder.setPadding(new Insets(8, 16, 16, 16));
        onder.setStyle("-fx-background-color: #1a1a1a;");
        this.setBottom(onder);
    }

    // Package-private getters
    TextArea getSpelregelsArea() { return spelregelsArea; }
    Button getSluitKnop()        { return sluitKnop; }
}
