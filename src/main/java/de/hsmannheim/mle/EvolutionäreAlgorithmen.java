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

    private static final int anzahlIndividuen = 500;
    private static final double mutationsRate = 0.4;
    private static final double kreuzRate = 0.2;
    private static final int anzahlGene = 100;
    private static List<Individuum> population = new ArrayList<>();
    private static final BitSet besteGene = new Individuum(anzahlIndividuen).getGene();

    public static void main(String[] argu) {
        erstellePopulation();
        int i=0;
        while (population.get(findeIndexVonBestesIndividuum()).getFitness() <anzahlGene-1) {
            
            System.out.println(population.get(findeIndexVonBestesIndividuum()).getFitness());
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
    
    private static void errechneFitness(){
        for(Individuum individuum:population){
            individuum.setFitness(besteGene);
        }
    }

    private static void kreuzen(List<Individuum> tempPop) {
        int selektionsAnzahl = (int) ((1 - kreuzRate) * anzahlIndividuen);
        int anzahlPaarungen = anzahlIndividuen - selektionsAnzahl;
        Random random = new Random();
        while (anzahlPaarungen > 0) {
            int indexA = random.nextInt(population.size()-1);
            int indexB = random.nextInt(population.size()-1);
            if (indexA != indexB) {
                tempPop.add(population.get(indexA).kreuzen(population.get(indexB)));
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
        for (int i = 0; i < anzahlIndividuen; i++) {
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
        int bestes = findeIndexVonBestesIndividuum();
        tempPopulation.add(population.remove(bestes));

        int selektionsAnzahl = (int) ((1 - kreuzRate) * anzahlIndividuen) - 1;
        Random random = new Random();

       
        while (selektionsAnzahl > 0) {
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

            tempPopulation.add(population.remove(index));

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
}
