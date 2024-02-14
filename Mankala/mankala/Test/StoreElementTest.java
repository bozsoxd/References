import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StoreElementTest {

    private static JButton a1;
    private static JButton a2;
    private static JButton a3;
    private static JButton a4;
    private static JButton a5;
    private static JButton a6;
    private static JButton a;
    private static JButton b1;
    private static JButton b2;
    private static JButton b3;
    private static JButton b4;
    private static JButton b5;
    private static JButton b6;
    private static JButton b;
    private static Lyuk A1;
    private static Lyuk A2;
    private static Lyuk A3;
    private static Lyuk A4;
    private static Lyuk A5;
    private static Lyuk A6;
    private static Lyuk A;
    private static Lyuk B1;
    private static Lyuk B2;
    private static Lyuk B3;
    private static Lyuk B4;
    private static Lyuk B5;
    private static Lyuk B6;
    private static Lyuk B;
    private static ArrayList<Lyuk> as;
    private static ArrayList<Lyuk> bs;
    private static StoreElement se;

    @BeforeAll
    public static void setUp() {
        a1 = new JButton();
        a2 = new JButton();
        a3 = new JButton();
        a4 = new JButton();
        a5 = new JButton();
        a6 = new JButton();
        a = new JButton();
        b1 = new JButton();
        b2 = new JButton();
        b3 = new JButton();
        b4 = new JButton();
        b5 = new JButton();
        b6 = new JButton();
        b = new JButton();
        A1 = new Lyuk(a1, 5, true);
        A2 = new Lyuk(a2, 5, true);
        A3 = new Lyuk(a3, 5, true);
        A4 = new Lyuk(a4, 5, true);
        A5 = new Lyuk(a5, 5, true);
        A6 = new Lyuk(a6, 5, true);
        A = new Lyuk(a, 0, true);
        B1 = new Lyuk(b1, 5, false);
        B2 = new Lyuk(b2, 5, false);
        B3 = new Lyuk(b3, 5, false);
        B4 = new Lyuk(b4, 5, false);
        B5 = new Lyuk(b5, 5, false);
        B6 = new Lyuk(b6, 5, false);
        B = new Lyuk(b, 0, false);
        as = new ArrayList<>();
        bs = new ArrayList<>();
        as.add(A);
        as.add(A1);
        as.add(A2);
        as.add(A3);
        as.add(A4);
        as.add(A5);
        as.add(A6);
        bs.add(B);
        bs.add(B1);
        bs.add(B2);
        bs.add(B3);
        bs.add(B4);
        bs.add(B5);
        bs.add(B6);
        se = new StoreElement(as, bs);
    }

    @Test
    public void getAElementAt() {
        Assertions.assertEquals(se.getAElementAt(1), A1);
    }

    @Test
    void getBElementAt() {
        Assertions.assertEquals(se.getBElementAt(0), B);
    }

    @Test
    void getALength() {
        Assertions.assertEquals(se.getALength(), 7);
    }

    @Test
    void getBLength() {
        Assertions.assertEquals(se.getBLength(), 7);
    }

    @Test
    void getLyukIndex() {
        Assertions.assertEquals(se.getLyukIndex(A3, se), 3);
    }
}