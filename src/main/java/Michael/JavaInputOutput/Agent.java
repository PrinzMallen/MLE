package Michael.JavaInputOutput;


public class Agent {
	
	int [][][][][][] gehirn;
	int richtungsEntscheidung;
	int []gedaechtnis;
	int letzteRichtungsEntscheidung;
	
	public Agent(int xBallSchritteMaximal, int yBallSchritteMaximal, int xSchlaegerSchritteMaximal) {
		
		int xBallGeschwindigkeiten = 2;
		int yBallGeschwindigkeiten = 2;
		int richtungen = 2;
		
		this.gehirn = new int [xBallSchritteMaximal + 1][yBallSchritteMaximal + 1][xSchlaegerSchritteMaximal + 1][xBallGeschwindigkeiten][yBallGeschwindigkeiten][richtungen];
		
		for (int[][][][][] xBallSchritte : gehirn) {
			for (int[][][][] yBallSchritte : xBallSchritte) {
				for (int[][][] xSchlaegerSchritte : yBallSchritte) {
					for (int[][] xGeschwindigkeit : xSchlaegerSchritte) {
						for (int[] yGeschwindigkeit : xGeschwindigkeit) {
							for (int i = 0; i < yGeschwindigkeit.length; i++) {
								yGeschwindigkeit[i] = 0;
							}
						}
					}
				}
			}
		}
	}
	
	public void aktualisieren(int xBall, int yBall, int xSchlaeger, int xBallGeschwindigkeit, int yBallGeschwindigkeit) {
		
		if(yBallGeschwindigkeit < 0){
			yBallGeschwindigkeit = 0;
		}
		if(xBallGeschwindigkeit < 0){
			xBallGeschwindigkeit = 0;
		}
		int []naechsteRichtung = this.gehirn[xBall][yBall][xSchlaeger][xBallGeschwindigkeit][yBallGeschwindigkeit];
		
		if(naechsteRichtung[0] == 1){
			gedaechtnis[letzteRichtungsEntscheidung] = 1;
			richtungsEntscheidung = 0;
		}else if(naechsteRichtung[1] == 1){
			gedaechtnis[letzteRichtungsEntscheidung] = 1;
			richtungsEntscheidung = 1;
		}else if(naechsteRichtung[0] == -1){
			richtungsEntscheidung = 1;
		}else if(naechsteRichtung[1] == -1){
			richtungsEntscheidung = 0;
		}else{
			richtungsEntscheidung = (int) Math.round(Math.random());
		}
		
		gedaechtnis = naechsteRichtung;
		letzteRichtungsEntscheidung = richtungsEntscheidung;
	}
	
	public void bekommeBelohnung(int belohnung) {
		gedaechtnis[letzteRichtungsEntscheidung] = belohnung;
	}

	public int naechsterSchritt() {
		return richtungsEntscheidung;
	}
}
