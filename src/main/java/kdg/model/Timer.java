package kdg.model;

/**
 * Countdown-timer voor het escape room spel.
 * Puur Java — geen JavaFX imports.
 * De Presenter roept tick() aan via een JavaFX Timeline (elke seconde).
 * Bij het aflopen van de tijd wordt de opgegeven callback uitgevoerd.
 *
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class Timer {
    // Attributen
    private final int maxSeconden;
    private int verstrekenSeconden;
    private boolean actief;
    private boolean afgelopen;
    private Runnable tijdOpCallback;

    // Constructor
    public Timer(int maxSeconden) {
        if (maxSeconden <= 0) {
            throw new IllegalArgumentException("maxSeconden moet groter zijn dan 0");
        }
        this.maxSeconden = maxSeconden;
        this.verstrekenSeconden = 0;
        this.actief = false;
        this.afgelopen = false;
    }

    // Timer starten
    public void start() {
        this.actief = true;
        this.afgelopen = false;
    }

    // Timer pauzeren
    public void pauze() {
        this.actief = false;
    }

    // Timer hervatten
    public void hervat() {
        if (!afgelopen) {
            this.actief = true;
        }
    }

    // Timer resetten
    public void reset() {
        this.verstrekenSeconden = 0;
        this.actief = false;
        this.afgelopen = false;
    }

    /**
     * Wordt elke seconde aangeroepen door de Presenter via JavaFX Timeline.
     * Verhoogt de verstreken tijd en vuurt de callback als de tijd om is.
     */
    public void tick() {
        if (!actief || afgelopen) return;

        verstrekenSeconden++;

        if (verstrekenSeconden >= maxSeconden) {
            actief = false;
            afgelopen = true;
            if (tijdOpCallback != null) {
                tijdOpCallback.run();
            }
        }
    }

    // Callback instellen die afgaat als de tijd om is
    public void setTijdOpCallback(Runnable callback) {
        this.tijdOpCallback = callback;
    }

    // Resterende seconden opvragen (voor weergave in de view)
    public int getResterendeSeconden() {
        return Math.max(0, maxSeconden - verstrekenSeconden);
    }

    // Getters
    public int getMaxSeconden() {
        return maxSeconden;
    }

    public int getVerstrekenSeconden() {
        return verstrekenSeconden;
    }

    public boolean isActief() {
        return actief;
    }

    public boolean isAfgelopen() {
        return afgelopen;
    }
}