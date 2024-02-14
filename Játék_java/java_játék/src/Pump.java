import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Egy pumpa példány
 */
public class Pump extends NotPipe {
	private int storedWater; //Mennyi vizet tárol
	private boolean broken; //El van-e törve
	private PumpDrawer drawer; //A rajzolását megvalósító objektum

	/**
	 * Konstruktor, amivel a ciszterna hozza létre a pumpát
	 */

	public Pump() {
		storedWater = 0; //Alapból üres
		broken = false; //Nincs eltörve
		id = Game.nextStructureID;
		System.out.println("Pump"+id+" created");
		Game.nextStructureID++;
		Game.addIPS(this); //Bekerül a játékba
		drawer = new PumpDrawer(this); //Kap egy kirajzoló objektumot
		x = -1;
		y = -1;
		szabadIranyok = new ArrayList<>();

		szabadIranyok.add("fel");
		szabadIranyok.add("jobb");
		szabadIranyok.add("le");
		szabadIranyok.add("bal");

	}

	/**
	 * konstruktor, amivel lehelyezzük a pumpát
	 * @param xx x koordináta
	 * @param yy y koordináta
	 */
	public Pump(int xx, int yy) {
		storedWater = 0; //Nem tárol vizet
		broken = false; //Nincs eltörve
		x = xx;
		y = yy;
		id = Game.nextStructureID;
		System.out.println("Pump"+id+" created");
		Game.nextStructureID++;
		Game.addIPS(this); //Bekerül a játékba
		szabadIranyok = new ArrayList<>();

		szabadIranyok.add("fel");
		szabadIranyok.add("jobb");
		szabadIranyok.add("le");
		szabadIranyok.add("bal");
		drawer = new PumpDrawer(this); //Kap egy kirajzoló objektumot
	}


	/**
	 * ha van víz, csökkenti
	 */
	public void decreaseWater() {
		if(storedWater > 0) storedWater--;
	}

	/**
	 * megkeresi, melyik indexeken vannak most a paraméterek, ha valamelyik nincs sehol, akkor nem csinál semmit, mert nem szomszéd.
	 * Az ő helyükre kerül a 0. és 1. helyen lévő cső, ők pedig bekerülnek a helyükre
	 * @param from Innen jöjjön a víz
	 * @param to Ide menjen a víz
	 */
	//
	public void redirectPump(Pipe from, Pipe to) {
		if(pipes.contains(to)&&pipes.contains(from)) {
			Collections.swap(pipes, pipes.indexOf(to), 1);
			Collections.swap(pipes, pipes.indexOf(from), 0);
		}
	}

	/**
	 * Pumpa megjavítása
	 */
	public void repair() {
		broken = false;
	}

	/**
	 * Pumpa elrontása
	 */
	@Override
	public void broke() {
		broken = true;
	}

	/**
	 * ha törött, vagy nem a bemenettől kapja a vizet, nem csinál semmit. Különben több víz lesz benne.
	 * @param pipe Innen kapja a vízet
	 * @return sikerült-e növeli a tárolt víz mennyiséget
	 */
	//
	@Override
	public boolean addWater(Pipe pipe) {
		if(broken || getPipes().get(0) != pipe) return false;

		storedWater++;
		return true;
	}

	/**
	 * Ha nem törött és van benne víz, meghívja a kimenet targetjét, majd saját magában csökkenti a víz számát.
	 */

	@Override
	public void step() {
		if(!broken && storedWater != 0 && pipes.size() > 1) {
			pipes.get(1).target(this);
			decreaseWater();
		}
		if(Game.getRandom()){
			Random r = new Random();
			if(r.nextInt(10) == 6){
				broke();
			}

		}
	}

	/**
	 * ha nincs még 4 szomszédja, hozzáadja a paraméterként kapott csövet a szomszédjaihoz
	 * @param pipe Egy másik pályaelem
	 * @return Sikerült-e csövet hozzácsatolni
	 */

	@Override
	public boolean add(IPipeSystem pipe) {
		if (getPipes().size() <= 4){
			pipes.add((Pipe)pipe);
			System.out.println("added");
			return true;}
		return false;
	}


	/**
	 * Getter a pumpa állapotára
	 * @return El van-e romolva
	 */
	public boolean getBroken(){return broken;}

	/**
	 * @return A pumpához csatolt csövek
	 */
	public ArrayList<Pipe> getPipes(){return pipes;}

	/**
	 * @return A pumpa ID-ja
	 */
	@Override
	public int getID(){
		return id;
	}

	public void details(){
		String res = "";
		res = res.concat("Pump"+ id+".water: " + storedWater + ", Pump" + id + ".broken: " + broken+", Pump"+ id+".pipes: ");
		for(Pipe p : pipes){
			res = res.concat("Pipe"+p.getID() + "-");
		}
		if(pipes.size() >= 2) {
			res = res.concat(", Pump" + id + ".from: " + "Pipe" + pipes.get(0).getID() + " - Pump" + id + ".to: " + "Pipe" + pipes.get(1).getID());
		} else if(pipes.size() == 1) {
			res = res.concat(", Pump" + id + ".from: " + "Pipe" + pipes.get(0).getID() + " - Pump" + id + ".to: " + "null");
		} else {
			res = res.concat(", Pump"+ id+".from: " + "null - Pump" + id +".to: " + "null");
		}
		System.out.println(res);
	}

	@Override
	public String toString(){
		return "Pump"+id;
	}

	/**
	 * Getter a kirajzoló objektumra
	 * @return A kirajzoló objektum
	 */
	public Drawable getDrawer(){
		return drawer;
	}
}

