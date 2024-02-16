## Description

Ez egy minimax algoritmus, amelynek célja a Connect4 játék megnyerése. 9 méjségig megy az algoritmus és alpha/beta nyesést használ, a jobb teljesítmény érdekében. Így a győzelmének nagyon magas esélye biztosított.

## Code fragments

```java title="max algoritmus" showLineNumbers
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
                //highlight-start
                alpha = max(alpha, value);
                if(alpha >= beta){
                    break;
                }
                //highlight-end
            }
            return new int[]{column, value};
        }
```
:::note
A jelölt részen látható az említett alpha/beta nyesés.
:::
```java title="Score calcing" showLineNumbers
private int scoreOfFour(int[] four, int playerIndex){
        int score = 0;
        if(outOfFour(four, playerIndex) == 4) score += 100;
        else if(outOfFour(four, playerIndex) == 3 && outOfFour(four, 0) == 1) score += 5;
        else if(outOfFour(four, playerIndex) == 2 && outOfFour(four, 0) == 2) score += 2;
        if(outOfFour(four, 1) == 3 && outOfFour(four, 0) == 1) score -= 100;
        else if(outOfFour(four, 1) == 2 && outOfFour(four, 0) == 2) score -= 2;
        return score;
    }
```