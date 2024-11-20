import java.io.Serializable;
import java.util.ArrayList;

public class StoreElement implements Serializable {

    private ArrayList<Lyuk> Alyukak = new ArrayList<>();
    private ArrayList<Lyuk> Blyukak = new ArrayList<>();

    boolean nextPlayer;

    public void setNextPlayer(boolean n){
        nextPlayer = n;
    }

    public boolean getNextPlayer(){
        return nextPlayer;
    }


    public StoreElement(ArrayList<Lyuk> a, ArrayList<Lyuk> b){
        Alyukak = a;
        Blyukak = b;
    }

    public Lyuk getAElementAt(int i){
        return Alyukak.get(i);
    }

    public Lyuk getBElementAt(int i){
        return Blyukak.get(i);
    }

    public int getALength(){
        return Alyukak.size();
    }
    public int getBLength(){
        return Blyukak.size();
    }

    public int getLyukIndex(Lyuk ly, StoreElement se){
        int index = 0;

        for(int i = 0; i < se.getBLength(); i++){
            if(ly.getoldal() == true){
                if(ly.equals(Alyukak.get(i))){
                    index = i;
                }
            }
            else{
                if(ly.equals(Blyukak.get(i))){
                    index = i;
                }
            }
        }
        return index;
    }

}
