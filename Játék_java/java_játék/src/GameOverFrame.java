import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A játék végeztével megjelenő ablak
 * Innen ki lehet lépni
 */
public class GameOverFrame extends JFrame {
    /**
     * A játék végeztével Kiírja a nyertes csapatot, vagy ha döntetlen lett akkor azt és lehetőséget biztosít kilépésre
     */
    GameOverFrame(){
        super("GameOver");
        setResizable(false);
        setSize(250,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel label;
        if(Game.gainedWater < Game.lostWater){
            label = new JLabel("Szabotőrök nyertek!", SwingConstants.CENTER); //Szabotőrök gyözelmének megjelenítése
            label.setFont(new Font("Arial", Font.BOLD, 18 ));
            label.setForeground(Color.RED);

        }
        else if(Game.gainedWater > Game.lostWater){
            label = new JLabel("Szerelők nyertek!", SwingConstants.CENTER); //Szerelők gyözelmének megjelenítése
            label.setFont(new Font("Arial", Font.BOLD, 18 ));
            label.setForeground(Color.GREEN);
        }
        else{
            label = new JLabel("Döntetlen", SwingConstants.CENTER); //Döntetlen végeredmény megjelenítése
            label.setPreferredSize(new Dimension(250, 70));
            label.setFont(new Font("Arial", Font.BOLD, 18 ));
            label.setForeground(Color.BLUE);
        }
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.add(label,BorderLayout.NORTH);
        this.getContentPane().add(panel);
        setLocationRelativeTo(null);
        JButton b1 = new JButton("Kilépés");
        panel.add(b1, BorderLayout.SOUTH);
        b1.setBackground(Color.WHITE);
        b1.setFocusPainted(false);
        b1.setFont(new Font("Arial", Font.BOLD, 18 ));
        b1.addActionListener(new ButtonListener());
        setVisible(true);
        setResizable(false);

    }

    /**
     * Kilépés gomb
     */
    final class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

}
