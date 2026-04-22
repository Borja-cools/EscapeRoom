package kdg.model;

import java.util.List;

/**
 * @author Borja
 * @version 1.0 28/03/2026 15:03
 *
 */
public class Game {
    // Attributen
    private Player player;
    private List<Room> rooms;
    private Room currentRoom;

    private GameState gameState; // huidige gameState
    private Room winRoom; // Welke room is de uitgang

    // Constructor
    public Game(Player player, List<Room> rooms, Room currentRoom) {
        // Checks
        if (player == null) {
            throw new IllegalArgumentException("player mag niet null zijn");
        }
        if (rooms == null) {
            throw new IllegalArgumentException("rooms mag niet null zijn");
        }
        if (currentRoom == null) {
            throw new IllegalArgumentException("currentRoom mag niet null zijn");
        }
        if (!rooms.contains(currentRoom)) {
            throw new IllegalArgumentException("currentRoom moet in rooms zitten");
        }

        this.player = player;
        this.rooms = rooms;
        this.currentRoom = currentRoom;
        this.gameState = GameState.MENU; // start altijd in menu
    }

    public Game(){}

    // Methodes voor het spel te delegeren (Gamestate)
    public void start(){
        if (gameState == GameState.MENU) {
            this.gameState = GameState.PLAYING;
        }
    }
    public void pause(){
        if (gameState == GameState.PLAYING) {
            this.gameState = GameState.PAUSED;
        }
    }
    public void resume(){
        if (gameState == GameState.PAUSED) {
            this.gameState = GameState.PLAYING;
        }
    }
    public void win(){
        if (gameState == GameState.PLAYING) {
            this.gameState = GameState.WON;
        }
    }
    public void lose(){
        if (gameState == GameState.PLAYING) {
            this.gameState = GameState.LOST;
        }
    }

    // Nog te implementeren
    public void stop(){}

    // Deur die de kamers verbindt gebruiken -> volgende room
    public boolean moveThroughDoor(Door door){
        if (gameState != GameState.PLAYING) return false;
        if (door == null) return false;
        if (door.isLocked()) return false;
        if (currentRoom.getExits().contains(door)) {
            this.currentRoom = door.getTargetRoom(currentRoom);
            return true;
        }
        return false;
    }

    // Item van de room oppakken
    public boolean pickupItem(Item item){
        // check
        if (gameState != GameState.PLAYING) return false;
        if(item == null) return false;
        // check of speler al item heeft
        if(player.hasItem(item)) return false;

        // Item uit room halen en inventory steken van speler
        if(currentRoom.getItems().contains(item)){
            player.pickUpItem(item);
            currentRoom.removeItem(item);
            return true;
        }
        return false;
    }

    // Item op een deur gebruiken
    public boolean useItemOnDoor(Item item, Door door){
        if (gameState != GameState.PLAYING) return false;
        // Check input geldig
        if(item == null || door == null) return false;
        // Check of player item heeft
        if(!player.hasItem(item)) return false;
        // proberen deur te openen en returnen of het gelukt is
        return door.unlock(item.getId());
    }

    // Getters voor attributen klasse
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
