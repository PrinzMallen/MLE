package de.hsmannheim.mle;

import java.util.Random;

public class HillClimber {

    // alle staedte in einer matrix.
    private int[][] distance;
    // alle staedte in einer bestimmten reihenfolge
    private int[] trip;

    public static void main(String[] args) {
        HillClimber HillClimber = new HillClimber();
        HillClimber.DoTrip();
    }

    public HillClimber() {
        distance = new int[100][100];
        Random random = new Random();
        for (int i = 0; i < distance.length - 1; i++) {
            for (int j = 0; j < distance.length - 1; j++) {
                distance[i][j] = random.nextInt(100);
            }
        }
        trip = new int[100];
        for (int i = 0; i < trip.length; i++) {
            trip[i] = i;
        }
    }

    public int getFitness(int[] trip1) {
        int tripDistance = 0;
        for (int i = 0; i < trip1.length - 1; i++) {
            tripDistance += distance[trip1[i]][trip1[1 + i]];
        }
        tripDistance += distance[trip1.length - 1][trip1[0]];
        return tripDistance;
    }

    public void DoTrip() {
        Random random = new Random();
        int lastFitness = getFitness(trip);
        for (int i = 0; i < 1000; i++) {
            int[] newTrip = swap(random.nextInt(100), random.nextInt(100));
            int thisFitness = getFitness(newTrip);
            if (thisFitness < lastFitness) {
                lastFitness = thisFitness;
                this.trip = newTrip;
            }
            System.out.println("Durchlauf Nummer: " + i + ", Kürzester Weg: " + lastFitness
                    + ", Aktueller Weg: " + thisFitness);
        }
    }

    private int[] swap(int v1, int v2) {
        int[] trip = this.trip;
        int temp = trip[v1];
        trip[v1] = trip[v2];
        trip[v2] = temp;
        return trip;
    }

}
