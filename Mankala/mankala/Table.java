import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Table extends JFrame {
    //Privát tagváltozók definiálása
    private JButton a = new JButton(" 0");
    private JButton a1 = new JButton("5");
    private JButton a2 = new JButton("5");
    private JButton a3 = new JButton("5");
    private JButton a4 = new JButton("5");
    private JButton a5 = new JButton("5");
    private JButton a6 = new JButton("5");
    private JButton b = new JButton("0");
    private JButton b1 = new JButton("5");
    private JButton b2 = new JButton("5");
    private JButton b3 = new JButton("5");
    private JButton b4 = new JButton("5");
    private JButton b5 = new JButton("5");
    private JButton b6 = new JButton("5");
    private JButton surrA = new JButton("Feladom");
    private JButton surrB = new JButton("Feladom");
    private JButton save = new JButton("Mentés");

    private JTextField playerAName = new JTextField("1. játékos");
    private JTextField playerBName = new JTextField("2. játékos");
    private JTextField nextPlayer = new JTextField(playerAName.getText() + " kezd!");
    private Init initialize = new Init(this);
    private StoreElement se;
    private boolean nextplayer = true;
    private boolean multiplayerbool = false;





    Table(boolean cont, boolean multi){
        super("Mankala table");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        multiplayerbool = multi;
        if(cont == false){
            se = initialize.storeInit();
            nextPlayer.setText("Kezdés a tábla alsó felén!");
        }
        else {
            try{
                FileInputStream fis = new FileInputStream("save.txt");
                ObjectInputStream in = new ObjectInputStream(fis);
                se = (StoreElement) in.readObject();
                nextplayer = se.getNextPlayer();
                Game g = new Game();
                g.ertekKiir(se);
                a = se.getAElementAt(0).getJb();
                a1 = se.getAElementAt(1).getJb();
                a2 = se.getAElementAt(2).getJb();
                a3 = se.getAElementAt(3).getJb();
                a4 = se.getAElementAt(4).getJb();
                a5 = se.getAElementAt(5).getJb();
                a6 = se.getAElementAt(6).getJb();
                b = se.getBElementAt(0).getJb();
                b1 = se.getBElementAt(1).getJb();
                b2 = se.getBElementAt(2).getJb();
                b3 = se.getBElementAt(3).getJb();
                b4 = se.getBElementAt(4).getJb();
                b5 = se.getBElementAt(5).getJb();
                b6 = se.getBElementAt(6).getJb();
                if (nextplayer) {
                    nextPlayer.setText(playerAName.getText() + " következik!");
                } else {
                    nextPlayer.setText(playerBName.getText() + " következik!");
                }
            }catch (Exception exc){
                System.out.println("hiba");
            }
        }

        a.setText(Integer.toString(se.getAElementAt(0).getErtek()));
        a1.setText(Integer.toString(se.getAElementAt(1).getErtek()));
        a2.setText(Integer.toString(se.getAElementAt(2).getErtek()));
        a3.setText(Integer.toString(se.getAElementAt(3).getErtek()));
        a4.setText(Integer.toString(se.getAElementAt(4).getErtek()));
        a5.setText(Integer.toString(se.getAElementAt(5).getErtek()));
        a6.setText(Integer.toString(se.getAElementAt(6).getErtek()));
        b.setText(Integer.toString(se.getBElementAt(0).getErtek()));
        b1.setText(Integer.toString(se.getBElementAt(1).getErtek()));
        b2.setText(Integer.toString(se.getBElementAt(2).getErtek()));
        b3.setText(Integer.toString(se.getBElementAt(3).getErtek()));
        b4.setText(Integer.toString(se.getBElementAt(4).getErtek()));
        b5.setText(Integer.toString(se.getBElementAt(5).getErtek()));
        b6.setText(Integer.toString(se.getBElementAt(6).getErtek()));
        setSize(600,600);
        setResizable(true);
        setLayout(new GridLayout(3,1));
        JPanel tablaPluszInfo = new JPanel();
        tablaPluszInfo.setLayout(new GridLayout(1, 8));
        JPanel szelA = new JPanel();
        JPanel szelB = new JPanel();
        szelA.setLayout(new GridLayout(1,1));
        szelB.setLayout(new GridLayout(1,1));
        JPanel oszlop1 = new JPanel();
        JPanel oszlop2 = new JPanel();
        JPanel oszlop3 = new JPanel();
        JPanel oszlop4 = new JPanel();
        JPanel oszlop5 = new JPanel();
        JPanel oszlop6 = new JPanel();
        oszlop1.setLayout(new GridLayout(2,1));
        oszlop2.setLayout(new GridLayout(2,1));
        oszlop3.setLayout(new GridLayout(2,1));
        oszlop4.setLayout(new GridLayout(2,1));
        oszlop5.setLayout(new GridLayout(2,1));
        oszlop6.setLayout(new GridLayout(2,1));
        szelA.add(a);
        szelB.add(b);
        oszlop1.add(b6);
        oszlop1.add(a1);
        oszlop2.add(b5);
        oszlop2.add(a2);
        oszlop3.add(b4);
        oszlop3.add(a3);
        oszlop4.add(b3);
        oszlop4.add(a4);
        oszlop5.add(b2);
        oszlop5.add(a5);
        oszlop6.add(b1);
        oszlop6.add(a6);
        tablaPluszInfo.add(szelA);
        tablaPluszInfo.add(oszlop1);
        tablaPluszInfo.add(oszlop2);
        tablaPluszInfo.add(oszlop3);
        tablaPluszInfo.add(oszlop4);
        tablaPluszInfo.add(oszlop5);
        tablaPluszInfo.add(oszlop6);
        tablaPluszInfo.add(szelB);

        nextPlayer.setFont(new Font("Courier", Font.BOLD, 20));
        nextPlayer.setEditable(false);
        playerAName.setEditable(false);
        playerAName.setFont(new Font("Courier", Font.BOLD, 20));
        playerBName.setFont(new Font("Courier", Font.BOLD, 20));
        playerBName.setEditable(false);
        JPanel playerAPanel = new JPanel();
        JPanel playerBPanel = new JPanel();
        playerBPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100,120));
        playerAPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100,30));
        playerAPanel.add(playerAName, BorderLayout.WEST);
        playerAPanel.add(surrA, BorderLayout.WEST);
        playerBPanel.add(playerBName, BorderLayout.WEST);
        if(!multiplayerbool){
            playerBPanel.add(surrB, BorderLayout.WEST);
        }
        JPanel down = new JPanel();
        down.setLayout(new GridLayout(2,1));
        JPanel info = new JPanel();
        info.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 30));
        info.add(save, BorderLayout.EAST);
        info.add(nextPlayer);
        down.add(playerAPanel);
        down.add(info);
        add(playerBPanel);
        add(tablaPluszInfo);
        add(down);
        a1.addActionListener(new ButtonListener());
        a2.addActionListener(new ButtonListener());
        a3.addActionListener(new ButtonListener());
        a4.addActionListener(new ButtonListener());
        a5.addActionListener(new ButtonListener());
        a6.addActionListener(new ButtonListener());
        if(!multi) {
            b1.addActionListener(new ButtonListener());
            b2.addActionListener(new ButtonListener());
            b3.addActionListener(new ButtonListener());
            b4.addActionListener(new ButtonListener());
            b5.addActionListener(new ButtonListener());
            b6.addActionListener(new ButtonListener());
            surrB.addActionListener(new SurrButtonListener());
        }
        surrA.addActionListener(new SurrButtonListener());
        save.addActionListener(new SaveButtonListener());
    }

    //Getterek és setterek
    public void setPlayerAName(String s){
        playerAName.setText(s);
    }

    public void setPlayerBName(String s){
        playerBName.setText(s);
    }
    public void setNextPlayer(boolean b){
        nextplayer = b;
    }
    public JButton getA() {
        return a;
    }

    public JButton getA1() {
        return a1;
    }

    public JButton getA2() {
        return a2;
    }

    public JButton getA3() {
        return a3;
    }

    public JButton getA4() {
        return a4;
    }

    public JButton getA5() {
        return a5;
    }

    public JButton getA6() {
        return a6;
    }

    public JButton getB() {
        return b;
    }
    public JButton getB1() {
        return b1;
    }

    public JButton getB2() {
        return b2;
    }

    public JButton getB3() {
        return b3;
    }

    public JButton getB4() {
        return b4;
    }

    public JButton getB5() {
        return b5;
    }

    public JButton getB6() {
        return b6;
    }



    //feladást megvalósító gomb
    final class SurrButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof JButton) {
                JButton btn = (JButton) source;
                if(btn.equals(surrA)){
                    JOptionPane.showConfirmDialog(null,
                            playerBName.getText() + " nyert", "Game Over", JOptionPane.DEFAULT_OPTION);
                }
                else {
                    JOptionPane.showConfirmDialog(null,
                            playerAName.getText() + " nyert", "Game Over", JOptionPane.DEFAULT_OPTION);
                }
                Menu menu = new Menu();
                menu.setVisible(true);
                dispose();


            }

        }
    }
    final class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof JButton) {
                Lyuk ly = new Lyuk();
                JButton btn = (JButton) source;
                ly = ly.getLyuk(btn, se);
                if (ly.getErtek() != 0) {
                    if (!nextplayer) {
                        if (btn.equals(a1) || btn.equals(a2) || btn.equals(a3) || btn.equals(a4) || btn.equals(a5) || btn.equals(a6)) {

                        }
                        else {
                            ly = ly.getLyuk(btn, se);
                            Game g = new Game();
                            g.pressed(btn, se);
                            if (g.checkEndGame(se)) {
                                JOptionPane.showConfirmDialog(null,
                                        playerBName.getText() + " nyert", "Game Over", JOptionPane.DEFAULT_OPTION);
                                Menu menu = new Menu();
                                menu.setVisible(true);
                                dispose();
                                return;
                            }
                            if (g.getLast().equals(se.getAElementAt(0))) {

                            } else {
                                nextplayer = !nextplayer;

                            }
                            if (nextplayer) {
                                nextPlayer.setText(playerAName.getText() + " következik!");
                            } else {
                                nextPlayer.setText(playerBName.getText() + " következik!");
                            }
                        }
                    } else {
                        if (btn.equals(b1) || btn.equals(b2) || btn.equals(b3) || btn.equals(b4) || btn.equals(b5) || btn.equals(b6)) {

                        } else {
                            ly = ly.getLyuk(btn, se);
                            Game g = new Game();
                            g.pressed(btn, se);
                            if (g.checkEndGame(se)) {
                                JOptionPane.showConfirmDialog(null,
                                        playerAName.getText() + " nyert", "Game Over", JOptionPane.DEFAULT_OPTION);
                                Menu menu = new Menu();
                                menu.setVisible(true);
                                dispose();
                                return;
                            }
                            if (g.getLast().equals(se.getBElementAt(0))) {

                            } else {
                                nextplayer = !nextplayer;

                            }
                            if (nextplayer) {
                                nextPlayer.setText(playerAName.getText() + " következik!");
                            } else {
                                nextPlayer.setText(playerBName.getText() + " következik!");
                            }
                        }
                    }



                }
            }

            if(multiplayerbool){
                Game g = new Game();
                Gep gep = new Gep();

                while(!nextplayer) {

                    JButton btn = gep.mitlep(se);
                    g.pressed(btn, se);
                    if (g.checkEndGame(se)) {
                        JOptionPane.showConfirmDialog(null,
                                playerBName.getText() + " nyert", "Game Over", JOptionPane.DEFAULT_OPTION);
                        Menu menu = new Menu();
                        menu.setVisible(true);
                        dispose();
                        return;
                    }
                    if (g.getLast().equals(se.getAElementAt(0))) {

                    } else {
                        nextplayer = !nextplayer;

                    }
                    if (nextplayer) {
                        nextPlayer.setText(playerAName.getText() + " következik!");
                    } else {
                        nextPlayer.setText(playerBName.getText() + " következik!");
                    }
                }
            }



        }
    }
    final class SaveButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            se.setNextPlayer(nextplayer);
            FileOutputStream fos;
            try{
                Game g = new Game();
                fos = new FileOutputStream("save.txt");
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(se);
                out.close();

            }catch (Exception exc){
                System.out.println("Nem megfelelő file");
            }


        }
    }





}
