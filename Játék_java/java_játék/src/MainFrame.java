import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private List<Drawable> list = new ArrayList<>();
    private int engineersCount;
    private int saboteursCount;
    private JLabel saboteurPointCount;
    private JLabel engineerPointCount;
    private JLabel remainingRounds;
    private JLabel log;
    public static JLabel nextPlayer;
    private JButton atiranyitas;
    private JButton lyukasztas;
    private JButton javitas;
    private JButton sikositas;
    private JButton ragasztas;
    private JButton felvetel;
    private JButton lerakas;
    private JButton skipRound;

    int playersc;
    int one;
    static boolean voltlepes = false;
  
    MainFrame(int ec, int sc){
        super("Game");
        playersc = ec+sc;
        setResizable(false);
        setSize(1500, 900);
        setResizable(false);
        engineersCount = ec;
        saboteursCount = sc;
        Game.oneround = playersc;
        one = Game.oneround;

        Game.init(engineersCount, saboteursCount);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        engineerPointCount = new JLabel("Szerelők: " + Game.gainedWater);
        saboteurPointCount = new JLabel("Szabotőrök: " + Game.lostWater);
        remainingRounds = new JLabel("Hátralévő körök: "+ 12);
        log = new JLabel("log: ");
        nextPlayer = new JLabel(Game.getNextPlayer().getName() + " következik");

        engineerPointCount.setFont(new Font("Arial", Font.PLAIN, 20));
        saboteurPointCount.setFont(new Font("Arial", Font.PLAIN, 20));
        remainingRounds.setFont(new Font("Arial", Font.PLAIN, 20));
        log.setFont(new Font("Arial", Font.PLAIN, 20));
        nextPlayer.setFont(new Font("Arial", Font.PLAIN, 20));

        atiranyitas = new JButton("Átirányítás");
        atiranyitas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        atiranyitas.setForeground(Color.WHITE);
        atiranyitas.setBorderPainted(false);
        atiranyitas.setFocusPainted(false);
        enableButton(atiranyitas);

        lyukasztas = new JButton("Lyukasztás ");
        lyukasztas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        lyukasztas.setForeground(Color.WHITE);
        lyukasztas.setBorderPainted(false);
        lyukasztas.setFocusPainted(false);
        disableButton(lyukasztas);

        javitas = new JButton("Javítás    ");
        javitas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        javitas.setForeground(Color.WHITE);
        javitas.setBorderPainted(false);
        javitas.setFocusPainted(false);
        enableButton(javitas);

        sikositas = new JButton("Síkosítás  ");
        sikositas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        sikositas.setForeground(Color.WHITE);
        sikositas.setBorderPainted(false);
        sikositas.setFocusPainted(false);
        disableButton(sikositas);
        
        ragasztas = new JButton("Ragasztás  ");
        ragasztas.setEnabled(false);
        ragasztas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        ragasztas.setForeground(Color.WHITE);
        ragasztas.setBackground(Color.BLACK);
        ragasztas.setBorderPainted(false);
        ragasztas.setFocusPainted(false);
        disableButton(ragasztas);

        felvetel = new JButton("Felvétel   ");
        felvetel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        felvetel.setForeground(Color.WHITE);
        felvetel.setBorderPainted(false);
        felvetel.setFocusPainted(false);
        disableButton(felvetel);

        lerakas = new JButton("Lerakás    ");
        lerakas.setEnabled(false);
        lerakas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        lerakas.setForeground(Color.WHITE);
        lerakas.setBorderPainted(false);
        lerakas.setFocusPainted(false);
        disableButton(lerakas);
        
        skipRound = new JButton("Kört_kihagy");
        skipRound.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        skipRound.setForeground(Color.WHITE);
        skipRound.setBackground(Color.getHSBColor(0.5f, 0.4f, 0.7f));
        skipRound.setBorderPainted(false);
        skipRound.setFocusPainted(false);

        JPanel actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(new BoxLayout(actionButtonsPanel, BoxLayout.Y_AXIS));
        actionButtonsPanel.setPreferredSize(new Dimension(290, 900));
        actionButtonsPanel.setBackground(Color.ORANGE);
        actionButtonsPanel.setBorder(null);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(engineerPointCount, Component.CENTER_ALIGNMENT);
        actionButtonsPanel.add(saboteurPointCount);
        actionButtonsPanel.add(remainingRounds);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionButtonsPanel.add(log);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionButtonsPanel.add(nextPlayer);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(atiranyitas);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(lyukasztas);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(javitas);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(sikositas);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(ragasztas);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(felvetel);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(lerakas);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        actionButtonsPanel.add(skipRound);

        JPanel mapPanel = new JPanel();
        mapPanel.setPreferredSize(new Dimension(1190, 900));
        mapPanel.setBackground(Color.ORANGE);

        for(IPipeSystem ips: Game.everything){
            mapPanel.add((JPanel)ips.getDrawer());
            ips.getDrawer().draw();
            ips.getDrawer().addMouseListener(fieldML);
        }

        mapPanel.setBorder(null);

        JPanel panel =new JPanel();
        panel.setLayout(new NoPaddingFlowLayout());
        panel.setBorder(null);
        panel.add(mapPanel);
        panel.add(actionButtonsPanel);
        this.getContentPane().add(panel);
        setVisible(true);
        
        atiranyitas.addActionListener(new ButtonActionListener());
        lyukasztas.addActionListener(new ButtonActionListener());
        javitas.addActionListener(new ButtonActionListener());
        sikositas.addActionListener(new ButtonActionListener());
        ragasztas.addActionListener(new ButtonActionListener());
        felvetel.addActionListener(new ButtonActionListener());
        lerakas.addActionListener(new ButtonActionListener());
        skipRound.addActionListener(new SkipActionListener());
    }
    
    private void enableButton(JButton button) {
        button.setEnabled(true);
        button.setBackground(Color.getHSBColor(0.5f, 0.4f, 0.7f));
    }
    private void disableButton(JButton button) {
        button.setEnabled(false);
        button.setBackground(Color.BLACK);
    }
    final class SkipActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            Player pNext = Game.getNextPlayer();
            MainFrame.nextPlayer.setText(pNext.getName() + " következik");
            Game.oneround--;
            if(Game.oneround == 0){
                Game.roundsCount--;
                Game.oneround = one;
            }

            //gombok ki-és bekapcsolása
            disableButton(atiranyitas);
            disableButton(lyukasztas);
            disableButton(javitas);
            disableButton(sikositas);
            disableButton(ragasztas);
            disableButton(felvetel);
            disableButton(lerakas);

            if (pNext.getStandingonpipe() != null) {
                enableButton(ragasztas);
                enableButton(lyukasztas);
                if (pNext.getClass().toString().contains("Saboteur")) {
                    enableButton(sikositas);
                } else {
                    enableButton(javitas);
                    enableButton(felvetel);
                    if (((Engineer)pNext).hasCarry() && ((Engineer)pNext).getCarry().getClass().toString().contains("Pump")) {enableButton(lerakas);}
                }
            } else if (pNext.getStandingon().getClass().toString().contains("Pump")) {
                enableButton(atiranyitas);
                if (pNext.getClass().toString().contains("Engineer")) {
                    enableButton(javitas);
                    if (((Engineer)pNext).hasCarry()&& ((Engineer)pNext).getCarry().getClass().toString().contains("Pipe")) {enableButton(lerakas);}
                }
            } else if (pNext.getStandingon().getClass().toString().contains("Cistern")) {
                if (pNext.getClass().toString().contains("Engineer") && ((Cistern)pNext.getStandingon()).getNewPumpsCount()>0) {enableButton(felvetel);}
            }

            remainingRounds.setText("Hátralévő körök: "+ Game.roundsCount);

            if(Game.roundsCount == 0){
                GameOver();
            }
            for(int i = 0; i < Game.everything.size(); i++){
                Game.everything.get(i).step();
                Game.everything.get(i).getDrawer().draw();
            }
            engineerPointCount.setText("Szerelők: " + Game.gainedWater);
            saboteurPointCount.setText("Szabotőrök: " + Game.lostWater);
            voltlepes = false;
            log.setText("log:");
        }
    }


    class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand().strip();
            boolean voltAction = true;
            switch (s) {
                //TODO redirectnek és carry-nek kell valahogyan paramétereket választani
                case "Átirányítás": {
                    boolean goodValues = false;
                    String input1 = JOptionPane.showInputDialog("Átirányítás", "Írd be a beérkező cső számát:");
                    String input2 = JOptionPane.showInputDialog("Átirányítás", "Írd be a kimenő cső számát:");
                    try{
                        int intin = Integer.parseInt(input1);
                        int intout = Integer.parseInt(input2);
                    }catch (Exception x){
                        log.setText("log: Helytelen in-, output!");
                        voltAction = false;
                        break;
                    }
                    for(Pipe p : Game.currPlayer.getStandingon().getPipes()){
                        if(p.getID() == Integer.parseInt(input1)){
                            for(Pipe p2 : Game.currPlayer.getStandingon().getPipes()){
                                if(p2.getID() == Integer.parseInt(input1)){
                                    goodValues = true;
                                }
                            }
                        }
                    }
                    if(!goodValues){
                        log.setText("log: Helytelen in-, output!");
                        voltAction = false;
                        break;
                    }
                    Pipe in = null;
                    Pipe out = null;
                    for(Pipe p : Game.currPlayer.getStandingon().getPipes()){
                        if(p.getID() == Integer.parseInt(input1)) in = p;
                        if(p.getID() == Integer.parseInt(input2)) out = p;
                    }
                    if(in != null && out != null)
                    Game.currPlayer.redirectPump(in, out);
                    break;}
                case "Lyukasztás": {Game.currPlayer.wreck(); break;}
                case "Javítás": {((Engineer)Game.currPlayer).repair(); break;}                           
                case "Síkosítás": {((Saboteur)Game.currPlayer).makeSlippery(); break; }
                case "Ragasztás": {Game.currPlayer.makeSticky(); break;}
                case "Felvétel": {


                    if(Game.currPlayer.getStandingon() != null && Game.currPlayer.getStandingon().getClass().toString().contains("Cistern")){
                        try{
                            ((Engineer)Game.currPlayer).carry(null);
                        }catch (RuntimeException r){
                            voltAction = false;
                            log.setText("Nem felvehető!");
                            break;
                        }

                    }

                    else{
                        String input = JOptionPane.showInputDialog("Lecsatolás", "Lecsatolandó oldal száma:");
                        NotPipe det = null;
                        try{
                            int proba = Integer.parseInt(input);
                        }catch (Exception x){
                            voltAction = false;
                            log.setText("log: Helytelen input!");
                            break;
                        }

                        for(NotPipe np : Game.currPlayer.getStandingonpipe().getPipeEnds()){
                            if(np.getID() == Integer.parseInt(input)) det = np;
                        }
                        if(det != null){
                            try{
                                ((Engineer)Game.currPlayer).carry(det);   //itt a pipedrawer beszarik a szomszédok változása miatt
                            }catch (RuntimeException r){
                                voltAction = false;
                                log.setText("Nem felvehető!");
                                break;
                            }
                        }
                        else{
                            log.setText("log: Helytelen input!");
                            voltAction = false;
                        }
                    }
                    break;}
                case "Lerakás": {
                    if(((Engineer)Game.currPlayer).getCarry().getClass().toString().contains("Pipe")){
                        if(((Engineer)Game.currPlayer).getStandingon() == ((Pipe)((Engineer)Game.currPlayer).getCarry()).getPipeEnds().get(0) ||  ((Engineer)Game.currPlayer).getStandingon() == ((Pipe)((Engineer)Game.currPlayer).getCarry()).getPipeEnds().get(1)){
                            voltAction = false;
                            log.setText("Nem lerakható!");
                            break;
                        }
                        if(!((Engineer)Game.currPlayer).connect(Game.currPlayer.getStandingon())){
                            voltAction = false;
                            log.setText("Nem lerakható!");
                            break;
                        }
                    }
                    else{
                        ((Engineer)Game.currPlayer).insertPump();
                    }
                    break;
                   
                }

                default: break;
            }
            if(voltAction){
                disableButton(atiranyitas);
                disableButton(lyukasztas);
                disableButton(javitas);
                disableButton(sikositas);
                disableButton(ragasztas);
                disableButton(felvetel);
                disableButton(lerakas);
            }

        }
    }

    public void GameOver(){
        Game.gameOver();
        dispose();
    }
    
    private MouseListener fieldML = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Drawable d = (Drawable)e.getSource();

            Game.currPlayer.moveTo(d.getObject());

            if(voltlepes){
                Player pNext = Game.getNextPlayer();
                if (pNext.getStuck() > 0) MainFrame.nextPlayer.setText(pNext.getName() + " csőhöz ragadt");
                else MainFrame.nextPlayer.setText(pNext.getName() + " következik");
                Game.oneround--;
                if(Game.oneround == 0){
                    Game.roundsCount--;
                    Game.oneround = one;
                }
                
                //gombok ki-és bekapcsolása
                disableButton(atiranyitas);
                disableButton(lyukasztas);
                disableButton(javitas);
                disableButton(sikositas);
                disableButton(ragasztas);
                disableButton(felvetel);
                disableButton(lerakas);

                if (pNext.getStandingonpipe() != null) {
                    enableButton(ragasztas);
                    if(!pNext.getStandingonpipe().isBroken()){
                        enableButton(lyukasztas);
                    }
                    if (pNext.getClass().toString().contains("Saboteur")) {
                        enableButton(sikositas);
                    } else {
                        if(pNext.getStandingonpipe().isBroken()){
                            enableButton(javitas);
                        }
                        if(!((Engineer) pNext).hasCarry()){
                            enableButton(felvetel);
                        }
                        if (((Engineer)pNext).hasCarry()&& ((Engineer)pNext).getCarry().getClass().toString().contains("Pump")) {enableButton(lerakas);}
                    }
                } else if (pNext.getStandingon().getClass().toString().contains("Pump")) {
                    enableButton(atiranyitas);
                    if (pNext.getClass().toString().contains("Engineer") && ((Pump) pNext.getStandingon()).getBroken()) {
                        enableButton(javitas);
                        if (((Engineer)pNext).hasCarry() && ((Engineer)pNext).getCarry().getClass().toString().contains("Pipe")) {enableButton(lerakas);}
                    }
                } else if (pNext.getStandingon().getClass().toString().contains("Cistern") && ((Cistern)pNext.getStandingon()).getNewPumpsCount()>0) {
                    if (pNext.getClass().toString().contains("Engineer") && !((Engineer) pNext).hasCarry()) {enableButton(felvetel);}
                }

                enableButton(skipRound);
                
                remainingRounds.setText("Hátralévő körök: "+ Game.roundsCount);

                if(Game.roundsCount == 0){
                    GameOver();
                }
                for(int i = 0; i < Game.everything.size(); i++){
                    Game.everything.get(i).step();
                    Game.everything.get(i).getDrawer().draw();
                }
                engineerPointCount.setText("Szerelők: " + Game.gainedWater);
                saboteurPointCount.setText("Szabotőrök: " + Game.lostWater);
                voltlepes = false;
                log.setText("log:");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    };
}
