import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A játék kezeléséért felelős osztály
 */
public class Game {
    static double gainedWater = 0;    // Engineers points
    static double lostWater = 0;      // Saboteurs points

    public static int nextPlayerIdx = 0; //A következő id

    public static int nextStructureID = 1;
    public static int nextPlayerID = 1;

    public static Player currPlayer;

    //Minden amiből a pálya áll
     static ArrayList<IPipeSystem> everything = new ArrayList<>();
    //Minden player
    private static ArrayList<Player> everyone = new ArrayList<>();

    public static int roundsCount = 12;
    public static int oneround;
    public static boolean random = true;

    /**
     * Reseteli a játékállást.
     */
    private static void reset() throws IOException {
        everything = new ArrayList<>();
        everyone = new ArrayList<>();
        nextStructureID = 1;
        nextPlayerID = 1;
    }

    /**
     * Eltárolja a megadott ips objektumot a listájában.
     * @param ips objektum amelyet a játékhoz szeretnénk adni
     */
    public static void addIPS(IPipeSystem ips){
        everything.add(ips);
    }

    /**
     * Eltárolja a megadott p objektumot a listájában.
     * @param p játékos amelyet a játékhoz szeretnénk adni
     */
    public static void addPlayer(Player p){
        everyone.add(p);
    }
    public static boolean getRandom(){
        return random;
    }
    public void setRandom(boolean r){
        random = r;
    }

    /**
     * ID alapján visszaadja a Player objektumot
     * @param id amelyből szeretnénk kinyerni a Player objektumot
     */
    private static Player getPlayerByID(int id){
        for(Player p: everyone){
            if(p.getID() == id){
                return p;
            }
        }
        return null;
    }

    /**
     * ID alapján visszaadja az IPS objektumot
     * @param id amelyből szeretnénk kinyerni az IPS objektumot
     */
    private static IPipeSystem getStructureByID(int id) {
        for(IPipeSystem ps : everything) {
            if(ps.getID() == id) {
                return ps;
            }
        }
        return null;
    }
    //Kiszedi a stringből a számokat
    private static int getIDFromString(String from){
        String digitsOnly = from.replaceAll("[^0-9]", "");
        return Integer.parseInt(digitsOnly);
    }

    /**
     * Engineer csapat pontjának növelése
     */
    static void gainWater(){
        gainedWater++;
    }

    /**
     * Szabotőr csapat pontjának növelése
     */
    static void loseWater(){
        lostWater++;
    }

    public static void details(){
        System.out.println("Game.lostWater: "+ lostWater + ", Game.gainedWater: "+gainedWater);
    }

    public boolean isGameOver(){
        return roundsCount>0?true:false;
    }

    /**
     * Megjeleníti a GameOverFrame-et
     */
    public static void gameOver(){
        new GameOverFrame();

    }

    /**
     * Visszaadja melyik játékos következik
     * @return Player soron következő játékos
     */
    public static Player getNextPlayer(){
        Player ret = everyone.get(nextPlayerIdx);
        if(nextPlayerIdx == everyone.size()-1) nextPlayerIdx = 0;
        else nextPlayerIdx++;
        currPlayer = ret;
        return ret;
    }

    /**
     * Pálya inicializálását végzi.
     */
    public static void init(int e, int s){


        Cistern c1 = new Cistern(1100, 100);
        Pipe pi1 = new Pipe();
        Pipe pi2 = new Pipe();
        Pipe pi3 = new Pipe();
        Pipe pi5 = new Pipe();
        Pipe pi6 = new Pipe();
        Pipe pi7 = new Pipe();
        Pipe pi8 = new Pipe();
        Pump p2 = new Pump(800, 100);
        Pump p1 = new Pump(500, 100);
        Pump p3 = new Pump(500, 300);
        Pump p4 = new Pump(800, 300);
        Pump p5 = new Pump(100, 300);
        Source s1 = new Source(100, 100);

        pi7.connect(s1);
        pi7.connect(p5);
        pi8.connect(p5);
        pi8.connect(p3);
        pi1.connect(p1);
        pi1.connect(s1);
        pi2.connect(p1);
        pi2.connect(p2);
        pi3.connect(p2);
        pi3.connect(c1);

        pi5.connect(p3);
        pi5.connect(p4);
        pi6.connect(p4);
        pi6.connect(p2);

        for(int i = 0; i < e; i++){
            Engineer en = new Engineer(null);
            en.setStandingon(p1);
        }
        for(int i = 0; i < s; i++){
           Saboteur sa = new Saboteur(null);
           sa.setStandingon(p1);
        }

    }

    /**
     * Visszaadja amelyik balrább
     * @param np1 összehasonlitandó objektum
     * @param np2 összehasonlitandó objektum
     * @return NotPipe balrább
     */
    public static NotPipe getLeft(NotPipe np1, NotPipe np2){
        if(np1.getY() == np2.getY()){
            if(np1.getX() < np2.getX()){
                return np1;
            }
            else {
                return np2;
            }
        }
        return null;
    }
    /**
     * Visszaadja amelyik jobbrább van
     * @param np1 összehasonlitandó objektum
     * @param np2 összehasonlitandó objektum
     * @return NotPipe jobb szomszéd
     */
    public static NotPipe getRight(NotPipe np1, NotPipe np2){
        if(np1.getY() == np2.getY()){
            if(np1.getX() > np2.getX()){
                return np1;
            }
            else {
                return np2;
            }
        }
        return null;
    }
    /**
     * Visszaadja azt amelyik magasabban van
     * @param np1 összehasonlitandó objektum
     * @param np2 összehasonlitandó objektum
     * @return NotPipe magasabb
     */
    public static NotPipe getUpper(NotPipe np1, NotPipe np2){
        if(np1.getX() == np2.getX()){
            if(np1.getY() < np2.getY()){
                return np1;
            }
            else {
                return np2;
            }
        }
        return null;
    }
    /**
     * Visszaadja amelyik lentebb van
     * @param np1 összehasonlitandó objektum
     * @param np2 összehasonlitandó objektum
     * @return NotPipe lentebb
     */
    public static NotPipe getLower(NotPipe np1, NotPipe np2){
        if(np1.getX() == np2.getX()){
            if(np1.getY() > np2.getY()){
                return np1;
            }
            else {
                return np2;
            }
        }
        return null;
    }

}
