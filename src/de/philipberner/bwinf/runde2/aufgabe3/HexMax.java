package de.philipberner.bwinf.runde2.aufgabe3;

import de.philipberner.bwinf.runde2.aufgabe3.sevenSegmentDisplay.SevenSegmentDisplay;

import java.util.Arrays;

public class HexMax {
    private final int moves;
    private final HexNumber number;
    private final int[][][] sensibleMoves;
    private final int[] maxFree;
    private final int[] maxPlace;
    private int tries = 0;

     HexMax(int moves, HexNumber number) {
        this.moves = moves;
        this.number = number;
        SevenSegmentDisplay sevenSegmentDisplay = new SevenSegmentDisplay();
        this.sensibleMoves = sevenSegmentDisplay.possibleMoves();
        this.maxFree = calculateMaxFree(sevenSegmentDisplay);
        this.maxPlace = calculateMaxPlace(sevenSegmentDisplay);
         System.out.println(Arrays.toString(maxFree));
         System.out.println(Arrays.toString(maxPlace));
    }

    public int[] calculateMaxPlace(SevenSegmentDisplay sevenSegmentDisplay) {
         int[] array = new int[number.getDigits().length];
         array[array.length-1] = sevenSegmentDisplay.maxPlace[number.getDigits()[array.length-1]];
         for (int i=array.length-2; i>=0; i--) {
            array[i] = array[i+1] + sevenSegmentDisplay.maxPlace[number.getDigits()[i]];
         }
         return array;
    }

    public int[] calculateMaxFree(SevenSegmentDisplay sevenSegmentDisplay) {
        int[] array = new int[number.getDigits().length];
        array[array.length-1] = sevenSegmentDisplay.maxFree[number.getDigits()[array.length-1]];
        for (int i=array.length-2; i>=0; i--) {
            array[i] = array[i+1] + sevenSegmentDisplay.maxFree[number.getDigits()[i]];
        }
        return array;
    }

    public HexNumber maxHexNumber() {
         //change the first character
        for (int i=15; i>=number.getDigits()[0]; i--) {
            //if move is sensible (!= -1) and move is reachable (in number of moves possible)
            if (sensibleMoves[number.getDigits()[0]][i][0] != -1 && sensibleMoves[number.getDigits()[0]][i][0] <= moves) {
                //updates the first digit in a working copy
                int[] val = number.getDigits().clone();
                val[0] = i;
                //calculates maxHexNumber for this starting position
                int[] res = new int[0];
                if (sensibleMoves[number.getDigits()[0]][i][1]<0) {
                    res = nextMove(val, sensibleMoves[number.getDigits()[0]][i][0] + sensibleMoves[number.getDigits()[0]][i][1], sensibleMoves[number.getDigits()[0]][i][1], 1);
                } else {
                    res = nextMove(val, sensibleMoves[number.getDigits()[0]][i][0], sensibleMoves[number.getDigits()[0]][i][1], 1);
                }
                //if a number is reachable
                if (res.length != 0) {
                    System.out.println(tries);
                    return new HexNumber(res);
                }
            }
        }
        //there is no reachable hex number that is larger
        return number;
    }

    private int[] nextMove(int[] num, int movesMade, int free, int place) {
        tries++;
         //if index out of bounds
        if (place >= num.length) {
            //current number is reachable? => return number
            if (movesMade <= moves && free == 0) {
                return num;
            }
            //number isn't reachable
            return new int[0];
        }
         //has the number changed so far?
        if (movesMade == 0 && free == 0) {
            //if the digits before haven't changed => this digit needs to increase its value or stay the same
            for (int i=15; i>=number.getDigits()[place]; i--) {
                //if move is sensible (!= -1) and move is reachable (in number of moves possible)
                if (sensibleMoves[number.getDigits()[place]][i][0] != -1 && sensibleMoves[number.getDigits()[place]][i][0] <= moves) {
                    //update the digit
                    num[place] = i;
                    //nextMove
                    return nextMove(num, sensibleMoves[number.getDigits()[place]][i][0], sensibleMoves[number.getDigits()[place]][i][1], place+1);
                }
            }
        } else {
            //if the digits before have changed => every value is possible for this and following digits
            for (int i=15; i>=0; i--) {
                //if move is sensible (!= -1) and move is reachable (movesNeeded <= moves - movesMade + ((-)freed positions) + (positions that will be freed or placed)
                if (sensibleMoves[number.getDigits()[place]][i][0] != -1 && sensibleMoves[number.getDigits()[place]][i][0] <= moves - movesMade + Math.min(free, 0) + Math.abs(sensibleMoves[number.getDigits()[place]][i][1])) {
                    //does this move make sense? => can the number of freed positions be used?
                    if (maxPlace.length-1 >= place+1) {
                        if (maxFree[place+1] > free + sensibleMoves[number.getDigits()[place]][i][1] || maxPlace[place+1] < free + sensibleMoves[number.getDigits()[place]][i][1]) {
                            continue;
                        }
                    }
                    num[place] = i;
                    //make next move
                    //movesMade = movesMade + (moves made this turn ) - (positions freed [isn't treated as a move - but sensibleMoves treats it as such])
                    int[] res = nextMove(num , movesMade + sensibleMoves[number.getDigits()[place]][i][0] + Math.min(sensibleMoves[number.getDigits()[place]][i][1], 0) , free+sensibleMoves[number.getDigits()[place]][i][1], place+1);
                    //if the move isn't possible make another
                    if (res.length != 0) {
                        return  res;
                    }
                }
            }
        }

         return new int[0];
    }


}
