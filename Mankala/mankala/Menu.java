import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton newGame = new JButton("New Game");
    private JButton loadGame = new JButton("Load Game");

    private JTextField addjamegA = new JTextField("Adja meg a nevét:");
    private JTextField addjamegB = new JTextField("Adja meg a nevét:");

    private JTextField playerAName = new JTextField("1. játékos");
    private JTextField playerBName = new JTextField("2. játékos");
    private String[] array = {"Multiplayer", "Singleplayer"};
    private JComboBox<String> multiplayercombo = new JComboBox<>(array);
    private boolean multiplayerbool;
    Menu(){
        super("Mankala menu");
        setSize(300, 300);
        setResizable(false);
        addjamegA.setEditable(false);
        addjamegB.setEditable(false);
        setLayout(new GridLayout(3, 1));
        JPanel felso = new JPanel();
        felso.setLayout(new FlowLayout());
        JPanel kozepso = new JPanel();
        kozepso.setLayout(new FlowLayout());
        JPanel also = new JPanel();
        also.setLayout(new FlowLayout());
        also.add(newGame);
        also.add(loadGame);
        also.add(multiplayercombo);
        felso.add(addjamegA);
        felso.add(playerAName);
        kozepso.add(addjamegB);
        kozepso.add(playerBName);
        add(felso);
        add(kozepso);
        add(also);
        addjamegA.setFont(new Font("Courier", Font.BOLD, 20));
        addjamegB.setFont(new Font("Courier", Font.BOLD, 20));
        playerAName.setFont(new Font("Courier", Font.BOLD, 20));
        playerBName.setFont(new Font("Courier", Font.BOLD, 20));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newGame.addActionListener(new newGameListener());
        loadGame.addActionListener(new loadGameListener());








    }

    final class newGameListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(multiplayercombo.getSelectedItem().equals("Multiplayer")){
                multiplayerbool = false;
            }
            else{
                multiplayerbool = true;
            }
            Table table = new Table(false, multiplayerbool);
            table.setVisible(true);
            table.setPlayerAName(playerAName.getText());
            table.setPlayerBName(playerBName.getText());
            if(multiplayerbool){
                table.setPlayerBName("Gép");
            }
            dispose();
        }

    }

    final class loadGameListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(multiplayercombo.getSelectedItem().equals("Multiplayer")){
                multiplayerbool = false;
            }
            else{
                multiplayerbool = true;
            }
            Table table = new Table(true, multiplayerbool);
            table.setVisible(true);
            table.setPlayerAName(playerAName.getText());
            table.setPlayerBName(playerBName.getText());
            if(multiplayerbool){
                table.setPlayerBName("Gép");
            }
            dispose();
        }

    }
}
