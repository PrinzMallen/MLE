package PingPong;

public class Agent {

    double[][][][][][] gehirn;
    int richtungsEntscheidung;
    int[] gedaechtnis;
    int letzteRichtungsEntscheidung;

    private double alpha = 0.01;
    private double gamma = 0.9;

    public Agent(int xBallSchritteMaximal, int yBallSchritteMaximal, int xSchlaegerSchritteMaximal) {

        int xBallGeschwindigkeiten = 2;
        int yBallGeschwindigkeiten = 2;
        int richtungen = 3;
        //jeder möglicher zustand gespeichert wird hierzu jede mögliche reaktion ( rechts,links, nichts tun) und ob diese belohnt wurde
        this.gehirn = new double[xBallSchritteMaximal + 1][yBallSchritteMaximal + 1][xBallGeschwindigkeiten][yBallGeschwindigkeiten][xSchlaegerSchritteMaximal + 1][richtungen];

        //initalisiere jeder aktion für jede situation mit einem sehr geringen wert
        for (double[][][][][] xBallSchritte : gehirn) {
            for (double[][][][] yBallSchritte : xBallSchritte) {
                for (double[][][] xSchlaegerSchritte : yBallSchritte) {
                    for (double[][] xGeschwindigkeit : xSchlaegerSchritte) {
                        for (double[] yGeschwindigkeit : xGeschwindigkeit) {
                            for (int i = 0; i < yGeschwindigkeit.length; i++) {
                                yGeschwindigkeit[i] = Math.random() / 1000;
                            }
                        }
                    }
                }
            }
        }
    }

    int maxQ(Status status) {
        int maxIndex = (int) (Math.random() * 3);
        double epsilon = 0.01;
        if (Math.random() > epsilon) {
            double maxValue = gehirn[status.xBallPos][status.yBallPos][status.xBallSpeed][status.yBallSpeed][status.xSchlägerPos][0];
            maxIndex = 0;
            //hole best bewertete aktion für diese situation
            for (int i = 0; i < 3; i++) {
                if (gehirn[status.xBallPos][status.yBallPos][status.xBallSpeed][status.yBallSpeed][status.xSchlägerPos][i] > maxValue) {
                    maxValue = gehirn[status.xBallPos][status.yBallPos][status.xBallSpeed][status.yBallSpeed][status.xSchlägerPos][i];
                    maxIndex = i;
                }
            }
        }
        return maxIndex;
    }

    void lernen(Status statusVorher, Status statusDanach, int aktion, int belohnung) {

        //generelle bewertung der aktion in dieser situation
        double bewertung = gehirn[statusVorher.xBallPos][statusVorher.yBallPos][statusVorher.xBallSpeed][statusVorher.yBallSpeed][statusVorher.xSchlägerPos][aktion];
        //errechne bewertung der geraden ausgeführten aktion im hinblick auf ergebnis ( belohnung)
        bewertung += alpha * (belohnung + gamma * gehirn[statusDanach.xBallPos][statusDanach.yBallPos][statusDanach.xBallSpeed][statusDanach.yBallSpeed][statusDanach.xSchlägerPos][maxQ(statusDanach)] - bewertung);
        gehirn[statusVorher.xBallPos][statusVorher.yBallPos][statusVorher.xBallSpeed][statusVorher.yBallSpeed][statusVorher.xSchlägerPos][aktion] = bewertung;
    }
}
