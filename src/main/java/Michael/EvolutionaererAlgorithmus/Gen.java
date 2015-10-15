package Michael.EvolutionaererAlgorithmus;

import java.util.Random;

public class Gen implements Comparable<Gen>{

	int []genBits;
	double fitness;
	
	public Gen(int genLaenge) {
		
		genBits = new int [genLaenge];
		
		for (int i = 0; i < genLaenge ; i++) {
			genBits[i] = (int) Math.round(Math.random());
		}
	}
	
	public Gen(int[] genBits) {
		this.genBits = genBits;
	}
	
	@Override
	public String toString() {
		String string = "";
		
		for (int bit : genBits) {
			string += bit;
		}
		
		return string;
	}
	
	public void mutiere(){
		int bitIndex = erstelleZufallsInt(0, genBits.length - 1);
		genBits[bitIndex] = (genBits[bitIndex] + 1) % 2;
	}
	
	public Gen kreuzen(Gen gen){
		
		int spaltungsStelle = erstelleZufallsInt(1, genBits.length - 1);
		int [] gekreuztesGen = new int[genBits.length];
		
		for (int i = 0; i < genBits.length; i++) {
			if(i < spaltungsStelle){
				gekreuztesGen[i] = genBits[i];
			}else{
				gekreuztesGen[i] = gen.genBits[i];
			}
		}
		
		return new Gen(gekreuztesGen);
	}
	
	@Override
	public boolean equals(Object gen) {
		Gen vergleichsGen = (Gen) gen;
		fitness = 0;

		for (int i = 0; i < genBits.length ; i++) {
			if(genBits[i] == vergleichsGen.genBits[i])
			{
				fitness += 1;
			}
		}
		return fitness == genBits.length;
	}
	
	public static int erstelleZufallsInt(int min, int max) {

	    Random random = new Random();
	    return random.nextInt((max - min) + 1) + min;
	}

	@Override
	public int compareTo(Gen gen) {
		return (int) (gen.fitness - fitness);
	}
}
