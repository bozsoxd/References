import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * A csövek logikáját valósítja meg
 */
public class Pipe implements IPipeSystem{
    private Point2D center;
    public String irany;
    private boolean water;
    private boolean broken;
    private List<NotPipe> pipeends;
    private Player player;
    protected int id;
    private int sticky = 0;
    private int length;

    private PipeDrawer drawer;

    private int timeUntilWreckable = 0;
    private int slippery = 0;
    public Pipe(){
        pipeends = new ArrayList<NotPipe>();
        pipeends.add(null);
        pipeends.add(null);
        water = false;
        broken = false;
        player = null;
        id = Game.nextStructureID;
        Game.nextStructureID++;
        Game.addIPS(this);
        //System.out.println("Pipe"+id+" created");
        length = 150;

        drawer = new PipeDrawer(this);
    }
    public Point2D getCenter(){
        return center;
    }

    public List<NotPipe> getPipeEnds(){
        return pipeends;
    }

    /**
     * Víz lépésének a logikája
     */
    @Override
    public void step() {
        if(water) {
            if (!broken && pipeends.get(1) != null) {
                pipeends.get(1).addWater(this);
            }else if (broken || pipeends.contains(null)) {
                Game.loseWater();
            }
        }
        clearPipe();

        if(timeUntilWreckable != 0) timeUntilWreckable--;
        if(player != null) player.decreaseStuck();

        decreaseSlippery();
        decreaseSticky();
    }

    /**
     * movedTo method megvalósítása
     * @param p Player amelyik rálépett
     */
    @Override
    public boolean movedTo(Player p) {
        if (player == null && pipeends.contains(p.getStandingon())) {
            player = p;
            System.out.println("Player"+p.getID()+" moved to " +"Pipe"+ this.getID());
            return true;
        }
        return false;
    }

    /**
     * Csőröl lecsatol NotPipe objektumot.
     * @param from lecsatolni kívánt NP
     */
    public void detach(NotPipe from){
        if(from != null) {
            try{
                from.detach(this);
                if(pipeends.get(0) == from){
                    pipeends.set(0, null);
                }
                else {
                    pipeends.set(1, null);
                }
            }catch (RuntimeException r){
                throw new RuntimeException();
            }

        }
    }
    /**
     * Connect method, csőhöz ad hozzá NotPipe objektumot
     * @param notPipe hozzáadni kívánt NP
     */
    @Override
    public boolean add(IPipeSystem notPipe){
        if(((NotPipe) notPipe).getPipes().size() < 4){
            //System.out.println("Pipe"+this.getID()+" connected to "+ notPipe.getClass().getName() + notPipe.getID());
            //System.out.println("Pipe"+this.getID()+" connected to "+ notPipe.getClass().getName() + notPipe.getID());
            if(pipeends.get(0) == null){
                pipeends.set(0, (NotPipe) notPipe);
                return true;
            }
            else if(pipeends.get(1) == null){
                pipeends.set(1, (NotPipe) notPipe);
                return true;
            }

        }
        return false;
    }
    /**
     * Játékos csövet csatlkazotat NP objektumhoz
     * @param notPipe csövet erre csatlakoztatjuk
     */
    public boolean connect(NotPipe notPipe){
            if(notPipe.vanSzabadIrany()){
                if(pipeends.get(0) != null && pipeends.get(1) != null){
                    return false;
                }
                else if(pipeends.get(0) == null && pipeends.get(1) == null){
                    this.irany = notPipe.getFirstSzabadIrany();
                    notPipe.removeFirstSzabadIrany();
                    add(notPipe);
                    notPipe.add(this);
                    return true;
                }
                else{
                    NotPipe fix;
                    if(pipeends.get(0) != null){
                        fix = pipeends.get(0);
                    }
                    else {
                        fix = pipeends.get(1);
                    }


                    if(fix.getY() == notPipe.getY()){
                        for(IPipeSystem ips: Game.everything){
                            if(ips.getClass().toString().contains("Pump") ||ips.getClass().toString().contains("Cistern") || ips.getClass().toString().contains("Source")){
                                if(ips.getX() > Game.getLeft(fix, notPipe).getX() && ips.getX() < Game.getRight(fix, notPipe).getX()){
                                    return false;
                                }
                            }
                        }
                        if(Game.getLeft(fix, notPipe) == notPipe && notPipe.checkFreeIrany("jobb")){
                            if(irany.equals("bal")){
                                this.irany = "hor";
                                notPipe.removeIrany("jobb");
                                add(notPipe);
                                notPipe.add(this);
                                return true;
                            }
                            else if(fix.checkFreeIrany("bal")){
                                fix.addSzabadIrany(this.irany);
                                fix.removeIrany("bal");
                                this.irany = "hor";
                                notPipe.removeIrany("jobb");
                                add(notPipe);
                                notPipe.add(this);
                                return true;
                            }
                        }
                        else if(Game.getRight(fix, notPipe) == notPipe && notPipe.checkFreeIrany("bal")){
                            if(irany.equals("jobb")){
                                this.irany = "hor";
                                notPipe.removeIrany("bal");
                                add(notPipe);
                                notPipe.add(this);
                                return true;
                            }
                            else if(fix.checkFreeIrany("jobb")){
                                fix.addSzabadIrany(this.irany);
                                fix.removeIrany("jobb");
                                this.irany = "hor";
                                notPipe.removeIrany("bal");
                                add(notPipe);
                                notPipe.add(this);
                                return true;
                            }
                        }
                    }
                    else if(fix.getX() == notPipe.getX()){
                        for(IPipeSystem ips: Game.everything){
                            if(ips.getClass().toString().contains("Pump") ||ips.getClass().toString().contains("Cistern") || ips.getClass().toString().contains("Source")){
                                if(ips.getY() > Game.getLower(fix, notPipe).getY() && ips.getY() < Game.getUpper(fix, notPipe).getY()){
                                    return false;
                                }
                            }
                        }
                        if(Game.getUpper(fix, notPipe) == notPipe && notPipe.checkFreeIrany("le")){
                            if(irany.equals("fel")){
                                this.irany = "ver";
                                notPipe.removeIrany("le");
                                add(notPipe);
                                notPipe.add(this);
                                return true;
                            }
                            else if(fix.checkFreeIrany("fel")){
                                fix.addSzabadIrany(this.irany);
                                fix.removeIrany("fel");
                                this.irany = "ver";
                                notPipe.removeIrany("le");
                                add(notPipe);
                                notPipe.add(this);
                                return true;
                            }
                        }
                        else if(Game.getLower(fix, notPipe) == notPipe && notPipe.checkFreeIrany("fel")){
                            if(irany.equals("le")){
                                this.irany = "ver";
                                notPipe.removeIrany("fel");
                                add(notPipe);
                                notPipe.add(this);
                                return true;
                            }
                            else if(fix.checkFreeIrany("le")){
                                fix.addSzabadIrany(this.irany);
                                fix.removeIrany("le");
                                this.irany = "ver";
                                notPipe.removeIrany("fel");
                                add(notPipe);
                                notPipe.add(this);
                                return true;
                            }
                        }
                    }


                }

            }

        return false;
    }

    public void connectIranybol(String irany, NotPipe notPipe){
        this.irany = irany;
        notPipe.removeIrany(irany);
        add(notPipe);
        notPipe.add(this);
    }

    /**
     * Ketté vágja a csövet, visszaadja a cső másik (új) felét, amelyet csatlakoztatott is a régi cső egyik NotPipe objektumára
     * @return Pipe új cső
     */
    public Pipe cut(){
        Pipe newPipe = new Pipe();
        NotPipe temp = pipeends.get(1);
        detach(pipeends.get(1));
        if(irany.equals("le")){
            newPipe.connectIranybol("fel", temp);
        }
        else if(irany.equals("fel")){
            newPipe.connectIranybol("le", temp);
        }
        else if(irany.equals("jobb")){
            newPipe.connectIranybol("bal", temp);
        }
        else {
            newPipe.connectIranybol("jobb", temp);
        }
        length = length -26;
        newPipe.setLength(newPipe.getLength()-26);
        return newPipe;
    }

    /**
     * Target method, vizet tesz a csőbe és np-t állítja be folyási iránynak.
     */
    public void target(NotPipe np){
        water = true;
        Collections.swap(pipeends, pipeends.indexOf(np), 0);
    }

    /**
     * Cső elrontása
     */
    public void broke(){
        if(timeUntilWreckable == 0) {
            System.out.println("Player" + this.player.getID() + " broke Pipe" + this.getID());
            broken = true;
            timeUntilWreckable = 6;
        }
        else {
            System.out.println("Pipe can't be broken");
        }

    }

    /**
     * Cső javítása
     */
    public void repair(){
        broken = false;
    }

    /**
     * Player levétele csőröl
     */
    public void remove() {
        player = null;
    }

    public void clearPipe(){
        water = false;
    }
    public List<NotPipe> getPipeends(){
        return pipeends;
    }
    public boolean isBroken() {
        return broken;
    }
    // Setters/Getters
    public boolean getWater() {
        return water;
    }
    public void setPipeends(List<NotPipe> pipeends) {
        this.pipeends = pipeends;
    }
    public void setWater(boolean b){
        water = b;
    }
    /**
     * Különböző játékbeli feltételek ellenőrzése, lépésnél használjuk
     */
    void pipeCheck(){
        if(slippery != 0){
           if(pipeends.size() == 1) {
        	   player.setStandingon(pipeends.get(0));
               return;
           }
           if(Game.random) {
               Random r = new Random();
               if (r.nextBoolean())
                   player.setStandingon(pipeends.get(0));
               else player.setStandingon(pipeends.get(1));
               player.nullPipe();
           }
           else {
               player.setStandingon(pipeends.get(1));
               player.nullPipe();
           }
           slippery = 0;
        }
        else if(sticky != 0){
            player.setStuck();
            sticky = 0;
        }
    }

    void decreaseSlippery(){
        if(slippery > 0)
            slippery--;
    }

    void decreaseSticky(){
        if(sticky > 0)
            sticky--;
    }

    void setSticky(){
        sticky = 6;
        System.out.println("Player" + this.player.getID() + " made Pipe" + this.getID() + " sticky.");
    }

    void setSlippery(){
        slippery = 6;
        System.out.println("Player" + this.player.getID() + " made Pipe" + this.getID() + " slippery.");
    }

    @Override
    public int getID(){
        return id;
    }

    public void details(){

        if(pipeends.size() == 2) {
            System.out.println("Pipe" + this.getID() + ".water: " + this.water + ", Pipe" + this.getID() + ".broken: " + this.broken + ", Pipe" + this.getID() + ".timeUntilWreckable: " + this.timeUntilWreckable + ", Pipe"
                    + this.getID() + ".slipperyUntil: " + this.slippery + ", Pipe" + this.getID() + ".sticky: " + this.sticky + ", Pipe" + this.getID() + ".pipeends: "
                    + this.pipeends.get(0).getClass().getName() + this.pipeends.get(0).getID() + "-" + this.pipeends.get(1).getClass().getName() + this.pipeends.get(1).getID());

        }else if(pipeends.size() == 1){
            System.out.println("Pipe" + this.getID() + ".water: " + this.water + ", Pipe" + this.getID() + ".broken: " + this.broken + ", Pipe" + this.getID() + ".timeUntilWreckable: " + this.timeUntilWreckable + ", Pipe"
                    + this.getID() + ".slipperyUntil: " + this.slippery + ", Pipe" + this.getID() + ".sticky: " + this.sticky + ", Pipe" + this.getID() + ".pipeends: "
                    + this.pipeends.get(0).getClass().getName() + this.pipeends.get(0).getID() + "-null");

        }else if(pipeends.size() == 0){
            System.out.println("Pipe" + this.getID() + ".water: " + this.water + ", Pipe" + this.getID() + ".broken: " + this.broken + ", Pipe" + this.getID() + ".timeUntilWreckable: " + this.timeUntilWreckable + ", Pipe"
                    + this.getID() + ".slipperyUntil: " + this.slippery + ", Pipe" + this.getID() + ".sticky: " + this.sticky + ", Pipe" + this.getID() + ".pipeends: "
                    + "null-null");
        }
    }
    @Override
    public String toString(){
        return "Pipe"+id;
    }

    public int getLength(){
        return length;
    }
    public void setLength(int d){
        length = d;
    }



    public boolean hasPlayer(){
        if(player != null) return true;
        return false;
    }

    public Player getPlayer(){
        return player;
    }

    public Drawable getDrawer(){
        return drawer;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    public void setPlayer(Player p){
        player = p;
    }
    
    public int getSlippery() {return slippery;}
    public int getSticky() {return sticky;}


}
