/**
 * Egy játékos ősosztály példány
 */
abstract class Player {
    protected Pipe standingonpipe; //Amin áll a játékos, ha cső
    protected NotPipe standingon; //Amin áll a játékos, ha nem cső

    protected String name; //Játékos név
    protected int id; //Játékos azonosító
    protected int stuck = 0; //Be van a ragadva

    /**
     * getter, hogy min áll a player
     * @return az objektum amelyen a Player áll
     */

    IPipeSystem position() {
        return standingon == null ? standingonpipe : standingon;    
    }

    /**
     * Egy játékos átlép egy másik IPS elemre
     * @param to Amire rá fog lépni
     */
    public void moveTo(IPipeSystem to) {

        if (stuck == 0 && to.movedTo(this)) { //Ha nincs beragadva
            //moved from NotPipe to Pipe
            if (standingon != null) {
                standingon.remove(this);
                standingonpipe = (Pipe) to;
                standingon = null;
                standingonpipe.pipeCheck();
            }
            //moved from Pipe to NotPipe
            else {
                standingonpipe.remove();
                standingonpipe = null;
                standingon = (NotPipe) to;
            }
            MainFrame.voltlepes = true;
        }
    }

    /**
     * Pumpa kimenet/bemenet átállítás
     * @param to ide megy a víz
     * @param from innen jön a víz
     */

    public void redirectPump(Pipe to, Pipe from) {

        if (standingon != null) {
            try {

                Pump temp = (Pump)standingon;
                temp.redirectPump(to, from);
                standingon = temp;

                System.out.println("from: Pipe" +to.getID()+" - to: Pipe"+from.getID());
            }
            catch (Exception e) {}
        }
    }

    /**
     * @param p Cső, amin állni fog
     */
    //  Setters/Getters
    public void setStandingonpipe(Pipe p){ standingonpipe = p; p.setPlayer(this);}

    /**
     * Áttálítja, hogy min áll a játékos
     * @param np Nem ccső, amin állni fog
     */
    public void setStandingon(NotPipe np){ standingon = np; np.setPlayers(this);}

    /**
     * getter, hogy a játékos min áll
     * @return Min áll (Ha cső)
     */
    public Pipe getStandingonpipe() { return standingonpipe; }

    /**
     * getter, hogy a játékos min áll
     * @return Min áll a játékos (ha Nem cső)
     */
    public NotPipe getStandingon() { return standingon; }

    /**
     * Ha áll valamin, akkor azt elrontja
     */
    public void wreck() {
        if (standingonpipe != null) standingonpipe.broke();
    }

    /**
     * Ha csövön áll, akkor ragadóssá teszi azt
     */
    public void makeSticky(){
        if(standingonpipe != null){
            standingonpipe.setSticky();
        }
    }

    /**
     * Ha be van ragadva, akkor körönként csökken a beragadásból hátralevő idő
     */
    public void decreaseStuck(){
       if(stuck > 0)
    	   stuck--;
    }

    /**
     * Ha ragacsos csöre állt, akkor beállítja a beragadást
     */
    public void setStuck(){
        stuck = 3;
    }
    public int getStuck(){
        return stuck;
    }

    /**
     * Törli, ha csövön állt
     */
    public void nullPipe(){
        if(standingonpipe != null){
            standingonpipe.remove();
            standingonpipe = null;
        }
    }

    /**
     * getter a player ID-ra
     * @return player ID
     */
    public int getID(){
        return id;
    }

    public abstract void details();

    /**
     * getter a játékosnévre
     * @return játékosnév
     */
    public String getName(){
        return name;
    }
}
