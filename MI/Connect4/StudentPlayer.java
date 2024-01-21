import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

//


    @Override
    public int step(Board _board) {
        return miniMax(_board, 9, -9999999, 9999999, true)[0];
    }


    private int[] miniMax(Board board, int depth, int alpha, int beta, boolean maxPlayer){
        boolean terminal = board.gameEnded();
        List<Integer> validSteps = board.getValidSteps();
        if (depth == 0 || terminal) {
            if (terminal) {
                if(board.getWinner() == 2) return new int[]{-1, 9999999};
                else if(board.getWinner() == 1) return new int[]{-1, -9999999};
                else return new int[]{-1, 0};
            }
            else return new int[]{-1, scoreCalc(board, this.playerIndex)};
        }

        if(maxPlayer){
            int value = -9999999;
            int column = validSteps.get(new Random().nextInt(validSteps.size()));
            for(int i: validSteps){
                Board boardCopy = new Board(board);
                boardCopy.step(this.playerIndex, i);
                int newScore = miniMax(boardCopy, depth-1, alpha, beta, false)[1];
                if(newScore > value){
                    value = newScore;
                    column = i;
                }
                alpha = max(alpha, value);
                if(alpha >= beta){
                    break;
                }
            }
            return new int[]{column, value};
        }
        else{
            int value = 9999999;
            int column = validSteps.get(new Random().nextInt(validSteps.size()));
            for(int i: validSteps){
                Board boardCopy = new Board(board);
                boardCopy.step(1, i);
                int newScore = miniMax(boardCopy, depth - 1, alpha, beta, true)[1];
                if(newScore < value){
                    value = newScore;
                    column = i;
                }
                beta = min(beta, value);
                if(alpha >= beta){
                    break;
                }
            }
            return new int[]{column, value};
        }



    }






    private int scoreCalc(Board _board, int playerIndex){
        int score = 0;



        score += centerCount(_board, playerIndex) * 4;

        //vízszint:
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 4; j++){
                int[] four = Arrays.copyOfRange(_board.getState()[i],j , j + 4);
                score += scoreOfFour(four, playerIndex);
            }
        }

        //függőleges
        for(int i = 0; i < 7; i++){
            int[] oszlop = new int[6];
            for(int j = 0; j < 6; j++){
                oszlop[j] = _board.getState()[j][i];
            }
            for (int j = 0; j < 3; j++){
                int[] four = Arrays.copyOfRange(oszlop, j, j + 4);
                score += scoreOfFour(four, playerIndex);
            }
        }

        //Pozitív átló
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                int[] four = new int[4];
                for(int k = 0; k < 4; k++){
                    four[k] = _board.getState()[i + k][j + k];
                }
                score += scoreOfFour(four, playerIndex);
            }
        }

        //negatív átló
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                int[] four = new int[4];
                for(int k = 0; k < 4; k++){
                    four[k] = _board.getState()[i + 3 - k][j + k];
                }
                score += scoreOfFour(four, playerIndex);
            }
        }

        return score;
    }

    private int centerCount(Board _board, int playerIndex){
        int centerScore = 0;
        for(int i = 0; i < 6; i++){
            if(_board.getState()[i][3] == playerIndex) centerScore++;
        }
        return centerScore;
    }


    private int outOfFour(int[] four, int index){
        int IndexCount = 0;

        for(int i: four){
            if(i == index)IndexCount++;
        }
        return IndexCount;
    }


    private int scoreOfFour(int[] four, int playerIndex){
        int score = 0;
        if(outOfFour(four, playerIndex) == 4) score += 100;
        else if(outOfFour(four, playerIndex) == 3 && outOfFour(four, 0) == 1) score += 5;
        else if(outOfFour(four, playerIndex) == 2 && outOfFour(four, 0) == 2) score += 2;
        if(outOfFour(four, 1) == 3 && outOfFour(four, 0) == 1) score -= 100;
        else if(outOfFour(four, 1) == 2 && outOfFour(four, 0) == 2) score -= 2;
        return score;
    }



















}
