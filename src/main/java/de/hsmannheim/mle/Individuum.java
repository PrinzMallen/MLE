/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsmannheim.mle;

import java.util.BitSet;
import java.util.Random;

/**
 *
 * @author Alexander
 */
public class Individuum {

    private BitSet gene;

    private double fitness;

    Random random;

    public Individuum(int genAnzahl, Random random) {
        gene = new BitSet(genAnzahl);
        this.random = random;
        for (int i = 0; i < genAnzahl; i++) {
            if (random.nextBoolean()) {
                gene.set(i);
            }

        }
    }

    public Individuum(BitSet gene, Random random) {
        this.random = random;
        this.gene = gene;
    }

    public double setFitness(BitSet besteGene) {
        fitness = 0;
        for (int i = 0; i < gene.length(); i++) {
            if (besteGene.get(i) == gene.get(i)) {
                fitness++;
            }
        }
        return fitness;
    }

    public Individuum[] kreuzen(Individuum partner) {

        //kein komplettes crossover .. 50 zu 50
        int spaltungStelle = random.nextInt((gene.size() - 1));
        BitSet gekreuzteGene1 = new BitSet(gene.size());
        BitSet gekreuzteGene2 = new BitSet(gene.size());
        for (int i = 0; i < gene.size(); i++) {
            if (i <= spaltungStelle) {
                gekreuzteGene1.set(i, this.gene.get(i));

                gekreuzteGene2.set(i, partner.getGene().get(i));
            } else {
                gekreuzteGene1.set(i, partner.getGene().get(i));
                gekreuzteGene2.set(i, this.gene.get(i));
            }
        }
        Individuum[] kinder = new Individuum[2];
        kinder[0] = new Individuum(gekreuzteGene1, random);
        kinder[1] = new Individuum(gekreuzteGene2, random);
        return kinder;
    }

    public void mutiere() {

        gene.flip(random.nextInt(gene.size() - 1));
    }

    public BitSet getGene() {
        return gene;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

}
