import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


/**
 * Egy ciszterna
 */
public class Cistern extends NotPipe {

    /**
     * Egy ciszterna kirajzolásáért felelős
     */
    private CisternDrawer drawer;


    /**
     * @param xx X koordináta
     * @param yy Y koordináta
     */
    public Cistern(int xx,int yy){
        id = Game.nextStructureID; //Generál egy ID-t az újonnan létrejövő ciszternának
        System.out.println("Cistern"+id+" created");
        Game.nextStructureID++;
        Game.addIPS(this); //Hozzáadja az újjonan létrjöt ciszternát a játékhoz
        x = xx;
        y = yy;
        szabadIranyok = new ArrayList<>();

        szabadIranyok.add("fel");
        szabadIranyok.add("jobb");
        szabadIranyok.add("le");
        szabadIranyok.add("bal");
        drawer = new CisternDrawer(this); //Példányosít egy rajzolót hozzá
    }

    /**
     * A ciszterna ebben tárolja a létrehozott pumpákat
     * LinkedListben tároljuk a ciszterna által újjonan létrehozott pumpákat, mert ezt a lista típust a legegyszerűbb FIFO-ként használni
     */
    private static LinkedList<Pump> newPumps = new LinkedList<>();

    /**
     * A Cistern lépése: létrehoz új pumpát és csövet is, a tesztelhetőség miatt egyelőre nem random, hanem mindig
     */
    @Override
    public void step() {

        if(Game.getRandom()){
            Random r = new Random();
            if(r.nextInt(5) == 2){
                newPumps.addFirst(new Pump());
            }
            else if(r.nextInt(5) == 3){
                Pipe np = new Pipe();
                np.connect(this);
            }

        }



    }

    public void details(){
        String res = "";
        res = res.concat("Cistern"+ id+".newPumps: ");
        for(Pump p: newPumps){
            res = res.concat(p.toString() + "-");
        }
        res = res.concat(", Cistern"+ id+".pipes: ");
        for(Pipe p : pipes){
            res = res.concat(p.toString() + "-");
        }
        System.out.println(res);
    }


    /**
     * ha nincs még 4 szomszédja, hozzáadja a paraméterként kapott csövet a szomszédjaihoz
     * @param pipe A csatlakozó cső
     * @return true
     */

    @Override
    public boolean add(IPipeSystem pipe) {
        if (getPipes().size() <= 4){
            pipes.add((Pipe)pipe);
            return true;}
        return false;
    }


    /**
     * visszaadja FIFO-ként a legkorábban létrehozott új pumpát
     * @return Az utolsóként létrejött új pumpa
     */
    public Pump getNewPump(){
        return newPumps.removeLast();
    }

    /**
     * A ciszterna általi pontozás megvalósítása
     * @param pipe Ebbe tobábbítja a vízet
     * @return true
     */
    @Override
    public boolean addWater(Pipe pipe){
        Game.gainWater();
        return true;
    }

    /**
     * Új pumpák száma getter
     * @return Újonan létrehozott pumpák száma a ciszternában
     */

    public int getNewPumpsCount(){
        return newPumps.size();
    }


    /**
     * ID getter
     * @return cisztrerna ID
     */

    @Override
    public int getID(){
        return id;
    }

    /**
     * Rajzoló getter
     * @return ciszterna rajzolója
     */
    public Drawable getDrawer(){
        return drawer;
    }
  
}
