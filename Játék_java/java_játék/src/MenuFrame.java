import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A játék menüjéért felelős megjelenítő frame
 */
public class MenuFrame extends JFrame {
    private JTextField enginerJTxt = new JTextField();
    private JTextField enginerCountIn = new JTextField(); //Szerelők száma
    private JTextField saboteurJTxt = new JTextField();
    private JTextField saboteurCountIn = new JTextField(); //Szabotőrök száma

    /**
     * Csak számokat enged beírni a JTextFieldbe
     * A DocumentFilter felülírása
     */
    final static class NumericDocumentFilter extends DocumentFilter {
        /**
         * @param fb     FilterBypass that can be used to mutate Document
         * @param offset the offset into the document to insert the content &gt;= 0.
         *               All positions that track change at or after the given location
         *               will move.
         * @param text   the string to insert
         * @param attrs  the attributes to associate with the inserted
         *               content.  This may be null if there are no attributes.
         * @throws BadLocationException
         */
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            super.insertString(fb, offset, sb.toString(), attrs);
        }

        /**
         * @param fb     FilterBypass that can be used to mutate Document
         * @param offset Location in Document
         * @param length Length of text to delete
         * @param text   Text to insert, null indicates no text to insert
         * @param attrs  AttributeSet indicating attributes of inserted text,
         *               null is legal.
         * @throws BadLocationException
         */
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                super.replace(fb, offset, length, text, attrs);
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            super.replace(fb, offset, length, sb.toString(), attrs);
        }
    }

    /**
     * A menü ablak konstruktora
     */
    MenuFrame(){
        super("Menu");
        setResizable(false);
        setSize(800, 551);
        setResizable(false);

        JPanel panel = new JPanel(){
            Image image;
            BufferedImage myImage = null;

            /**
             * Háttér kirajzolása
             * @param g the <code>Graphics</code> object to protect
             */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try{
                    myImage = ImageIO.read(new File("menuBack.jpg"));
                }catch(IOException e){}
                image = myImage;
                g.drawImage(image, 0, 0, this);
            }
        };
        panel.setLayout(null);

        ImageIcon StartButtonImage = new ImageIcon("startButton.png"); //Start gomb
        JButton startButton = new JButton(StartButtonImage); //Gomb képe
        startButton.setBounds(150, 50, 480, 210);
        startButton.setBorder(null);
        panel.add(startButton);
        startButton.addActionListener(new startGameListener());

        enginerJTxt.setText("Szerelők száma:");
        enginerJTxt.setBackground(Color.gray);
        enginerJTxt.setFont(new Font("Arial", Font.BOLD, 30));
        enginerJTxt.setBounds(155, 300, 370, 80);
        enginerJTxt.setEditable(false);
        enginerJTxt.setBorder(null);

        enginerCountIn.setText(""); //Alapból üres, ide kell beadni a szerelők számát
        enginerCountIn.setBackground(Color.gray);
        enginerCountIn.setFont(new Font("Arial", Font.BOLD, 30));
        enginerCountIn.setBounds(155+370, 300, 100, 80);
        enginerCountIn.setBorder(null);
        ((AbstractDocument) enginerCountIn.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        saboteurJTxt.setText("Szabotőrök száma:");
        saboteurJTxt.setBackground(Color.gray);
        saboteurJTxt.setFont(new Font("Arial", Font.BOLD, 30));
        saboteurJTxt.setBounds(155, 400, 370, 80);
        saboteurJTxt.setEditable(false);
        saboteurJTxt.setBorder(null);

        saboteurCountIn.setText(""); //Alapból üres, ide kell beadni a szabotőrök számát
        saboteurCountIn.setBackground(Color.gray);
        saboteurCountIn.setFont(new Font("Arial", Font.BOLD, 30));
        saboteurCountIn.setBounds(155+370, 400, 100, 80);
        saboteurCountIn.setBorder(null);
        ((AbstractDocument) saboteurCountIn.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        panel.add(enginerJTxt);
        panel.add(saboteurJTxt);
        panel.add(enginerCountIn);
        panel.add(saboteurCountIn);
        this.getContentPane().add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * A játék indításáért felelős
     */
    final class startGameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int eC = Integer.parseInt(enginerCountIn.getText());
                int sC = Integer.parseInt(saboteurCountIn.getText());
                if(!enginerCountIn.getText().equals("") && !saboteurCountIn.getText().equals("") && eC > 0 && eC < 9 && sC > 0 && sC < 9){ //Csak ha a szabotőrök és a szerelők száma meg van adva és [1,8]
                    Game.roundsCount = 12;
                    new MainFrame(Integer.parseInt(enginerCountIn.getText()), Integer.parseInt(saboteurCountIn.getText()));
                    dispose();
                }
            }catch (Exception x){}


        }
    }
}
