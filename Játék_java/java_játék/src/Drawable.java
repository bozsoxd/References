import java.awt.event.MouseListener;

/**
 * Közös interface a kirajzolható osztályoknak
 */
public interface Drawable {
    /**
     * Kirajzoló függvény
     */
    void draw();

    /**
     * MouseListener csatlakoztatása
     * @param me MouseListener
     */
    void addMouseListener(MouseListener me);

    /**
     * @return A hozzá tartozó objektum
     */
    IPipeSystem getObject();
}
