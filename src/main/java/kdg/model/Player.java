package kdg.model;

/**
 * @author Borja
 * @version 1.0 28/03/2026 14:04
 *
 */
public class Player {
    // Attributen klasse
    private final String name;
    private final Inventory inventory;

    // Constructor
    public Player(String name) {
        // Check
        if (name == null) {
            throw new IllegalArgumentException("name mag niet null zijn");
        }

        this.name = name;
        this.inventory = new Inventory();
    }

    // player item laten oppakken -> spel actie
    public void pickUpItem(Item item) {
        this.inventory.addItem(item);
    }

    // Item weghalen uit inventory -> spel actie
    public void dropItem(Item item) {
        this.inventory.removeItem(item);
    }

    // nagaan of speler Item al heeft
    public boolean hasItem(Item item) {
        return inventory.containsItem(item);
    }

    // nagaan of speler Item heeft volgens ID
    public boolean hasItemById(String id) {
        return inventory.getItemById(id) != null;
    }

    // Getter
    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
