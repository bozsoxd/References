import javax.swing.*;

public class Game {

    private Lyuk last;
    private boolean lastNull = false;
    public Lyuk getLast() throws NullPointerException{
        return last;
    }

    public void pressed(JButton jb, StoreElement se){
        Lyuk ly = new Lyuk();
        ly = ly.getLyuk(jb, se);
        boolean actualPlayer = ly.getoldal();
        int position = se.getLyukIndex(ly, se);
        int stones = ly.getErtek();
        ly.setZero();
        boolean actualSide = ly.getoldal();
        while(stones > 0){
            try{
                stones = lepEgyikOldal(se, stones, position, actualSide, actualPlayer);
            }
            catch (Exception e){

            }
            actualSide = !actualSide;
            position = -1;
        }
        rablas(actualPlayer, se, !actualSide);
    }

    public int lepEgyikOldal(StoreElement se, int st, int pos, boolean jelenlegiOldal, boolean actualPlayer) throws Exception{
        int stones = st;
        if(jelenlegiOldal == true){
            int i = pos+1;
            while(stones > 0 && i < se.getALength()){
                if(actualPlayer == true && i == 0){
                    i++;
                }
                else{
                    if(se.getAElementAt(i).getErtek() == 0 && i != 0){
                        lastNull = true;
                    }
                    else{
                        lastNull = false;
                    }
                    se.getAElementAt(i).addOne();
                    last = se.getAElementAt(i);
                    i++;
                    stones--;
                }

            }

        }
        else{
            int i = pos+1;
            while(stones > 0 && i < se.getBLength()){
                if(actualPlayer == false && i == 0){
                    i++;
                }
                else{
                    if(se.getBElementAt(i).getErtek() == 0 && i != 0){
                        lastNull = true;
                    }
                    else{
                        lastNull = false;
                    }
                    se.getBElementAt(i).addOne();
                    last = se.getBElementAt(i);
                    i++;
                    stones--;
                }

            }
        }
        return stones;
    }

    public void ertekKiir(StoreElement se) {
        for (int i = se.getBLength() - 1; i >= 0; i--) {
            System.out.print(se.getBElementAt(i).getErtek());
        }
        System.out.println();
        for (int i = 0; i < se.getALength(); i++) {
            System.out.print(se.getAElementAt(i).getErtek());
        }
        System.out.println();
    }

    public boolean checkEndGame(StoreElement se){
        boolean end = true;
        for(int i = 1; i < se.getALength(); i++){
            if(se.getAElementAt(i).getErtek() != 0){
               end = false;
            }
        }
        if(end){
            return true;
        }
        end = true;
        for(int i = 1; i < se.getBLength(); i++){
            if(se.getBElementAt(i).getErtek() != 0){
                end = false;
            }
        }
        if(end){
            return true;
        }

        return false;
    }

    public void rablas(boolean actualPlayer, StoreElement se, boolean actualSide){
        if(lastNull == true && actualPlayer == actualSide){
            int side1 = getLast().getErtek();
            int side2;
            int lastIndex = se.getLyukIndex(getLast(), se);
            Lyuk ebbol = new Lyuk();
            if(actualPlayer == true && se.getBElementAt(7 - se.getLyukIndex(getLast(), se)).getErtek() != 0){
                ebbol = se.getBElementAt(7 - se.getLyukIndex(getLast(), se));
                se.getBElementAt(0).addMore(side1);
                side2 = ebbol.getErtek();
                se.getBElementAt(0).addMore(side2);
                getLast().setZero();
                ebbol.setZero();
            }
            else if(actualPlayer == false && se.getAElementAt(7 - se.getLyukIndex(getLast(), se)).getErtek() != 0){
                ebbol = se.getAElementAt(7 - se.getLyukIndex(getLast(), se));
                se.getAElementAt(0).addMore(side1);
                side2 = ebbol.getErtek();
                se.getAElementAt(0).addMore(side2);
                getLast().setZero();
                ebbol.setZero();
            }



        }
    }

}
