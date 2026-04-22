package kdg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Borja
 * @version 1.0 11/04/2026 13:36
 *
 */
public class GameBuilder {
    public static Game buildGame() {
        return buildGame("Speler");
    }

    public static Game buildGame(String spelerNaam) {
        // ----- Items ------
        Item zaklamp = new Item("Zaklamp_01", "Zaklamp", "Een oude zaklamp, heeft batterijen nodig om licht te geven.");
        Item batterij = new Item("Batterij_01", "Batterij", "Een AA batterij");
        Item notitie = new Item("Notitie_01", "Notitie", "Hier kan je een aanwijzing vinden.");
        Item keykard = new Item("Keykard_01", "Keykard", "Dit is de kaard van professor Cools, geeft toegang tot het laboratorium.");
        Item zekering = new Item("Zekering_01", "Zekering", "De zekering is afgeslagen. stroom kan worden aangezet door de zekering te draaien.");
        Item schroevendraaier = new Item("Schroevendraaier_01", "schroevendraaier", "Schroevendraaier om iets mee vast te zetten.");

        // ------ Rooms ------
        Room beginKamer = new Room("BeginKamer", "Beginkamer ");
        Room gang = new Room("Gang", "Een lange industriële gang. Meerdere deuren, de meeste afgesloten.");
        Room opslagruimte = new Room("Opslagruimte", "Volle rekken met materiaal. Ergens moet hier iets nuttigs zijn.");
        Room labo = new Room("Labo", "Computerschermen flikkeren. Een terminal vraagt een wachtwoord.");
        Room controlekamer = new Room("Controlekamer", "De centrale computer staat hier. Alles wat je nodig hebt om te ontsnappen.");
        Room eindkamer = new Room("Eindkamer", "Een zware bunkerdeur. Daglicht sijpelt door de kieren.");
        // ------ Items in Rooms ------
        beginKamer.addItem(zaklamp);
        beginKamer.addItem(batterij);

        opslagruimte.addItem(schroevendraaier);
        opslagruimte.addItem(zekering);

        labo.addItem(notitie);

        controlekamer.addItem(keykard);

        // ------ Doors ------
        Door startNaarGang = new Door(beginKamer, gang, false, null);
        Door gangNaarOpslag = new Door(gang, opslagruimte, true, zaklamp.getId());
        Door gangNaarLabo = new Door(gang, labo, true, keykard.getId());
        Door laboNaarControle = new Door(labo, controlekamer, false, null);
        Door controleNaarEinde =  new Door(controlekamer, eindkamer, false, null);

        // ------ Exits ------
        beginKamer.addExit(startNaarGang);

        gang.addExit(gangNaarOpslag);
        gang.addExit(gangNaarLabo);
        gang.addExit(startNaarGang);

        opslagruimte.addExit(gangNaarOpslag);

        labo.addExit(laboNaarControle);
        labo.addExit(gangNaarLabo);

        controlekamer.addExit(controleNaarEinde);
        controlekamer.addExit(laboNaarControle);

        // ------ Game ------
        Player player = new Player(spelerNaam);
        List<Room> rooms = List.of(beginKamer, gang, opslagruimte, controlekamer, labo, eindkamer);

        return new Game(player, rooms, beginKamer);
    }
}
