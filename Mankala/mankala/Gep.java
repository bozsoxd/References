import javax.swing.*;

public class Gep {

    public JButton mitlep(StoreElement se){

        for (int i = 1; i < se.getBLength(); i++){
            if((7-i) == se.getBElementAt(i).getErtek()){
                System.out.println(i + "belep");
                return se.getBElementAt(i).getJb();
            }
        }
        for(int i = 1; i < se.getBLength(); i++){
            if(se.getBElementAt(i).getErtek() == 0){
                for(int j = 1; j < i; j++){
                    if((i-j) == se.getBElementAt(j).getErtek()){
                        System.out.println(j + "rablas");
                        return se.getBElementAt(j).getJb();
                    }
                }
            }
        }
        int max = 0;
        JButton maxjb = new JButton();
        for(int i = 1; i < se.getBLength(); i++){
            if(se.getBElementAt(i).getErtek() > max){
                max = se.getBElementAt(i).getErtek();
                maxjb = se.getBElementAt(i).getJb();
            }
        }

        System.out.println("legnagyobb");
        return maxjb;




    }



}
