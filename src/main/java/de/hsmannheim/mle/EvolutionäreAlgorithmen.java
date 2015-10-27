/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsmannheim.mle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Alexander
 */
public class EvolutionäreAlgorithmen {

    private static final int anzahlIndividuen = 100;
    private static final double mutationsRate = 0.2;
    private static final double kreuzRate = 0.8;
    private static final int anzahlGene = 100;
    private static Individuum[] population = new Individuum[anzahlIndividuen];
    private static final BitSet besteGene = new Individuum(anzahlIndividuen).getGene();

    public static void main(String[] argu) {
        erstellePopulation();
        while (population[findeIndexVonBestesIndividuum()].getFitness() < 80) {
            System.out.println(population[findeIndexVonBestesIndividuum()].getFitness());
            Individuum[] tempPop = selektieren();
            kreuzen(tempPop);
            mutieren();
        }
    }

    private static void erstellePopulation() {

        for (int i = 0; i < anzahlIndividuen; i++) {
            Individuum individuum = new Individuum(anzahlGene);
            individuum.setFitness(besteGene);
            population[i] = individuum;

        }
    }

    private static void kreuzen(Individuum[] tempPop) {
        int selektionsAnzahl = (int) ((1 - kreuzRate) * anzahlIndividuen);
        int anzahlPaarungen = anzahlIndividuen - selektionsAnzahl;
        Random random = new Random();
        while (anzahlPaarungen > 0) {
            int indexA = random.nextInt(anzahlIndividuen);
            int indexB = random.nextInt(anzahlIndividuen);
            if (indexA != indexB && population[indexA] != null && population[indexB] != null) {
                tempPop[selektionsAnzahl++] = population[indexA].kreuzen(population[indexB]);
                anzahlPaarungen--;
            }

        }

        population = tempPop;

    }

    private static void mutieren() {
        int anzahlMutationen = (int) ((mutationsRate) * anzahlIndividuen);
        Random random = new Random();
        List<Integer> schonMutiert = new ArrayList<>();
        while (anzahlMutationen > 0) {
            int index = random.nextInt(anzahlIndividuen);
            if (!schonMutiert.contains(index)) {
                population[index].mutiere();
                schonMutiert.add(index);
                anzahlMutationen--;
            }

        }
    }

    private static int findeIndexVonBestesIndividuum() {
        double maxFitness = -1;
        int index = 0;
        for (int i = 0; i < anzahlIndividuen; i++) {
            if (maxFitness < population[i].getFitness()) {
                index = i;
                maxFitness = population[i].getFitness();
            }
        }
        return index;
    }

    private static Individuum[] selektieren() {
        int gesamtFitness = errechneGesamtFitness();
        Individuum[] tempPopulation = new Individuum[anzahlIndividuen];
        int bestes = findeIndexVonBestesIndividuum();
        tempPopulation[0] = population[bestes];
        List<Integer> indexBereitsSelektiert = new ArrayList<>();

        int selektionsAnzahl = (int) ((1 - kreuzRate) * anzahlIndividuen) - 1;
        Random random = new Random();

        int bereitsSelektiert = 1;
        while (selektionsAnzahl > 0) {
            int index = random.nextInt(anzahlIndividuen - 1);
            double rndWsk = random.nextDouble();
            double summeWsk = 0;

            while (summeWsk < rndWsk) {
                if(!indexBereitsSelektiert.contains(index)){
                double pr = errechnePr(gesamtFitness, population[index].getFitness());
                summeWsk += pr;
                }
                index++;
                if (index >= anzahlIndividuen) {
                    index = 0;
                }
            }

            if (!indexBereitsSelektiert.contains(index)) {
                tempPopulation[bereitsSelektiert++] = population[index];
                indexBereitsSelektiert.add(index);
                selektionsAnzahl--;
            }
            else{
                System.out.println(Arrays.toString(indexBereitsSelektiert.toArray()));
            }

            //verhindere, dass Individuum öfter gewählt wird 
            indexBereitsSelektiert.add(index);

        }
        return tempPopulation;

    }

    private static int errechneGesamtFitness() {
        int gesamtFitness = 0;
        for (Individuum individuum : population) {
            gesamtFitness += individuum.getFitness();
        }
        return gesamtFitness;
    }

    private static double errechnePr(double gesamtFitness, double fitness) {
        return fitness / gesamtFitness;
    }
}
