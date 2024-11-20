import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Pumpa megjelenítő osztály
 */
public class PumpDrawer extends JPanel implements Drawable{
    Pump pump; //a megjelenítendő pumpa
    private JLabel name = new JLabel();

    /**
     * Konstruktor
     * @param p Megjelenítendő pumpa
     */
    public PumpDrawer(Pump p){
        pump = p;
        this.add(name);
        name.setText(String.valueOf(pump.getID()));
    }

    /**
     * Pumpa kirajzolása, az állapotának megfelelően
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {

        if(pump.x >= 0 && pump.y >= 0){
            this.setBounds(pump.x, pump.y, 50, 50);
            super.paintComponent(g);
            this.setBackground(null);
            BufferedImage img;
            try {
                img = ImageIO.read(new File("pumpa.png")); //Pumpa képe
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(img, 0, 0, null);
            if (pump.getBroken()) { //Ha törött, annak megjelenítése
                try {
                    BufferedImage ho = ImageIO.read(new File("hole.png"));
                    g.drawImage(ho, 11, 6, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (pump.hasPlayers()) { //Ha áll rajta játékos azok megjelenítése a pumpán
                int tolX = 0;
                int tolY = 0;
                for (Player p : pump.getPlayers()) {
                    try {
                        String image;
                        if (p.getID() > 100 && p == Game.currPlayer) {
                            image = "lilEngineerNext.png";
                        } else if (p.getID() > 100) {
                            image = "lilEngineer.png";
                        } else if (p.getID() < 100 && p == Game.currPlayer) {
                            image = "lilSaboteurNext.png";
                        } else {
                            image = "lilSaboteur.png";
                        }
                        BufferedImage player = ImageIO.read(new File(image));
                        g.drawImage(player, tolX, tolY, null);
                        if (tolX < 60) {
                            tolX += 20;
                        } else {
                            tolY += 20;
                            tolX = 0;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * Egérkattintás hozzáadása
     * @param me MouseListener
     */
    public void addMouseListener(MouseListener me){
        super.addMouseListener(me);
    }

    /**
     * Kirajzolás megvalósítása
     */
    @Override
    public void draw() {
        this.repaint();
    }

    /**
     * Visszaadja a hozzátartozó pumpát
     * @return a kirajzolt pumpa objektuma
     */
    public IPipeSystem getObject(){
        return pump;
    }
}
