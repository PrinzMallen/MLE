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

    private static final int anzahlIndividuen = 2000;
    private static final double mutationsRate = 0.5;
    private static final double kreuzRate = 0.8;
    private static final int anzahlGöttlicherAktionen = 20;
    private static final int anzahlGene = 1000;
    private static List<Individuum> population = new ArrayList<>();
    private static final BitSet besteGene = new Individuum(anzahlGene).getGene();

    public static void main(String[] argu) {
        erstellePopulation();
        int i = 0;
        double max = population.get(findeIndexVonBestesIndividuum()).getFitness();
        while (population.get(findeIndexVonBestesIndividuum()).getFitness() < anzahlGene - 1) {
            if (max < population.get(findeIndexVonBestesIndividuum()).getFitness()) {
                max = population.get(findeIndexVonBestesIndividuum()).getFitness();
                System.out.println(max);
            }

            List<Individuum> tempPop = selektieren();

            kreuzen(tempPop);
            mutieren();
            errechneFitness();
            i++;
        }
        System.out.println(i);
    }

    private static void erstellePopulation() {

        for (int i = 0; i < anzahlIndividuen; i++) {
            Individuum individuum = new Individuum(anzahlGene);
            individuum.setFitness(besteGene);
            population.add(individuum);

        }
    }

    private static void errechneFitness() {
        for (Individuum individuum : population) {
            individuum.setFitness(besteGene);
        }
    }

    private static void kreuzen(List<Individuum> tempPop) {
        population.addAll(tempPop);
        int gesamtFitness=errechneGesamtFitness();
        int selektionsAnzahl = (int) ((1 - kreuzRate) * anzahlIndividuen);
        int anzahlPaarungen = anzahlIndividuen - selektionsAnzahl;
        while (anzahlPaarungen > 0) {
            int indexA = wähleIndividuumAnhandFitness(gesamtFitness);
            int indexB = wähleIndividuumAnhandFitness(gesamtFitness);
            if (indexA != indexB) {
                //TODO 2 Kinder.. Eventuell mit einem aufruf und array zurückgeben. Dann kann man quasi vater x mutter, mutter x vater machen mit selber Spaltungstelle
                Individuum neues = population.get(indexA).kreuzen(population.get(indexB));
                //neues.setFitness(besteGene);
                //System.out.println(population.get(indexA).getFitness()+"x "+population.get(indexB).getFitness()+" ="+neues.getFitness());
                tempPop.add(neues);
                anzahlPaarungen--;

            }

        }

        population = tempPop;

    }

    private static void mutieren() {
        int anzahlMutationen = (int) ((mutationsRate) * anzahlIndividuen);

        List<Individuum> tempPop = new ArrayList<>();
        while (anzahlMutationen > 0) {
            Random random = new Random();

            int index = random.nextInt(population.size() - 1);
            Individuum individuum = population.remove(index);
            individuum.mutiere();
            tempPop.add(individuum);

            anzahlMutationen--;
        }
        population.addAll(tempPop);

    }

    private static int findeIndexVonBestesIndividuum() {
        double maxFitness = -1;
        int index = 0;
        for (int i = 0; i < population.size(); i++) {
            if (maxFitness < population.get(i).getFitness()) {
                index = i;
                maxFitness = population.get(i).getFitness();
            }
        }
        return index;
    }

    private static List<Individuum> selektieren() {
        int gesamtFitness = errechneGesamtFitness();
        List<Individuum> tempPopulation = new ArrayList<>();
        for (int i = 0; i < anzahlGöttlicherAktionen; i++) {
            int bestes = findeIndexVonBestesIndividuum();
            tempPopulation.add(population.remove(bestes));
        }

        int selektionsAnzahl = (int) ((1 - kreuzRate) * anzahlIndividuen) - anzahlGöttlicherAktionen;

        while (selektionsAnzahl > 0) {

            tempPopulation.add(population.remove(wähleIndividuumAnhandFitness(gesamtFitness)));

            selektionsAnzahl--;

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

    private static int wähleIndividuumAnhandFitness(int gesamtFitness) {
        Random random = new Random();
        int index = random.nextInt(population.size() - 1);
        double rndWsk = random.nextDouble();
        double summeWsk = 0;

        while (summeWsk < rndWsk) {
            double pr = errechnePr(gesamtFitness, population.get(index).getFitness());
            summeWsk += pr;

            index++;
            if (index >= population.size() - 1) {
                index = 0;
            }
        }
        return index;
    }
}
