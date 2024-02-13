import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/** A Pipe megjelenítéséért felelős osztály
*/
public class PipeDrawer extends JPanel implements Drawable{
    Pipe pipe;
    int lngth = 0;
    private List<Point2D> closests ;
    private JPanel playerPanel;

    private JLabel name = new JLabel();

    /**
    * PipeDrawer konstruktora
    * @param p cső amelyet rajzolni szeretnénk
    */
    public PipeDrawer(Pipe p){
        pipe = p;
        closests = new ArrayList<Point2D>();
        closests.add(null);
        closests.add(null);
        this.add(name);

    }


    /**
     * JPanel paintComponent felüldefiniálása, cső rajzolását végzi
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        int width = 50;



        if(pipe.getPipeEnds().get(0) != null || pipe.getPipeEnds().get(1) != null) {
            name.setText(String.valueOf(pipe.getID()));

            if (pipe.getPipeEnds().get(0) == null || pipe.getPipeEnds().get(1) == null) {


                if (pipe.getPipeEnds().get(0) == null) {

                    if (pipe.irany.equals("fel")) {
                        this.setBounds(pipe.getPipeEnds().get(1).x, pipe.getPipeEnds().get(1).y - pipe.getLength() - 1, width, pipe.getLength());
                    }
                    if (pipe.irany.equals("jobb")) {
                        this.setBounds(pipe.getPipeEnds().get(1).x + 51, pipe.getPipeEnds().get(1).y, pipe.getLength(), width);
                    }
                    if (pipe.irany.equals("le")) {
                        this.setBounds(pipe.getPipeEnds().get(1).x, pipe.getPipeEnds().get(1).y + 1 , width, pipe.getLength());
                    }
                    if (pipe.irany.equals("bal")) {
                        this.setBounds(pipe.getPipeEnds().get(1).x - 1 - pipe.getLength(), pipe.getPipeEnds().get(1).y, pipe.getLength(), width);
                    }

                } else if (pipe.getPipeEnds().get(1) == null) {

                    if (pipe.irany.equals("fel")) {
                        this.setBounds(pipe.getPipeEnds().get(0).x, pipe.getPipeEnds().get(0).y - pipe.getLength() - 1, width, pipe.getLength());
                        //System.out.println("fel");
                    }
                    if (pipe.irany.equals("jobb")) {
                        this.setBounds(pipe.getPipeEnds().get(0).x + 51, pipe.getPipeEnds().get(0).y, pipe.getLength(), width);
                        //System.out.println("jobb");
                    }
                    if (pipe.irany.equals("le")) {
                        this.setBounds(pipe.getPipeEnds().get(0).x, pipe.getPipeEnds().get(0).y + 51, width, pipe.getLength());
                        //System.out.println("le");
                    }
                    if (pipe.irany.equals("bal")) {
                        this.setBounds(pipe.getPipeEnds().get(0).x - 1 - pipe.getLength(), pipe.getPipeEnds().get(0).y, pipe.getLength(), width);
                        //System.out.println("bal");
                    }
                }
            } else {
                returnclosestCorner(pipe.getPipeEnds().get(0).x, pipe.getPipeEnds().get(0).y, 50, 50, pipe.getPipeEnds().get(1).x, pipe.getPipeEnds().get(1).y, 50, 50);
                if (pipe.getPipeEnds().get(0).x == pipe.getPipeEnds().get(1).x && pipe.getPipeEnds().get(0).y > pipe.getPipeEnds().get(1).y) {
                    this.setBounds((int) closests.get(1).getX() + 1, (int) closests.get(1).getY() + 1, width, lngth);
                    //System.out.println("fel");
                } else if (pipe.getPipeEnds().get(0).x > pipe.getPipeEnds().get(1).x && pipe.getPipeEnds().get(0).y == pipe.getPipeEnds().get(1).y) {
                    this.setBounds((int) closests.get(1).getX() + 1, (int) closests.get(1).getY() + 1, lngth, width);
                    //System.out.println("jobbra");

                } else if (pipe.getPipeEnds().get(0).x == pipe.getPipeEnds().get(1).x && pipe.getPipeEnds().get(0).y < pipe.getPipeEnds().get(1).y) {
                    this.setBounds((int) closests.get(0).getX() + 1, (int) closests.get(0).getY() + 1, width, lngth);
                    //System.out.println("le");

                } else if (pipe.getPipeEnds().get(0).x < pipe.getPipeEnds().get(1).x && pipe.getPipeEnds().get(0).y == pipe.getPipeEnds().get(1).y) {
                    this.setBounds((int) closests.get(0).getX() + 1, (int) closests.get(0).getY() + 1, lngth, width);
                    //System.out.println("balra");

                }
            }
            super.paintComponent(g);


            Color pipeBorderColor;
            int cR, cG, cB;
            if (!pipe.getWater()) {
                name.setForeground(Color.black);
                this.setBackground(Color.GRAY);
            } else {
                name.setForeground(Color.black);
                this.setBackground(Color.cyan);
            }

            if (pipe.isBroken()) {
                cR = 255;
            } else {
                cR = 0;
            }

            if (pipe.getSticky() > 0) {
                cG = 255;
            } else {
                cG = 0;
            }

            if (pipe.getSlippery() > 0) {
                cB = 255;
            } else {
                cB = 0;
            }
            pipeBorderColor = new Color(cR, cG, cB);
            this.setBorder(BorderFactory.createLineBorder(pipeBorderColor, 6));
            if (pipe.hasPlayer()) {
                try {
                    String image;
                    if (pipe.getPlayer().getID() > 100 && pipe.getPlayer() == Game.currPlayer){
                        image = "engineerNext.png";
                    }
                    else if (pipe.getPlayer().getID() > 100){
                        image = "engineer.png";
                    }
                    else if (pipe.getPlayer().getID() < 100 && pipe.getPlayer() == Game.currPlayer){
                        image = "saboteurNext.png";
                    }
                    else{
                        image = "saboteur.png";
                    }

                    BufferedImage player = ImageIO.read(new File(image));
                    if (pipe.irany.equals("fel") || pipe.irany.equals("le") || pipe.irany.equals("ver")) {
                        g.drawImage(player, -5, lngth / 3, null);
                    } else {
                        g.drawImage(player, lngth / 3, 0, null);

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Drawable interface draw megvalósítása, meghívja az objektumon a repaint-et
     */
    @Override
    public void draw() {
        this.repaint();
    }

    /**
     * Beállítja a cső kirajzolási hosszát
     * @param x1 cső egyik végének x koordinátája
     * @param y1 cső egyik végének y koordinátája
     * @param width1 cső egyik végének hossza
     * @param height1 cső egyik végének magassága
     * @param x2 cső másik végének x koordinátája
     * @param y2 cső másik végének y koordinátája
     * @param width2 cső másik végének hossza
     * @param height2 cső másik végének magassága
     */
    private void returnclosestCorner(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2){
        Point2D rect1side1 = new Point2D.Double(x1, y1);
        Point2D rect1side2 = new Point2D.Double(x1+width1 , y1);
        Point2D rect1side3 = new Point2D.Double(x1, y1+height1);
        Point2D rect1side4 = new Point2D.Double(x1, y1);

        Point2D rect2side1 = new Point2D.Double(x2, y2);
        Point2D rect2side2 = new Point2D.Double(x2+width2, y2);
        Point2D rect2side3 = new Point2D.Double(x2, y2+height2);
        Point2D rect2side4 = new Point2D.Double(x2, y2);

        double d = Integer.MAX_VALUE;

        List<Point2D> panel1 = new ArrayList<>();
        panel1.add(rect1side1);
        panel1.add(rect1side2);
        panel1.add(rect1side3);
        panel1.add(rect1side4);
        List<Point2D> panel2 = new ArrayList<>();
        panel2.add(rect2side1);
        panel2.add(rect2side2);
        panel2.add(rect2side3);
        panel2.add(rect2side4);

        for(Point2D oldal1: panel1){
            for(Point2D oldal2: panel2){
                if(oldal1.distance(oldal2) < d){
                    d = oldal1.distance(oldal2);
                    closests.set(0, oldal1);
                    closests.set(1, oldal2);
                }
            }
        }
        lngth = (int) d-1;
        pipe.setLength((int) d);
    }
    /**
     * Hozzáad egy MouseListener-t a JPanel-hez.
     */
    public void addMouseListener(MouseListener me){
        super.addMouseListener(me);
    }
    /**
     * Visszaadja az eltárolt objektumot.
     * @return IPipeSystem pipe
     */
    public IPipeSystem getObject(){
        return pipe;
    }
}
