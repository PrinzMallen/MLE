package de.hsmannheim.mle;

import java.util.Random;

public class HillClimber {

    private final int anzahlDurchläufe = 1000000;

    // alle staedte in einer matrix.
    private final int[][] entfernungen;
    // alle staedte in einer bestimmten reihenfolge
    private int[] tour;

    public static void main(String[] args) {
        HillClimber HillClimber = new HillClimber();
        HillClimber.starteTour();
    }

    public HillClimber() {
        entfernungen = new int[100][100];
        Random zufallsGenerator = new Random();
        for (int i = 0; i < entfernungen.length - 1; i++) {
            for (int j = 0; j < entfernungen.length - 1; j++) {
                //nur wenn es wirklich eine Distanz geben kann
                if (j != i) {
                    //keine 0 als distanz
                    int zufälligeEntfernung = zufallsGenerator.nextInt(100) + 1;
                    //x zu y gleiche distanz wie y zu x
                    entfernungen[i][j] = zufälligeEntfernung;
                    entfernungen[j][i] = zufälligeEntfernung;
                }

            }
        }
        tour = new int[100];
        for (int i = 0; i < tour.length; i++) {
            tour[i] = i;
        }
    }

    public int errechneGesamtDistanz(int[] tour) {
        int tourStrecke = 0;
        //errechne weg bis zur letzten stadt
        for (int i = 0; i < tour.length - 1; i++) {
            tourStrecke += entfernungen[tour[i]][tour[1 + i]];
        }
        //kehre zurück zur ersten Stadt
        tourStrecke += entfernungen[tour.length - 1][tour[0]];
        return tourStrecke;
    }

    public void starteTour() {
        Random random = new Random();
        //errechne tour von 1-100
        int besteDistanz = errechneGesamtDistanz(tour);
        int anzahlErfolgloserDurchgänge = 0;
        System.out.println("Anfangsdistanz: "+besteDistanz);
        for (int i = 0; i < anzahlDurchläufe; i++) {
            //ändere Tour leicht, aber stelle sicher das sich etwas ändert
            int stadtA = 0;
            int stadtB = 0;
            while(stadtA==stadtB){
                stadtA=random.nextInt(100);
                stadtB=random.nextInt(100);
            }
            int[] neueTour = swap(stadtA, stadtB);
            //errechne veränderte Tour
            int neueDistanz = errechneGesamtDistanz(neueTour);
            //wenn neue beste Distanz gefunden, speichern + ausgabe
            if (neueDistanz < besteDistanz) {
                besteDistanz = neueDistanz;
                this.tour = neueTour;
                System.out.println("Durchlauf Nummer: " + i + ", Kürzester bisheriger Weg: " + besteDistanz
                        + ", benötigte Durchläufe bis zur Verbesserung: " + anzahlErfolgloserDurchgänge);
                anzahlErfolgloserDurchgänge=0;
            } else {
                anzahlErfolgloserDurchgänge++;
            }

        }
        System.out.println("Beste errechnete Distanz: "+besteDistanz);
    }

    private int[] swap(int v1, int v2) {
        //standard 3 Eck-Swap
        int[] neueTour = this.tour;
        int temp = neueTour[v1];
        neueTour[v1] = neueTour[v2];
        neueTour[v2] = temp;
        return neueTour;
    }

}
