import javax.swing.*;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Lyuk implements Serializable {
    private JButton jb;
    private int ertek;
    private boolean aoldal;

    public Lyuk(JButton j, int e, boolean a){
        jb = j;
        ertek = e;
        aoldal = a;
    }
    public Lyuk(){
        jb = new JButton();
        ertek = 0;
    }



    public void addOne()throws Exception{
        ertek += 1;
        jb.setText(Integer.toString(ertek));
    }
    public void addMore(int i){
        ertek += i;
        jb.setText(Integer.toString(ertek));
    }

    public JButton getJb(){
        return jb;
    }

    public void setZero(){
        ertek = 0;
        jb.setText(Integer.toString(0));
    }

    public int getErtek(){
        return ertek;
    }

    public Lyuk getLyuk(JButton jb, StoreElement se){
        Lyuk ly = new Lyuk();
        for(int i = 0; i < se.getALength(); i++){
            if(se.getAElementAt(i).getJb().equals(jb)){
                ly = se.getAElementAt(i);
            }
        }
        for(int i = 0; i < se.getBLength(); i++){
            if(se.getBElementAt(i).getJb().equals(jb)){
                ly = se.getBElementAt(i);
            }
        }
        return ly;
    }

    public boolean getoldal (){
        return aoldal;
    }

}
