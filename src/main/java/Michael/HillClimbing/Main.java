package Michael.HillClimbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		int anzahlDerStaedte = 100;
		int anzahlDerDurchlaeufe = 1000;
		
		int[][] distanzenTabelle = erstelleDistanzenTabelle(anzahlDerStaedte);
		int[] backupArray;
		int[] hypothese = erstelleStartHypothese(anzahlDerStaedte);
		
		ausgabe("Anfangszustand = ", hypothese);
		
		for (int i = 0; i < anzahlDerDurchlaeufe; i++) {
			
			backupArray = erstelleTiefeArryaKopie(hypothese);
			
			int aktuelleDistanz = errechneAktuelleDistanz(hypothese, distanzenTabelle);
			
			List<Integer> nummerierung = new ArrayList<Integer>();
			for (int j = 1; j < anzahlDerStaedte; j++) {
				nummerierung.add(j);
			}
			
			int zufallsInt;
			
			zufallsInt = erstelleZufallsInt(0, (nummerierung.size()-1));
			int zufallsIntA = nummerierung.get(zufallsInt);
			nummerierung.remove(zufallsInt);
			
			zufallsInt = erstelleZufallsInt(0, (nummerierung.size()-1));
			int zufallsIntB = nummerierung.get(zufallsInt);
			nummerierung.remove(zufallsInt);

            int temp = hypothese[zufallsIntA];
            hypothese[zufallsIntA] = hypothese[zufallsIntB];
            hypothese[zufallsIntB] = temp;
            
            int neueDistanz = errechneAktuelleDistanz(hypothese, distanzenTabelle);
            
            if (aktuelleDistanz >= neueDistanz){
            	System.out.println("Distanzminderung in Schritt "+ i + ": von " + aktuelleDistanz + " auf " + neueDistanz + " gesunken");
            }else{
            	hypothese = erstelleTiefeArryaKopie(backupArray);
            }
		}
		ausgabe("Endzustand = ", hypothese);
	}
	
	
	public static int errechneAktuelleDistanz(int[] hypothese, int[][] distanzenTabelle){
		
		int aktuelleDistanz = 0;

        for (int i = 0; i < hypothese.length - 1; ++i)
        {
        	aktuelleDistanz += distanzenTabelle[hypothese[i]][hypothese[i + 1]];
        }
		
		return aktuelleDistanz;
	}
	
    public static int[] erstelleTiefeArryaKopie(int[] originalArray){
    	
        int[] tiefeArryaKopie = new int[originalArray.length];
        
        for (int i = 0; i < originalArray.length; ++i)
        {
        	tiefeArryaKopie[i] = originalArray[i];
        }
        return tiefeArryaKopie;
    }
	
	public static int[] erstelleStartHypothese(int anzahlDerStaedte){
		
		int[] startHypothese = new int[anzahlDerStaedte];
		List<Integer> nummerierung = new ArrayList<Integer>();
		
		startHypothese[0] = 0;
		
		for (int i = 1; i < anzahlDerStaedte; i++) {
			nummerierung.add(i);
		}
		
		for (int i = 1; i < startHypothese.length; i++) {
			int zufallsInt = erstelleZufallsInt(0, (nummerierung.size()-1));
			startHypothese[i] = nummerierung.get(zufallsInt);
			nummerierung.remove(zufallsInt);
		}
		return startHypothese;
	}
	
	public static int[][] erstelleDistanzenTabelle(int anzahlDerStaedte){
		
		int[][] distanzenTabelle = new int[anzahlDerStaedte][anzahlDerStaedte];
		
		for(int i = 0; i < distanzenTabelle.length; i++) {
			distanzenTabelle[i][i] = 0;
		}
		for (int i = 0; i < distanzenTabelle.length; i++) {
			for (int j = (i+1); j < distanzenTabelle.length; j++) {
				distanzenTabelle[j][i] = erstelleZufallsInt(1, 200);
				distanzenTabelle[i][j] = distanzenTabelle[j][i];
			}
		}
		return distanzenTabelle;
	}
	
	public static int erstelleZufallsInt(int min, int max) {

	    Random random = new Random();
	    return random.nextInt((max - min) + 1) + min;
	}
	
	public static void ausgabe(String textZusatz, int[] auszugebendesArray){
		
		String ausgabe = "" + auszugebendesArray[0];
		
		for (int i = 1; i < auszugebendesArray.length; i++) {
			ausgabe += ", " + auszugebendesArray[i];
		}
		System.out.println(textZusatz + ausgabe);
	}
}
