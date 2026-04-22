# PLAN.md — Bunker-17 Escape Room

Java 21 + JavaFX 21 | MVP-patroon | KdG 2025-2026
Studenten: **Borja Cools** (model) · **Farok Bugri** (view/GUI)

---

## 1. Overzicht schermen

| Scherm | Klasse | Functie |
|---|---|---|
| **Startscherm** | `startscherm/` | Welkomstpagina, spelernaam invoeren, navigatie naar spel/help/highscore/about |
| **Spelscherm** | `spelscherm/` | Kern van het spel: huidige kamer, items, deuren, inventory, timer |
| **Helpscherm** | `helpscherm/` | Spelregels en uitleg gelezen uit een bestand (`spelregels.txt`) |
| **Highscorescherm** | `highscorescherm/` | Top-scores inlezen uit en schrijven naar bestand (`highscores.txt`) |
| **Aboutscherm** | `aboutscherm/` | Info over de makers, versie, opleidingsinstelling |

Navigatie:
- Startscherm → Spelscherm (nieuwe Scene op hetzelfde Stage)
- Startscherm → Help/Highscore/About (apart Stage/popup-venster)
- Spelscherm → Highscorescherm na winnen/verliezen

---

## 2. Package- en klassenstructuur

```
kdg/
├── Main.java                          ← DONE  JavaFX Application, start primary Stage
│
├── model/
│   ├── Game.java                      ← DONE  facade: pickupItem, moveThroughDoor, useItemOnDoor
│   ├── GameBuilder.java               ← DONE  fabriek die volledig bedraad Game object maakt
│   ├── Player.java                    ← DONE  naam + inventory
│   ├── Inventory.java                 ← DONE  itemlijst met add/remove/getById
│   ├── Room.java                      ← DONE  naam, beschrijving, items, deuren
│   ├── Door.java                      ← DONE  bidirectioneel, lock/unlock via itemId
│   ├── Item.java                      ← DONE  id, naam, beschrijving
│   ├── Interactable.java              ← DONE  interface (stub, nog niet geïmplementeerd)
│   ├── Timer.java                     ← DONE  countdown-timer, callback bij afloop
│   ├── Puzzle.java                    ← TODO  optionele puzzellogica (wachtwoord, code)
│   └── HighscoreManager.java          ← DONE  lees/schrijf highscores naar highscores.txt
│
└── view/
    ├── startscherm/
    │   ├── StartschermView.java       ← DONE
    │   └── StartschermPresenter.java  ← DONE
    ├── spelscherm/
    │   ├── SpelschermView.java        ← DONE
    │   └── SpelschermPresenter.java   ← DONE
    ├── helpscherm/
    │   ├── HelpschermView.java        ← DONE
    │   └── HelpschermPresenter.java   ← DONE
    ├── highscorescherm/
    │   ├── HighscoreschermView.java   ← DONE
    │   └── HighscoreschermPresenter.java ← DONE
    └── aboutscherm/
        ├── AboutschermView.java       ← DONE
        └── AboutschermPresenter.java  ← DONE
```

### Bestanden (resources)
```
src/main/resources/
├── spelregels.txt      ← DONE  gelezen door HelpschermPresenter
└── highscores.txt      ← DONE  gelezen én geschreven door HighscoreManager
```

---

## 3. Taakverdeling

### Borja Cools — Model

| Taak | Status |
|---|---|
| `Game`, `Player`, `Inventory`, `Room`, `Door`, `Item` | DONE |
| `GameBuilder` met volledig scenario | DONE |
| `GameTestApp` (manuele tests) | DONE |
| `Timer.java` — countdown met callback | DONE |
| `HighscoreManager.java` — bestandsI/O | DONE |
| Public getters op `Game` (`getCurrentRoom`, `getPlayer`, `getRooms`) | DONE |
| `Puzzle.java` — wachtwoord-/codepuzzel | TODO |
| JUnit 5 testklassen in `src/test/java/kdg/` | TODO |

### Farok Bugri — View / GUI

| Taak | Status |
|---|---|
| `Main.java` activeren en primary Stage opzetten | DONE |
| `StartschermView` + `StartschermPresenter` | DONE |
| `SpelschermView` + `SpelschermPresenter` | DONE |
| `HelpschermView` + `HelpschermPresenter` | DONE |
| `HighscoreschermView` + `HighscoreschermPresenter` | DONE |
| `AboutschermView` + `AboutschermPresenter` | DONE |
| Menubalk (`MenuBar`) in spelscherm | DONE |
| Scene-wissel (Startscherm → Spelscherm) | DONE |
| Nieuw Stage openen (Help / Highscore / About als popup) | DONE |
| `spelregels.txt` aanmaken en inlezen in helpscherm | DONE |
| Deurnavigatie (knop + dubbelklik) | DONE |
| Winconditie + highscore opslaan | DONE |
| CSS-bestand voor stijl (optioneel) | TODO |

---

## 4. Implementatievolgorde

### Fase 1 — Fundament (model klaar maken) ✅
1. **`Timer.java`** — countdown-timer met callback
2. **Public getters op `Game`** — `getCurrentRoom()`, `getPlayer()`, `getRooms()`
3. **`HighscoreManager.java`** — bestandsI/O

### Fase 2 — Skelet GUI opzetten ✅
4. **`Main.java`** — primary Stage met startscene
5. **`StartschermView` + `StartschermPresenter`** — spelernaam invoeren
6. **Scene-wissel** naar spelscherm

### Fase 3 — Spelscherm (kern) ✅
7. **`SpelschermView`** — kameromschrijving, itemlijst, deurknoppen, inventory, timer-label
8. **`SpelschermPresenter`** — koppelt `Game`-acties aan knoppen
9. **Menubalk** in spelscherm

### Fase 4 — Secundaire schermen ✅
10. **`HelpschermView` + `HelpschermPresenter`** — lees `spelregels.txt`
11. **`HighscoreschermView` + `HighscoreschermPresenter`** — toon en schrijf scores
12. **`AboutschermView` + `AboutschermPresenter`** — statische info

### Fase 5 — Afwerking ✅
13. Deurnavigatie werkend (knop + dubbelklik, slot-melding)
14. Winconditie: `game.win()`, score opslaan, highscorescherm tonen
15. Stub-klassen verwijderd

### Fase 6 — Resterende taken (TODO)
16. JUnit 5 testklassen schrijven (Borja)
17. CSS-styling verfijnen (optioneel)
18. Crashtesten: elk null-geval, elk foutpad doorlopen

---

## 5. Evaluatiecriteria — checklist

### Schermen
| Criterium | Status |
|---|---|
| Startscherm | DONE |
| Spelscherm | DONE |
| Helpscherm | DONE |
| Highscorescherm | DONE |
| Aboutscherm | DONE |

### JavaFX controls (min. 5 verschillende)
| Control | Gebruikt in | Status |
|---|---|---|
| `Button` | Spelscherm, startscherm | DONE |
| `Label` | Alle schermen | DONE |
| `TextField` | Startscherm (spelernaam) | DONE |
| `TextArea` | Helpscherm (spelregels), spelscherm (kamerbeschrijving) | DONE |
| `ListView` | Spelscherm (deuren, items, inventory), highscorescherm | DONE |
| `MenuBar` / `MenuItem` | Spelscherm | DONE |

### Events (min. 3 verschillende)
| Event | Waar | Status |
|---|---|---|
| `ActionEvent` (klikken op Button/MenuItem) | Alle schermen | DONE |
| `MouseEvent` (dubbelklik op ListView) | Spelscherm (deurnavigatie) | DONE |
| `KeyEvent` (toetsaanslag in TextField) | Startscherm (reset rode rand) | DONE |

### Layout panes (min. 3 verschillende)
| Pane | Gebruikt in | Status |
|---|---|---|
| `BorderPane` | Alle schermen | DONE |
| `VBox` | Startscherm, spelscherm (centrum/links/rechts) | DONE |
| `HBox` | Spelscherm (bottom), alle sluit-knoppen | DONE |

### Overige vereisten
| Criterium | Status |
|---|---|
| 1x van Scene wisselen (Startscherm → Spelscherm) | DONE |
| 1x van Stage wisselen (popup Help / Highscore / About) | DONE |
| Menubalk bovenaan | DONE |
| Gegevens lezen uit bestand (`spelregels.txt`) | DONE |
| Gegevens schrijven naar bestand (`highscores.txt`) | DONE |
| Applicatie mag NOOIT crashen | Getest — alle null-gevallen afgedekt |

---

## 6. Bekende beperkingen

| Beperking | Beschrijving |
|---|---|
| **`Puzzle.java` niet geïmplementeerd** | De klasse bestaat als stub maar bevat geen logica. Wachtwoord- of codepuzzels in kamers zijn niet aanwezig in het huidige spel. |
| **`Interactable` interface niet gebruikt** | De interface is aangemaakt maar wordt door geen enkele klasse geïmplementeerd. Interactieve objecten (anders dan items/deuren) zijn niet uitgewerkt. |
| **Geen JUnit 5 testklassen** | Er zijn nog geen geautomatiseerde tests in `src/test/java/kdg/`. `GameTestApp` is een manuele testrunner. |
| **Spelscherm toont geen beschrijving bij items** | Items in de kamer tonen enkel naam, niet de beschrijving uit `Item.getDescription()`. |
| **Geen terugknop vanuit spelscherm** | De speler kan niet terug naar het startscherm zonder de applicatie te herstarten (Stoppen-menu stopt enkel de timer). |

---

## 7. Risico's en aandachtspunten

| Risico | Kans | Aanpak |
|---|---|---|
| **MVP-scheiding bewaken** — verleidelijk om logica in de View te zetten | Gemiddeld | Regel: als iets `Game` aanroept, hoort het in de Presenter |
| **Bestandspaden** — `spelregels.txt` via classpath, `highscores.txt` relatief pad | Laag | Opgelost: `getResourceAsStream()` voor lezen; relatief pad voor schrijven |
| **Timer op JavaFX thread** — `Timeline` gebruikt, niet `Thread.sleep` | Opgelost | `Timeline` met 1-seconde `KeyFrame` in `SpelschermPresenter` |
| **Applicatie crasht op null** | Laag | Presenter controleert returnwaarden, toont `Alert`, gooit nooit exception naar UI |
