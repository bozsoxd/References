import java.awt.*;

/**
 * Olyan flowLayout megvalósítás, ahol a benne levő panelek közt nincsen hely
 */
public class NoPaddingFlowLayout extends FlowLayout {
    /**
     * Konstruktor Vgap = 0 és Hgap = 0
     */
    public NoPaddingFlowLayout() {
        setVgap(0); //Nincs gap egyik oldalon sem
        setHgap(0);
    }


    /**
     * @param target the container that needs to be laid out
     * @return dimenzió
     */
    @Override
    public Dimension preferredLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim = super.preferredLayoutSize(target);
            dim.width -= getHgap();
            return dim;
        }
    }

    /**
     * @param target the container that needs to be laid out
     * @return dimenzió
     */
    @Override
    public Dimension minimumLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim = super.minimumLayoutSize(target);
            dim.width -= getHgap();
            return dim;
        }
    }
}