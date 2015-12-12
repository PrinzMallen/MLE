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
    private static final double mutationsRate = 0.1;
    private static final double kreuzRate = 0.25;
    private static final int anzahlGöttlicherAktionen = 1;
    private static final int anzahlGene = 100;
    private static List<Individuum> population = new ArrayList<>();
    private static final Random random = new Random(0);
    private static final BitSet besteGene = new Individuum(anzahlGene, random).getGene();

    public static void main(String[] argu) {
        erstellePopulation();
        System.out.println(population.size());
        int i = 0;
        double max = population.get(findeIndexVonBestesIndividuum()).getFitness();
        while (population.get(findeIndexVonBestesIndividuum()).getFitness() < anzahlGene - 1) {
            if (max < population.get(findeIndexVonBestesIndividuum()).getFitness()) {
                max = population.get(findeIndexVonBestesIndividuum()).getFitness();
                System.out.println(max);
            }
//            if(max>population.get(findeIndexVonBestesIndividuum()).getFitness()){
//                System.out.println("FAIL");
//            }

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
            Individuum individuum = new Individuum(anzahlGene, random);
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
        int gesamtFitness = errechneGesamtFitness();
        int selektionsAnzahl = (int) ((1 - kreuzRate) * anzahlIndividuen);
        int anzahlPaarungen = anzahlIndividuen - selektionsAnzahl;
        while (anzahlPaarungen > 0) {
            int indexA = wähleIndividuumAnhandFitness(gesamtFitness);
            int indexB = wähleIndividuumAnhandFitness(gesamtFitness);
            if (indexA != indexB) {
                //TODO 2 Kinder.. Eventuell mit einem aufruf und array zurückgeben. Dann kann man quasi vater x mutter, mutter x vater machen mit selber Spaltungstelle
                Individuum neue[] = population.get(indexA).kreuzen(population.get(indexB));
                //neues.setFitness(besteGene);
                //System.out.println(population.get(indexA).getFitness()+"x "+population.get(indexB).getFitness()+" ="+neues.getFitness());
                tempPop.add(neue[0]);
                anzahlPaarungen--;
                if (anzahlPaarungen > 0) {
                    tempPop.add(neue[1]);
                    anzahlPaarungen--;
                }
            }

        }

        population = tempPop;

    }

    private static void mutieren() {
        int anzahlMutationen = (int) ((mutationsRate) * anzahlIndividuen);

        List<Individuum> tempPop = new ArrayList<>();
        while (anzahlMutationen > 0) {
           

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

        int index = random.nextInt(population.size() - 1);
        double rndWsk = random.nextDouble();
        double summeWsk = 0;

        do {
            index++;
            double pr = errechnePr(gesamtFitness, population.get(index).getFitness());
            summeWsk += pr;

            
            if (index >= population.size() - 1) {
                index = 0;
            }
        }while (summeWsk < rndWsk) ;
        return index;
    }
}
