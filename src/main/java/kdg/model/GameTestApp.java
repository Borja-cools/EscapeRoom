package kdg.model;

/**
 * @author Borja
 * @version 1.0 29/03/2026 16:51
 *
 */
import java.util.ArrayList;
import java.util.List;

public class GameTestApp {
    public static void main(String[] args) {
        // ============ Test klasse GameBuilder =================
        testPickupItem();
        testMoveThroughDoor();
        UseItemOnDoor();

    }

    // =====================
    // pickupItem
    // =====================
    static void testPickupItem() {
        System.out.println("\n=== Test: pickupItem ===");

        // --- Test 1: happy path ---
        // Arrange
        Game game = GameBuilder.buildGame();
        game.start();
        Item zaklamp = game.getCurrentRoom().getItems().get(0);
        // Act
        boolean result1 = game.pickupItem(zaklamp);
        // Assert
        System.out.println(result1 ? "✅ zaklamp opgepakt" : "❌ zaklamp niet opgepakt");

        // --- Test 2: item al in inventory ---
        // Arrange - zaklamp al opgepakt in vorige test
        // Act
        boolean result2 = game.pickupItem(zaklamp);
        // Assert
        System.out.println(!result2 ? "✅ duplicaat correct geweigerd" : "❌ duplicaat had moeten falen");

        // --- Test 3: item niet meer in room na pickup ---
        // Arrange - zaklamp al opgepakt
        // Act
        boolean result3 = game.getCurrentRoom().getItems().contains(zaklamp);
        // Assert
        System.out.println(!result3 ? "✅ zaklamp niet meer in room" : "❌ zaklamp nog in room");

        // --- Test 4: null ---
        // Arrange
        Game game2 = GameBuilder.buildGame();
        game2.start();
        // Act
        boolean result4 = game2.pickupItem(null);
        // Assert
        System.out.println(!result4 ? "✅ null correct geweigerd" : "❌ null had moeten falen");

        // --- Test 5: item niet in huidige room ---
        // Arrange
        Game game3 = GameBuilder.buildGame();
        game3.start();
        Item foutItem = new Item("fout_01", "Fout Item", "Dit item zit niet in de room");
        // Act
        boolean result5 = game3.pickupItem(foutItem);
        // Assert
        System.out.println(!result5 ? "✅ item niet in room correct geweigerd" : "❌ had moeten falen");
    }

    // =====================
    // moveThroughDoor
    // =====================
    static void testMoveThroughDoor() {
        System.out.println("\n=== Test: moveThroughDoor ===");

        // --- Test 1: happy path open deur ---
        // Arrange
        Game game = GameBuilder.buildGame();
        game.start();
        Door startNaarGang = game.getCurrentRoom().getExits().get(0);
        // Act
        boolean result1 = game.moveThroughDoor(startNaarGang);
        // Assert
        System.out.println(result1 ? "✅ naar gang bewogen" : "❌ had moeten werken");
        System.out.println(game.getCurrentRoom().getName().equals("Gang") ? "✅ in gang" : "❌ niet in gang");

        // --- Test 2: bidirectioneel terugkeren ---
        // Arrange - al in gang na vorige test
        // Act
        boolean result2 = game.moveThroughDoor(startNaarGang);
        // Assert
        System.out.println(result2 ? "✅ terugkeer gelukt" : "❌ terugkeer had moeten werken");
        System.out.println(game.getCurrentRoom().getName().equals("BeginKamer") ? "✅ terug in beginkamer" : "❌ niet in beginkamer");

        // --- Test 3: null ---
        // Arrange
        Game game2 = GameBuilder.buildGame();
        game2.start();
        // Act
        boolean result3 = game2.moveThroughDoor(null);
        // Assert
        System.out.println(!result3 ? "✅ null correct geweigerd" : "❌ null had moeten falen");

        // --- Test 4: gesloten deur ---
        // Arrange
        Game game3 = GameBuilder.buildGame();
        game3.start();
        Door startNaarGang3 = game3.getCurrentRoom().getExits().get(0);
        game3.moveThroughDoor(startNaarGang3); // naar gang
        Door gangNaarOpslag = game3.getCurrentRoom().getExits().get(0); // gesloten
        // Act
        boolean result4 = game3.moveThroughDoor(gangNaarOpslag);
        // Assert
        System.out.println(!result4 ? "✅ gesloten deur correct geweigerd" : "❌ gesloten deur had moeten falen");

        // --- Test 5: deur hoort niet bij huidige kamer ---
        // Arrange
        Game game4 = GameBuilder.buildGame();
        game4.start();
        Door startNaarGang4 = game4.getCurrentRoom().getExits().get(0);
        game4.moveThroughDoor(startNaarGang4); // naar gang
        Door gangNaarLabo = game4.getCurrentRoom().getExits().get(1); // deur van gang, niet van beginkamer
        game4.moveThroughDoor(startNaarGang4); // terug naar beginkamer
        // Act
        boolean result5 = game4.moveThroughDoor(gangNaarLabo);
        // Assert
        System.out.println(!result5 ? "✅ verkeerde deur correct geweigerd" : "❌ had moeten falen");
    }

    // =====================
    // useItemOnDoor
    // =====================
    static void UseItemOnDoor() {
        System.out.println("\n=== Test: useItemOnDoor ===");

        // --- Test 1: null item ---
        // Arrange
        Game game = GameBuilder.buildGame();
        game.start();
        Door startNaarGang = game.getCurrentRoom().getExits().get(0);
        game.moveThroughDoor(startNaarGang);
        Door gangNaarOpslag = game.getCurrentRoom().getExits().get(0);
        // Act
        boolean result1 = game.useItemOnDoor(null, gangNaarOpslag);
        // Assert
        System.out.println(!result1 ? "✅ null item correct geweigerd" : "❌ null had moeten falen");

        // --- Test 2: null deur ---
        // Arrange
        Game game2 = GameBuilder.buildGame();
        game2.start();
        Item zaklamp2 = game2.getCurrentRoom().getItems().get(0);
        game2.pickupItem(zaklamp2);
        // Act
        boolean result2 = game2.useItemOnDoor(zaklamp2, null);
        // Assert
        System.out.println(!result2 ? "✅ null deur correct geweigerd" : "❌ null had moeten falen");

        // --- Test 3: fout item op gesloten deur ---
        // Arrange
        Game game3 = GameBuilder.buildGame();
        game3.start();
        Item batterij = game3.getCurrentRoom().getItems().get(1);
        game3.pickupItem(batterij);
        Door startNaarGang3 = game3.getCurrentRoom().getExits().get(0);
        game3.moveThroughDoor(startNaarGang3);
        Door gangNaarOpslag3 = game3.getCurrentRoom().getExits().get(0);
        // Act
        boolean result3 = game3.useItemOnDoor(batterij, gangNaarOpslag3);
        // Assert
        System.out.println(!result3 ? "✅ fout item correct geweigerd" : "❌ fout item had moeten falen");

        // --- Test 4: juist item op gesloten deur ---
        // Arrange
        Game game4 = GameBuilder.buildGame();
        game4.start();
        Item zaklamp4 = game4.getCurrentRoom().getItems().get(0);
        game4.pickupItem(zaklamp4);
        Door startNaarGang4 = game4.getCurrentRoom().getExits().get(0);
        game4.moveThroughDoor(startNaarGang4);
        Door gangNaarOpslag4 = game4.getCurrentRoom().getExits().get(0);
        // Act
        boolean result4 = game4.useItemOnDoor(zaklamp4, gangNaarOpslag4);
        // Assert
        System.out.println(result4 ? "✅ deur correct geopend" : "❌ deur had moeten openen");

        // --- Test 5: item op al open deur ---
        // Arrange - deur al open na vorige test
        // Act
        boolean result5 = game4.useItemOnDoor(zaklamp4, gangNaarOpslag4);
        // Assert
        System.out.println(!result5 ? "✅ al open deur correct geweigerd" : "❌ had moeten falen");

        // --- Test 6: item niet in inventory ---
        // Arrange
        Game game5 = GameBuilder.buildGame();
        game5.start();
        Door startNaarGang5 = game5.getCurrentRoom().getExits().get(0);
        game5.moveThroughDoor(startNaarGang5);
        Door gangNaarOpslag5 = game5.getCurrentRoom().getExits().get(0);
        Item foutItem = new Item("fout_01", "Fout Item", "Niet in inventory");
        // Act
        boolean result6 = game5.useItemOnDoor(foutItem, gangNaarOpslag5);
        // Assert
        System.out.println(!result6 ? "✅ item niet in inventory correct geweigerd" : "❌ had moeten falen");
    }
}