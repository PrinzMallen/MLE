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

    public Individuum(int genAnzahl) {
        gene = new BitSet(genAnzahl);
        Random random = new Random();
        for (int i = 0; i < genAnzahl; i++) {
            if (random.nextBoolean()) {
                gene.set(i);
            }

        }
    }
    
    public Individuum(BitSet gene){
        this.gene=gene;
    }
    

    public double setFitness(BitSet besteGene) {
        for (int i = 0; i < gene.length(); i++) {
            if (besteGene.get(i) == gene.get(i)) {
                fitness++;
            }
        }
        return fitness;
    }

    public Individuum kreuzen(Individuum partner) {
        Random random = new Random();
        //kein komplettes crossover .. 50 zu 50
        int spaltungStelle =random.nextInt((int) (gene.length()*0.5d));
        BitSet gekreuzteGene=new BitSet(gene.length());
        for(int i=0;i<gene.length();i++){
            if(i<=spaltungStelle){
                gekreuzteGene.set(i, this.gene.get(i));
            }
            else{
                 gekreuzteGene.set(i, partner.getGene().get(i));
            }
        }
        return new Individuum(gekreuzteGene);
    }
    
    public void mutiere(){
        Random random = new Random();
        gene.flip(random.nextInt(gene.length()-1));
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
