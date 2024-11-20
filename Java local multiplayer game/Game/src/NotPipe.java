import java.awt.geom.Point2D;
import java.util.*;
/**
 * A nem cső típusú elemek logikáját valósítja meg
 */
public abstract class NotPipe implements IPipeSystem {
    //Tárolja a rajta álló Playereket
    protected int id;
    protected int x = 0;
    protected int y = 0;
    protected List<String> szabadIranyok;
    protected ArrayList<Player> players = new ArrayList<>();
    //Tárolja, a hozzá kötött csöveket
    protected ArrayList<Pipe> pipes = new ArrayList<>();

    /**
     * Lecsatlakoztatja magát a csőről.
     * @param from Cső amelyről lecsatlakozik
     */
    public void detach(Pipe from) {
        if(from.getPipeEnds().get(0) == null || from.getPipeEnds().get(1) == null){
            throw new RuntimeException();
        }
        NotPipe marad;
        if(from.getPipeEnds().get(0) == this){
            marad = from.getPipeEnds().get(1);
        }
        else{
            marad = from.getPipeEnds().get(0);
        }
        if(from.irany.equals("hor")){
            if(Game.getLeft(marad, this) == marad){
                from.irany = "jobb";
                szabadIranyok.add("bal");
                from.setLength(this.getX()- marad.getX()-51);
            }
            else {
                from.irany = "bal";
                szabadIranyok.add("jobb");
                from.setLength(marad.getX()-this.getX()-51);
            }
        }
        else {
            if(Game.getUpper(marad, this) == marad){
                from.irany = "le";
                szabadIranyok.add("fel");
                from.setLength(this.getY()- marad.getY()-51);
            }
            else {
                from.irany = "fel";
                szabadIranyok.add("le");
                from.setLength(marad.getY()-this.getY()-51);
            }
        }
        pipes.remove(from);

    }

    /**
     * Hozzá ad egy új csövet
     * @param p cső amelyet csatlakoztatunk
     */
    @Override
    public boolean add(IPipeSystem p) {
        pipes.add((Pipe)p); return true;
    }

    /**
     * Megtelik vízzel a NotPipe objektum
     * @param pipe cső amelyiknek adja
     * @return boolean
     */
    public boolean addWater(Pipe pipe) {
        return true;
    }

    /**
     * movedTo felüldefiniálása, ha rálép egy Player ez fut le
     * @param p Player amelyik rálépett
     * @return boolean
     */
    @Override
    public boolean movedTo(Player p) {
        if(p.getStandingonpipe()!= null && pipes.contains(p.getStandingonpipe())) {
            players.add(p);
            System.out.println("Player" + p.getID() + " moved to " + this.getClass().getName() + this.getID());
            return true;
        }
        return false;
    }

    /**
     * Vissza adja a csatlakoztatott csöveket
     * @return ArrayList<Pipe></Pipe> csövek listája
     */
    public ArrayList<Pipe> getPipes() {
        return pipes;
    }
    @Override
    public void broke() {}
    @Override
    public void repair() {}

    /**
     * Eltávolít egy Player-t ha az lelép
     * @param player eltávolítani kívánt player
     */
    public void remove(Player player) {
        players.remove(player);
    }

    /**
     * Hozzáad egy Player-t ha az rálép
     * @param p hozzáadni kívánt player
     */
    public void setPlayers(Player p) {players.add(p);}

    /**
     * Visszaadja áll-e rajta Player
     */
    public boolean hasPlayers() {
        if(players.isEmpty()) return false;
        return true;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getFirstSzabadIrany(){
        if(szabadIranyok.size()>0){
            return szabadIranyok.get(0);
        }
        return null;
    }

    public void removeFirstSzabadIrany(){
        if(szabadIranyok.size()>0){
            szabadIranyok.remove(0);
        }
    }

    public boolean vanSzabadIrany(){
        return szabadIranyok.size()>0;
    }


    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public boolean checkFreeIrany(String irany){
        for(String s: szabadIranyok){
            if(s.equals(irany)){
                return true;
            }
        }
        return false;
    }

    public void removeIrany(String irany){
        for(int i = 0; i < szabadIranyok.size(); i++){
            if(szabadIranyok.get(i).equals(irany)){
                szabadIranyok.remove(i);
                return;
            }
        }
    }
    public void addSzabadIrany(String irany){
        szabadIranyok.add(irany);
    }

}
