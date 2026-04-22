package kdg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Borja
 * @version 1.0 28/03/2026 13:26
 *
 */
public class Inventory {
    // Attributen klasse
    private final List<Item> items;

    // Constructor - initialiseren van Items lijst
    public Inventory() {
        this.items = new ArrayList<>();
    }

    // Item aan inventory toevoegen - technische actie
    public void addItem(Item item) {
        if (item == null) throw new IllegalArgumentException("item mag niet null zijn");
        this.items.add(item);
    }

    // Checken of speler Item heeft
    public boolean containsItem(Item item) {
        return this.items.contains(item);
    }

    // Item uit lijst verwijderen -> technische actie
    public boolean removeItem(Item item) {
        return this.items.remove(item);
    }

    // Item verwijderen uit lijst met ItemId
    public boolean removeItemById(String id) {
        return  this.items.removeIf(item -> item.getId().equals(id));
    }

    // Item teruggeven adv item ID.
    public Item getItemById(String id) {
        return this.items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    // Lijst met items teruggeven - new array aanmaken zodat array niet van buitenaf kan worden aangepast
    public List<Item> getItems() {
        return new ArrayList<>(this.items);
    }



}
