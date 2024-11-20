import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/** A Source megjelenítéséért felelős osztály
 */
public class SourceDrawer extends JPanel implements Drawable{
    Source source;
    private JLabel name = new JLabel();

    /**
     * SourceDrawer konstruktora
     * @param s forrás amelyet rajzolni szeretnénk
     */
    public SourceDrawer(Source s){
        source = s;
        this.add(name);
        name.setText(String.valueOf(source.getID()));
    }

    /**
     * JPanel paintComponent felüldefiniálása, forrás rajzolását végzi
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        this.setBounds(source.x, source.y, 50, 50);
        super.paintComponent(g);
        this.setBackground(null);
        BufferedImage img;
        try {
            img = ImageIO.read(new File("source.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(img, 0, 0, null);
        if(source.hasPlayers()){
            for(Player p : source.getPlayers()){
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
     * Hozzáad egy MouseListener-t a JPanel-hez.
     */
    public void addMouseListener(MouseListener me){
        super.addMouseListener(me);
    }

    /**
     * Drawable interface draw megvalósítása, meghívja az objektumon a repaint-et
     */
    @Override
    public void draw() {
        this.repaint();
    }

    /**
     * Visszaadja az eltárolt objektumot.
     * @return IPipeSystem pipe
     */
    public IPipeSystem getObject(){
        return source;
    }
}
