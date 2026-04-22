package kdg.view.spelscherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class SpelschermView extends BorderPane {

    // TOP
    private MenuBar menuBalk;
    private MenuItem pauzerenItem;
    private MenuItem stoppenItem;
    private MenuItem spelregelsItem;

    // LEFT
    private Label kamerNaamLabel;
    private TextArea kamerBeschrijvingArea;

    // CENTER
    private ListView<String> uitgangenLijst;
    private Button gaDoorDeurKnop;
    private ListView<String> kamerItemsLijst;
    private Button oppakkenKnop;

    // RIGHT
    private ListView<String> inventoryLijst;

    // BOTTOM
    private Label timerLabel;
    private Button gebruikItemKnop;

    public SpelschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        // Menu
        pauzerenItem  = new MenuItem("Pauzeren");
        stoppenItem   = new MenuItem("Stoppen");
        spelregelsItem = new MenuItem("Spelregels");

        Menu spelMenu = new Menu("Spel");
        spelMenu.getItems().addAll(pauzerenItem, stoppenItem);

        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(spelregelsItem);

        menuBalk = new MenuBar(spelMenu, helpMenu);
        menuBalk.setStyle("-fx-background-color: #2a2a2a; -fx-text-fill: #00ff41;");

        // LEFT
        kamerNaamLabel = new Label("...");
        kamerNaamLabel.setStyle("-fx-text-fill: #00ff41; -fx-font-family: monospace; -fx-font-size: 14px; -fx-font-weight: bold;");

        kamerBeschrijvingArea = new TextArea();
        kamerBeschrijvingArea.setEditable(false);
        kamerBeschrijvingArea.setWrapText(true);
        kamerBeschrijvingArea.setPrefSize(220, 200);
        kamerBeschrijvingArea.setStyle("""
                -fx-control-inner-background: #2a2a2a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-font-family: monospace;
                """);

        // CENTER
        uitgangenLijst   = maakLijst(180, 100);
        gaDoorDeurKnop   = maakKnop("Ga door deur");
        kamerItemsLijst  = maakLijst(180, 120);
        oppakkenKnop     = maakKnop("Oppakken");

        // RIGHT
        inventoryLijst = maakLijst(180, 250);

        // BOTTOM
        timerLabel = new Label("00:00");
        timerLabel.setStyle("-fx-text-fill: #00ff41; -fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold;");

        gebruikItemKnop = maakKnop("Gebruik item op deur");
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a1a1a;");

        // TOP
        this.setTop(menuBalk);

        // LEFT
        Label kamerTitelLabel = maakSectieLabel("Huidige kamer:");
        VBox links = new VBox(10, kamerTitelLabel, kamerNaamLabel, kamerBeschrijvingArea);
        links.setPadding(new Insets(16));
        links.setPrefWidth(240);
        links.setStyle("-fx-background-color: #1a1a1a;");
        this.setLeft(links);

        // CENTER
        Label uitgangenLabel = maakSectieLabel("Uitgangen:");
        Label kamerItemsLabel = maakSectieLabel("Items in kamer:");
        VBox centrum = new VBox(10, uitgangenLabel, uitgangenLijst, gaDoorDeurKnop, kamerItemsLabel, kamerItemsLijst, oppakkenKnop);
        centrum.setPadding(new Insets(16));
        centrum.setAlignment(Pos.TOP_CENTER);
        centrum.setStyle("-fx-background-color: #1a1a1a;");
        this.setCenter(centrum);

        // RIGHT
        Label inventoryTitelLabel = maakSectieLabel("Inventory:");
        VBox rechts = new VBox(10, inventoryTitelLabel, inventoryLijst);
        rechts.setPadding(new Insets(16));
        rechts.setPrefWidth(220);
        rechts.setStyle("-fx-background-color: #1a1a1a;");
        this.setRight(rechts);

        // BOTTOM
        Label tijerTitelLabel = maakSectieLabel("Tijd:");
        HBox onder = new HBox(12, tijerTitelLabel, timerLabel, gebruikItemKnop);
        onder.setPadding(new Insets(12, 16, 12, 16));
        onder.setAlignment(Pos.CENTER_LEFT);
        onder.setStyle("-fx-background-color: #2a2a2a; -fx-border-color: #00ff41; -fx-border-width: 1 0 0 0;");
        this.setBottom(onder);
    }

    private Label maakSectieLabel(String tekst) {
        Label label = new Label(tekst);
        label.setStyle("-fx-text-fill: #aaaaaa; -fx-font-family: monospace; -fx-font-size: 12px;");
        return label;
    }

    private Button maakKnop(String tekst) {
        Button knop = new Button(tekst);
        knop.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-cursor: hand;
                """);
        return knop;
    }

    private ListView<String> maakLijst(double breedte, double hoogte) {
        ListView<String> lijst = new ListView<>();
        lijst.setPrefSize(breedte, hoogte);
        lijst.setStyle("""
                -fx-control-inner-background: #2a2a2a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-font-family: monospace;
                """);
        return lijst;
    }

    // Package-private getters voor Presenter
    MenuItem getPauzerenItem()      { return pauzerenItem; }
    MenuItem getStoppenItem()       { return stoppenItem; }
    MenuItem getSpelregelsItem()    { return spelregelsItem; }
    Label getKamerNaamLabel()       { return kamerNaamLabel; }
    TextArea getKamerBeschrijvingArea() { return kamerBeschrijvingArea; }
    ListView<String> getUitgangenLijst()  { return uitgangenLijst; }
    ListView<String> getKamerItemsLijst() { return kamerItemsLijst; }
    Button getGaDoorDeurKnop()      { return gaDoorDeurKnop; }
    Button getOppakkenKnop()        { return oppakkenKnop; }
    ListView<String> getInventoryLijst()  { return inventoryLijst; }
    Label getTimerLabel()           { return timerLabel; }
    Button getGebruikItemKnop()     { return gebruikItemKnop; }
}
