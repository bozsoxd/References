import java.util.ArrayList;

/**
 * Egy forrást megvalósító osztály
 */
public class Source extends NotPipe {

    private SourceDrawer drawer; //a kirajzolásáért felelős objektum

    /**
     * A source egy lépése
     */

    @Override
    public void step() {
        for(Pipe p: getPipes()){
            p.target(this);
        }
    }


    /**
     * Konstruktor a forráshoz
     * @param xx x koordináta
     * @param yy y koordináta
     */
    public Source(int xx, int yy){
        id = Game.nextStructureID;
        System.out.println("Source"+id+" created");
        Game.nextStructureID++;
        Game.addIPS(this); //Hozzáadás a játéékhoz
        x = xx;
        y = yy;
        szabadIranyok = new ArrayList<>();

        szabadIranyok.add("fel");
        szabadIranyok.add("jobb");
        szabadIranyok.add("le");
        szabadIranyok.add("bal");
        drawer = new SourceDrawer(this); //Kirajzoló objektum
    }

    public void details(){
        System.out.print("Source"+ id+".pipes: ");
        for(Pipe p : pipes){
            System.out.print(p.toString() + "-");
        }
    }

    /**
     * ha nincs még 4 szomszédja, hozzáadja a paraméterként kapott csövet a szomszédjaihoz
     * @param pipe Egy másik pályaelem
     * @return Sikerült-e csatlakoztatni
     */
    @Override
    public boolean add(IPipeSystem pipe) {
        if (getPipes().size() <= 4){
            pipes.add((Pipe)pipe);
            return true;}
        return false;
    }

    /**
     * getter az ID-ra
     * @return Forrás ID-ja
     */
    @Override
    public int getID(){
        return id;
    }

    /**
     * getter a kirajzolójára
     * @return az őt kirajzoló objektum
     */
    public Drawable getDrawer(){
        return drawer;
    }
}
