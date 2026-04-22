package kdg.model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Beheert het lezen en schrijven van highscores naar/van highscores.txt.
 * Formaat per lijn: naam;verstrekenSeconden
 * Gesorteerd op verstrekenSeconden oplopend (snelste tijd = beste score).
 * Puur Java — geen JavaFX imports.
 *
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class HighscoreManager {

    // ---------- Inner klasse ----------

    public static class Highscore {
        private final String spelerNaam;
        private final int seconden;

        public Highscore(String spelerNaam, int seconden) {
            if (spelerNaam == null || spelerNaam.isBlank()) {
                throw new IllegalArgumentException("spelerNaam mag niet leeg zijn");
            }
            if (seconden < 0) {
                throw new IllegalArgumentException("seconden mag niet negatief zijn");
            }
            this.spelerNaam = spelerNaam;
            this.seconden = seconden;
        }

        public String getSpelerNaam() {
            return spelerNaam;
        }

        public int getSeconden() {
            return seconden;
        }

        // Omzetten naar leesbaar formaat: mm:ss
        public String getTijdAlsTekst() {
            int minuten = seconden / 60;
            int sec = seconden % 60;
            return String.format("%02d:%02d", minuten, sec);
        }

        @Override
        public String toString() {
            return spelerNaam + " — " + getTijdAlsTekst();
        }
    }

    // ---------- Attributen ----------

    private static final String BESTANDSNAAM = "highscores.txt";
    private static final int MAX_SCORES = 10;

    private final Path bestandspad;
    private final List<Highscore> scores;

    // ---------- Constructors ----------

    // Standaard: highscores.txt naast de applicatie
    public HighscoreManager() {
        this(Path.of(BESTANDSNAAM));
    }

    // Aangepast pad (handig voor testen)
    public HighscoreManager(Path bestandspad) {
        if (bestandspad == null) {
            throw new IllegalArgumentException("bestandspad mag niet null zijn");
        }
        this.bestandspad = bestandspad;
        this.scores = new ArrayList<>();
        laadVanBestand();
    }

    // ---------- Public methodes ----------

    // Nieuwe score toevoegen, sorteren en opslaan
    public void voegScoreToe(String spelerNaam, int seconden) {
        scores.add(new Highscore(spelerNaam, seconden));
        scores.sort(Comparator.comparingInt(Highscore::getSeconden));
        if (scores.size() > MAX_SCORES) {
            scores.subList(MAX_SCORES, scores.size()).clear();
        }
        slaOpNaarBestand();
    }

    // Gesorteerde lijst opvragen (defensieve kopie)
    public List<Highscore> getScores() {
        return new ArrayList<>(scores);
    }

    // Controleren of een score in de top-10 valt
    public boolean isTopScore(int seconden) {
        if (scores.size() < MAX_SCORES) return true;
        return seconden < scores.get(scores.size() - 1).getSeconden();
    }

    // ---------- Private hulpmethodes ----------

    private void laadVanBestand() {
        if (!Files.exists(bestandspad)) return;

        try (BufferedReader reader = Files.newBufferedReader(bestandspad)) {
            String lijn;
            while ((lijn = reader.readLine()) != null) {
                verwerkLijn(lijn.trim());
            }
            scores.sort(Comparator.comparingInt(Highscore::getSeconden));
        } catch (IOException e) {
            // Bestand onleesbaar — starten met lege lijst, geen crash
        }
    }

    private void verwerkLijn(String lijn) {
        if (lijn.isEmpty()) return;
        String[] delen = lijn.split(";", 2);
        if (delen.length != 2) return;
        try {
            String naam = delen[0].trim();
            int seconden = Integer.parseInt(delen[1].trim());
            if (!naam.isEmpty() && seconden >= 0) {
                scores.add(new Highscore(naam, seconden));
            }
        } catch (NumberFormatException e) {
            // Ongeldige lijn overslaan
        }
    }

    private void slaOpNaarBestand() {
        try (BufferedWriter writer = Files.newBufferedWriter(bestandspad)) {
            for (Highscore score : scores) {
                writer.write(score.getSpelerNaam() + ";" + score.getSeconden());
                writer.newLine();
            }
        } catch (IOException e) {
            // Schrijven mislukt — score staat wel in geheugen, geen crash
        }
    }
}