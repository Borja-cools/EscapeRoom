# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean compile

# Run all tests
./mvnw test

# Run the manual test app (GameTestApp)
./mvnw exec:java -Dexec.mainClass="kdg.model.GameTestApp"

# Run JavaFX app (not yet functional — Main.java is commented out)
./mvnw clean javafx:run
```

On Windows use `mvnw.cmd` instead of `./mvnw`.

## Architecture

This is a Java 21 + JavaFX 21 escape room game following the **Model-View-Presenter (MVP)** pattern.

### Model (`kdg.model`)

The domain layer. Key relationships:

- `Game` is the main facade — it orchestrates all player actions (`pickupItem`, `moveThroughDoor`, `useItemOnDoor`). All game logic goes through `Game`.
- `GameBuilder` constructs a fully wired `Game` instance with rooms, doors, and items pre-configured. Use it in tests and manual runs.
- `Room` holds a list of `Item`s and `Door`s (exits). Rooms are bidirectionally connected through `Door`.
- `Door` connects two rooms bidirectionally. It can be locked and requires a specific `Item` ID to unlock. `getTargetRoom(currentRoom)` returns the other side.
- `Player` delegates inventory management to `Inventory`. Items are identified and matched by their string `id` field.
- `Interactable` is an interface (`interact(Player)`, `inspect()`) intended for interactive objects — not yet implemented on any class.

### View (`kdg.view`)

Stub MVP scaffolding only — `ApplicatieNaamView` (extends `BorderPane`) and `ApplicatieNaamPresenter` are empty templates. `Main.java` is fully commented out. The JavaFX UI is not yet built.

### Testing

No JUnit test classes exist yet. `GameTestApp` is a manual test runner in `src/main/java` that uses `GameBuilder` and prints ✅/❌ to stdout. JUnit 5 is configured in `pom.xml` and ready to use — test classes go in `src/test/java/kdg/`.

## Conventions

- Comments and variable names are in **Dutch** (e.g., `beginKamer`, `zaklamp`, validation messages like `"name mag niet null zijn"`).
- Getters in `Game`, `Player`, `Item`, and `Inventory` are **package-private** (no `public`) — intentional for encapsulation within the `kdg.model` package.
- Defensive copies are returned from `getItems()` and `getExits()` to prevent external mutation.
- `Room.equals()` is based on `name` only; `Door.equals()` is bidirectional (fromRoom/toRoom order-independent).

# Bunker-17 Escape Room — Project Instructies

## Project Context
Java programmeerproject voor Karel de Grote Hogeschool (academiejaar 2025-2026).
Twee studenten: Borja Cools (model) en Farok Bugri(GUI/view).
GitHub: https://github.com/Borja-cools/EscapeRoom.git

## Architectuur: Model-View-Presenter (MVP)
Dit project gebruikt STRIKT het MVP-patroon zoals geleerd in de lessen:
- **Model** (`kdg.model`): pure Java logica, GEEN JavaFX imports
- **View** (`kdg.view.schermnaam`): enkel UI-code, geen logica
- **Presenter** (`kdg.view.schermnaam`): synchroniseert model ↔ view

Per scherm: aparte subpackage met `XxxView.java` en `XxxPresenter.java`

## Package structuur
kdg/
├── Main.java
├── model/
│   ├── Game.java
│   ├── Player.java
│   ├── Room.java
│   ├── Door.java
│   ├── Item.java
│   ├── Inventory.java
│   ├── Interactable.java
│   ├── Timer.java          (TODO)
│   ├── Puzzle.java         (TODO)
│   └── HighscoreManager.java (TODO)
└── view/
├── startscherm/
│   ├── StartschermView.java
│   └── StartschermPresenter.java
├── spelscherm/
├── helpscherm/
├── highscorescherm/
└── aboutscherm/
## Evaluatievereisten (niet vergeten!)
- Minimaal 5 schermen: start, spel, help, highscore, about
- Minimaal 5 verschillende JavaFX controls
- Minimaal 3 verschillende events
- Minimaal 3 verschillende layout panes
- Minstens 1x van Scene wisselen, 1x van Stage
- Menubalk bovenaan
- Gegevens lezen UIT bestand (bv. spelregels)
- Gegevens schrijven NAAR bestand (highscores)
- Applicatie mag NOOIT crashen

## Code conventies
- Taal: Nederlands (variabelen, comments, UI-tekst)
- PascalCase voor klassen, camelCase voor methodes/variabelen
- Alle attributen private
- Constructors valideren input met IllegalArgumentException
- Getters die lijsten returnen gebruiken defensive copying (new ArrayList<>)
- Geen God-klassen (splits op indien nodig)
