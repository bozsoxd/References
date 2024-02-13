/**
 * A pálya elemeinek közös interface
 */
public interface IPipeSystem {
    /**
     * Egy kör az adott pályaelemen
     */
    void step();

    /**
     * Játékos rá akar lépni a pályaelemre
     * @param p Játékos aki rálép
     * @return Rá tudott-e lépni
     */
    boolean movedTo(Player p);

    /**
     * Pályaelemek egymáshoz csatlakoztatása
     * @param np Egy másik pályaelem
     * @return Sikeres volt-e a csatlakozás
     */
    boolean add(IPipeSystem np);

    /**
     * Pályaelem megjavítása
     */
    void repair();

    /**
     * Pályaelem elromlása
     */
    void broke();

    /**
     * Getter a pályaelem ID-jára
     * @return A pályaelem ID-ja
     */
    int getID();
    void details();

    /**
     * A pályaelemhez tartozó rajzoló objektum lekérdezése
     * @return A pályaelemhez tartozó kirajzoló
     */
    Drawable getDrawer();

    int getX();
    int getY();
}
