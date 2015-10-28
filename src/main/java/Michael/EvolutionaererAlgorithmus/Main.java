package Michael.EvolutionaererAlgorithmus;

import java.util.Arrays;

public class Main {

    static int population = 10000;
    static int mutationsrate = 10;
    static int guteGene = 1;
    static int fitnessSumme;
    static Gen zielGen = new Gen(100);
    static Gen[] gene = new Gen[population];

    public static void main(String[] args) {

        for (int i = 0; i < gene.length; i++) {
            gene[i] = new Gen(100);
        }

        System.out.println("Zielgen = " + zielGen);

        do {
            for (Gen gen : gene) {
                gen.equals(zielGen);
            }

            System.out.println("bestesGen = " + gene[0]);

            errechneFitnessSumme();
            selektiereGene();

            for (int i = 0; i < mutationsrate; i++) {
                gene[Gen.erstelleZufallsInt(10, population)].mutiere();
            }

        } while (!gene[0].equals(zielGen));

    }

    private static void errechneFitnessSumme() {
        fitnessSumme = 0;
        for (Gen gen : gene) {
            fitnessSumme += gen.fitness;
        }
    }

    private static void selektiereGene() {
        double pr;
        boolean stop = false;

        Gen[] temporerePopulation = new Gen[population];
        Arrays.sort(gene);

        int i;
        for (i = 0; i < guteGene; i++) {
            temporerePopulation[i] = gene[i];
        }

        while (!stop) {
            double random = Math.random();
            for (Gen gen : gene) {

                pr = gen.fitness / fitnessSumme;

                if (random <= pr) {
                    temporerePopulation[i++] = gen;
                }
                if (i >= (0.2 * population)) {
                    stop = true;
                    break;
                }
            }
        }

        for (; i < gene.length; i++) {
            int indexA = Gen.erstelleZufallsInt(0, i - 1);
            int indexB = Gen.erstelleZufallsInt(0, i - 1);

            temporerePopulation[i] = temporerePopulation[indexA].kreuzen(temporerePopulation[indexB]);
        }

        gene = temporerePopulation;
    }
}
