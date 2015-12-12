package PingPong;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    public static final int imageWidth = 360;
    public static final int imageHeight = 360;
    public int xBallGroese = 30;
    public int yBallGroese = 30;
    public int xSchlaegerGroese = 90;
    public int ySchlaegerGroese = 10;
    public int xBallSchritteMaximal = 10;
    public int yBallSchritteMaximal = 11;
    public int xSchlaegerSchritteMaximal = 9;
    public InputOutput inputOutput = new InputOutput(this);
    public boolean stop = false;
    ImagePanel canvas = new ImagePanel();
    ImageObserver imo = null;
    Image renderTarget = null;
    public int mousex, mousey, mousek;
    public int key;

    public MainFrame(String[] args) {
        super("PingPong");

        getContentPane().setSize(imageWidth, imageHeight);
        setSize(imageWidth + 50, imageHeight + 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        canvas.img = createImage(imageWidth, imageHeight);

        add(canvas);

        run();
    }

    public void run() {
        int times = 0;
        int xBall = 5, yBall = 6, xSchlaeger = 5, xBallGeschwindigkeit = 1, yBallGeschwindigkeit = 1;
        int score = 0;

        Agent agent = new Agent(xBallSchritteMaximal, yBallSchritteMaximal, xSchlaegerSchritteMaximal);

        while (!stop) {
            //vorher eh nicht wirklich erfolgreich.. schnell simulierne
            if (times > 100000) {
                inputOutput.fillRect(0, 0, imageWidth, imageHeight, Color.black);
                inputOutput.fillRect(xBall * 30, yBall * 30, xBallGroese, yBallGroese, Color.green);
                inputOutput.fillRect(xSchlaeger * 30, 11 * 30 + 20, xSchlaegerGroese, ySchlaegerGroese, Color.orange);
            }
            //Status vor aktion
            Status statusVorher = new Status(xBall, yBall, xBallGeschwindigkeit, yBallGeschwindigkeit, xSchlaeger);
            int aktion = agent.maxQ(statusVorher);
            //0 --> nichts tun, 1--> nach rechts, 2--> nach links
            
            //führe schritt aus 
            if (aktion == 1) {
                xSchlaeger++;
            }
            if (aktion == 2) {
                xSchlaeger--;
            }
            if (xSchlaeger < 0) {
                xSchlaeger = 0;
            }
            if (xSchlaeger > xSchlaegerSchritteMaximal) {
                xSchlaeger = xSchlaegerSchritteMaximal;
            }

            xBall += xBallGeschwindigkeit;
            yBall += yBallGeschwindigkeit;
            if (xBall > xBallSchritteMaximal - 1 || xBall < 1) {
                xBallGeschwindigkeit = -xBallGeschwindigkeit;
            }
            if (yBall > yBallSchritteMaximal - 1 || yBall < 1) {
                yBallGeschwindigkeit = -yBallGeschwindigkeit;
            }
            int belohnung = 0;
            //schritt vorbei, checken ob  ball unten ankam und ob schläger getroffen wurde. Wenn ja dann belohne mit 1 .
            if (yBall == yBallSchritteMaximal) {
                if (xSchlaeger == xBall || xSchlaeger == xBall - 1 || xSchlaeger == xBall - 2) {
                    belohnung = 1;
                    System.out.println("positive reward " + score);
                    ++score;
                } else {
                    belohnung = 0;
                    System.out.println("negative reward " + score);
                    --score;
                }
                times++;
            }
            
            

            Status statusDanach = new Status(xBall, yBall, xBallGeschwindigkeit, yBallGeschwindigkeit, xSchlaeger);
            //lerne anhand des ergebnisses der aktion
            agent.lernen(statusVorher, statusDanach, aktion, belohnung);
            //wenn eingelernt zeige ball etc.
            if (times > 100000) {
                try {
                    Thread.sleep(100);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                repaint();
                validate();
            }

        }

        setVisible(false);
        dispose();
    }

    public void mouseReleased(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mousePressed(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseExited(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseEntered(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseClicked(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseMoved(MouseEvent e) {
        // System.out.println(e.toString());
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseDragged(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void keyTyped(KeyEvent e) {
        key = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e) {
        key = e.getKeyCode();
    }

    public void keyPressed(KeyEvent e) {
        System.out.println(e.toString());
    }

    /**
     * Construct main frame
     *
     * @param args passed to MainFrame
     */
    public static void main(String[] args) {
        new MainFrame(args);
    }
}
