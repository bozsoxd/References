import java.util.ArrayList;

public class Init {
    private Lyuk A;
    private Lyuk A1;
    private Lyuk A2;
    private Lyuk A3;
    private Lyuk A4;
    private Lyuk A5;
    private Lyuk A6;
    private Lyuk B;
    private Lyuk B1;
    private Lyuk B2;
    private Lyuk B3;
    private Lyuk B4;
    private Lyuk B5;
    private Lyuk B6;
    private ArrayList<Lyuk> a;
    private ArrayList<Lyuk> b;

    private StoreElement store;

    public Init(Table table){
        A = new Lyuk(table.getA(), 0, true);
        A1 = new Lyuk(table.getA1(), 5, true);
        A2 = new Lyuk(table.getA2(), 5, true);
        A3 = new Lyuk(table.getA3(), 5, true);
        A4 = new Lyuk(table.getA4(), 5, true);
        A5 = new Lyuk(table.getA5(), 5, true);
        A6 = new Lyuk(table.getA6(), 5, true);
        B = new Lyuk(table.getB(), 0, false);
        B1 = new Lyuk(table.getB1(), 5, false);
        B2 = new Lyuk(table.getB2(), 5, false);
        B3 = new Lyuk(table.getB3(), 5, false);
        B4 = new Lyuk(table.getB4(), 5, false);
        B5 = new Lyuk(table.getB5(), 5, false);
        B6 = new Lyuk(table.getB6(), 5, false);
        a = new ArrayList<Lyuk>();
        a.add(A);
        a.add(A1);
        a.add(A2);
        a.add(A3);
        a.add(A4);
        a.add(A5);
        a.add(A6);
        b = new ArrayList<Lyuk>();
        b.add(B);
        b.add(B1);
        b.add(B2);
        b.add(B3);
        b.add(B4);
        b.add(B5);
        b.add(B6);
        store = new StoreElement(a, b);
    }

    public StoreElement storeInit(){
        for(int i = store.getBLength()-1; i >= 0; i--){
            System.out.print(store.getBElementAt(i).getErtek());
        }
        System.out.println();
        for(int i = 0; i < store.getALength(); i++){
            System.out.print(store.getAElementAt(i).getErtek());
        }
        System.out.println();



        return store;
    }



}
