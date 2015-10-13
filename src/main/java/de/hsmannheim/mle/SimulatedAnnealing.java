package de.hsmannheim.mle;

import java.util.Random;

public class SimulatedAnnealing {

    private final int anzahlDurchläufe = 1000000;

    private final double startTemperatur = 80;

    private final double epsilon = 0.005d;

    // alle staedte in einer matrix.
    private final int[][] entfernungen;
    // alle staedte in einer bestimmten reihenfolge
    private int[] tour;

    public static void main(String[] args) {
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
        simulatedAnnealing.starteTour();
    }

    public SimulatedAnnealing() {
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
        double aktuelleTemperatur = startTemperatur;
        double aktuellBesteTemperatur = 0;
        int besteDistanz = errechneGesamtDistanz(tour);
        System.out.println("Anfangsdistanz: " + besteDistanz);
        do {
            //ändere Tour leicht, aber stelle sicher das sich etwas ändert
            int stadtA = 0;
            int stadtB = 0;
            while (stadtA == stadtB) {
                stadtA = random.nextInt(100);
                stadtB = random.nextInt(100);
            }
            int[] neueTour = swap(stadtA, stadtB);
            //errechne veränderte Tour
            int neueDistanz = errechneGesamtDistanz(neueTour);

            
            //errechne wsk für besseres ergebnis
            double wsk = errechneWsk(neueDistanz, besteDistanz, aktuelleTemperatur);

            //wenn neue beste Distanz gefunden, speichern + ausgabe
            if (neueDistanz < besteDistanz || random.nextDouble() < wsk) {
                
                besteDistanz = neueDistanz;
                this.tour = neueTour;
                aktuellBesteTemperatur = aktuelleTemperatur;
            }

            aktuelleTemperatur = aktuelleTemperatur - epsilon;

        } while (aktuelleTemperatur > epsilon);
        System.out.println("Beste errechnete Distanz: " + besteDistanz + " mit Temperatur: " +aktuellBesteTemperatur);
    }

    private double errechneWsk(int neueDistanz, int besteDistanz, double temp) {
        return Math.exp(-(neueDistanz - besteDistanz) / temp);
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
