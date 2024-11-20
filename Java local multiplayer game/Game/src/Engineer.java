/**
 * Egy szerelő játékos
 */
public class Engineer extends Player {
    /**
     * Amit cipel
     */
    private IPipeSystem carry;

    /**
     * Példányosít egy szerelő típusú játékost
     * @param _name Játékosnév (Generált azonosító)
     */
    public Engineer(String _name){

        id = Game.nextPlayerID+100;
        if(_name == null){

            name = "Engineer" + id;
        }
        else{
            name = _name;
        }
        Game.addPlayer(this); //Hozzáadja a game játékosaihoz
        System.out.println("Player"+id+"(Engineer) created.");
        Game.nextPlayerID++;
    }

    /**
     * Szerelő megjavítja a csövet, amin áll
     */
    public void repair() {
        System.out.println("Player" + id + " repaired " + position().getClass().getName() + position().getID());
        position().repair();
    }

    /**
     * Szerelő felvesz egy NotPipe típusú objektumot (Cistern, Source, Pump)
     * @param np Amit visz
     */
    public void carry(NotPipe np) {
        //Engineer wants to pick up Pipe
        if (standingonpipe != null) {
            try{
                standingonpipe.detach(np); //Lecsatlakoztatja
                carry = standingonpipe; //felveszi
                System.out.println("Player"+id+"is carrying Pipe" + carry.getID() );
            }catch (RuntimeException r){
                throw new RuntimeException();
            }

        }
        //Engineer wants to pick up Pump, has to be standing on Cistern
        else {try{carry = ((Cistern)standingon).getNewPump();
            System.out.println("Player"+id+"is carrying Pump" + carry.getID() );
        } catch(Exception e){ System.out.println("Player"+id+"can't carry"); }}

    }

    /**
     * Csatlakoztat egy objektumot egy másikhoz
     * @param where Ahova csatlakoztatni szeretne
     */

    public boolean connect(IPipeSystem where) {
        return ((Pipe) carry).connect((NotPipe) where);
    }

    /**
     * egy pumpát beteszük egy csőbe
     */

    public void insertPump() {
        if (standingonpipe != null && carry != null) {
            Pipe newPipe = standingonpipe.cut(); //elvágjuk a csövet, amivel új cső jön létre
            carry.add(standingonpipe);
            standingonpipe.add(carry);
            carry.add(newPipe);
            newPipe.add(carry); //Csatlakotatunk mindent mindennel
            System.out.println("Player"+id+" placed Pump"+carry.getID());
            carry = null;

        }
        else {
            System.out.println("Player"+id+" can't place Pump");
        }
    }

    /**
     * Beállítja, hogy mit vigyen
     * @param c Ezt vigye
     */
    public void setCarry(IPipeSystem c){carry = c;}

    /**
     * @return Visszaadja, hogy van-e valami az Engineer kezében
     */

    public boolean hasCarry(){
        return carry != null;
    }

    /**
     * @return Visszaadja, hogy mi van az Engineer kezében
     */
    public IPipeSystem getCarry(){
        return carry;
    }

    /**
     * Kiírja konzolos kimenetre az Engineer objektum tulajdonságait.
     */
    public void details(){
        if(standingonpipe != null && carry != null)
        System.out.println("Player" + getID() +".stuck: " + stuck + ", Player" + getID() + ".standingonpipe: Pipe" + standingonpipe.getID() + ", Player" + getID() + ".standingon: null, Player" + getID() +".Carry: "+carry.getClass().getName() + carry.getID());
        else if (standingon != null && carry != null)
            System.out.println("Player" + getID() +".stuck: " + stuck + ", Player" + getID() + ".standingonpipe: null, Player" + getID() + ".standingon: " + standingon.getClass().getName() + standingon.getID() +  ", Player" + getID() +".Carry: "+carry.getClass().getName() + carry.getID());
        else if(standingonpipe != null && carry == null)
            System.out.println("Player" + getID() +".stuck: " + stuck + ", Player" + getID() + ".standingonpipe: Pipe" + standingonpipe.getID() + ", Player" + getID() + ".standingon: null, Player" + getID() +".Carry: null");
        else if(standingon != null && carry == null)
            System.out.println("Player" + getID() +".stuck: " + stuck + ", Player" + getID() + ".standingonpipe: null, Player" + getID() + ".standingon: " + standingon.getClass().getName() + standingon.getID() +  ", Player" + getID() +".Carry: null");
        else System.out.println("Player" + getID() +".stuck: " + stuck + ", Player" + getID() + ".standingonpipe: null, Player" + getID() + ".standingon: null, Player" + getID() +".Carry: null");

    }
}
