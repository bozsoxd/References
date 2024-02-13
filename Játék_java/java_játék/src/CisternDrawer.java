import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Egy ciszterna kirajzolásáért felelős osztály
 */
public class CisternDrawer extends JPanel implements Drawable{
    /**
     * A hozzákötött ciszterna
     */
    Cistern cistern;
    private JLabel name = new JLabel();

    /**
     * @param c A reprezentát ciszterna
     */
    public CisternDrawer(Cistern c){
        cistern = c;
        this.add(name);
        name.setText(String.valueOf(cistern.getID()));
    }

    /**
     * Az x, y koordinátájában a játék panelnek kirajzol egy ciszternát
     * Ha a ciszternán áll player, akkor azt megjeleníti a ciszternán
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        this.setBounds(cistern.x, cistern.y, 50, 50);
        super.paintComponent(g);
        this.setBackground(null);
        BufferedImage img;
        try {
            img = ImageIO.read(new File("cistern.png")); //Ciszterna képének beolvasása
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(cistern.getNewPumpsCount() > 0){
            try{
                String LilPumpImage = "lilPump.png";
                BufferedImage newPump = ImageIO.read(new File(LilPumpImage));
                g.drawImage(newPump, 0, 30, null);
            }catch (Exception x){}

        }
        g.drawImage(img, 0, 0, null); //Kép kirajzolása
        if(cistern.hasPlayers()){
            for(Player p : cistern.getPlayers()){
                try {
                    int tolX = 0;
                    int tolY = 0;
                    String image;
                    if(p.getID() > 100 && p == Game.currPlayer) {
                        image = "lilEngineerNext.png";
                    } else if (p.getID() > 100) {
                        image = "lilEngineer.png";
                    }
                    else if(p.getID() < 100 && p == Game.currPlayer) {
                        image = "lilSaboteurNext.png";
                    }
                    else {
                        image = "lilSaboteur.png";
                    }
                    BufferedImage player = ImageIO.read(new File(image));
                    g.drawImage(player, tolX, tolY, null);
                    if(tolX < 60){
                        tolX += 20;
                    }
                    else{
                        tolY += 20;
                        tolX = 0;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @param me the mouse listener
     */
    public void addMouseListener(MouseListener me){
        super.addMouseListener(me);
    }

    /**
     * Újra rajzol
     */
    @Override
    public void draw() {
        this.repaint();
    }

    /**
     * @return A reprezentált ciszterna
     */
    public IPipeSystem getObject(){
        return cistern;
    }
}
