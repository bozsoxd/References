/**
 * Szabotőr játékost megvalósító osztály
 */
public class Saboteur extends Player {
    /**
     * Konstruktor egy szabotőrhöz
     * @param _name Játékos neve
     */
    public Saboteur(String _name){
        if(_name == null){
            name = "Saboteur" + Game.nextPlayerID;
        }
        else{
            name = _name;
        }
        id = Game.nextPlayerID;
        Game.addPlayer(this); //Hozzáadás a játékhoz
        System.out.println("Player"+id+"(Saboteur) created.");
        Game.nextPlayerID++;
    }

    /**
     * Cső csúszóssá tétele
     */
    public void makeSlippery(){
        if (standingonpipe != null) standingonpipe.setSlippery();
    }

    public void details(){
        if(standingonpipe != null)
            System.out.println("Player" + getID() +".stuck: " + stuck + ", Player" + getID() + ".standingonpipe: Pipe" + standingonpipe.getID() + ", Player" + getID() + ".standingon: null");
        else if (standingon != null)
            System.out.println("Player" + getID() +".stuck: " + stuck + ", Player" + getID() + ".standingonpipe: null, Player" + getID() + ".standingon: " + standingon.getClass().getName() + standingon.getID());
        else System.out.println("Player" + getID() +".stuck: " + stuck + ", Player" + getID() + ".standingonpipe: null, Player" + getID() + ".standingon: null");
    }
}
