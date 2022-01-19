package de.philipberner.bwinf.runde2.aufgabe3.sevenSegmentDisplay;

public class SevenSegmentDisplay {
    //numbers[i] =>  i => hex number, numbers[i][j] => which segment is on (starts on top and goes clockwise - center is last)
    public final boolean[][] numbers = new boolean[][]{
            {true, true, true, true, true, true, false}, // 0
            {false, true, true, false, false, false, false}, // 1
            {true, true, false, true, true, false, true}, // 2
            {true, true, true, true, false, false, true}, // 3
            {false, true, true, false, false, true, true}, // 4
            {true, false, true, true, false, true, true}, // 5
            {true, false, true, true, true, true, true}, // 6
            {true, true, true, false, false, false, false}, // 7
            {true, true, true, true, true, true, true}, // 8
            {true, true, true, true, false, true, true}, // 9
            {true, true, true, false, true, true, true}, // A
            {false, false, true, true, true, true, true}, // B
            {true, false, false, true, true, true, false}, // C
            {false, true, true, true, true, false, true}, // D
            {true, false, false, true, true, true, true}, // E
            {true, false, false, false, true, true, true}, // F
    };

    public final int[] maxFree = {
            -4, //0
            0, //1
            -3, //2
            -3, //3
            -2, //4
            -3, //5
            -4, //6
            -1, //7
            -5, //8
            -4, //9
            -4, //A
            -3, //B
            -2, //C
            -3, //D
            -3, //E
            -2 //F
    };

    public final int[] maxPlace = {
            1, //0
            5, //1
            2, //2
            2, //3
            3, //4
            2, //5
            1, //6
            4, //7
            0, //8
            1, //9
            1, //A
            2, //B
            3, //C
            2, //D
            2, //E
            3 //F
    };


    //the same as possible moves but it prints the results
    //is only used for debugging purposes
    public void generateChangeList() {
        int[][][] array = new int[16][16][2];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int ilen = 0;
                int jlen = 0;
                int diff = 0;
                for (int k = 0; k < 7; k++) {
                    if (numbers[i][k] != numbers[j][k]) {
                        diff++;
                    }
                    if (numbers[i][k]) {
                        ilen++;
                    }
                    if (numbers[j][k]) {
                        jlen++;
                    }
                }
                array[i][j][1] = jlen - ilen;
                array[i][j][0] = (diff + Math.abs(array[i][j][1]))/2;
            }
            for (int k=15; k>=0; k--) {
                for (int l=k+1; l<16; l++) {
                    if (array[i][k][1] == array[i][l][1] && array[i][k][0] >= array[i][l][0] && array[i][l][0] != -1) {
                        array[i][k][0] = -1;
                        break;
                    }
                }
            }

            String s = i + ": ";
            for (int k=15; k>=0; k--) {
                if (array[i][k][0] != -1) {
                    s += "=> " + k + " (" + array[i][k][0] +") (" +  array[i][k][1] +"), ";
                }
            }
            System.out.println(s);
        }
    }

    //generates all sensible moves and the number of moves needed to complete
    public int[][][] possibleMoves() {
        //array will save alle possible moves
        //array[i][j] - i is the current number - j is the number changed to
        //array[i][j][0] - the number of moves needed to change i to j - tanking away and placing segments from the outside (half moves) are counted as full moves as well
        //array[i][j][0]  => changing i to j isn't sensible
        //array[i][j][1] - the difference in the number of segments turned on in i and j
        int[][][] array = new int[16][16][2];
        //for every starting number i
        for (int i = 0; i < 16; i++) {
            //for every target number j
            for (int j = 0; j < 16; j++) {
                //number of segments turned on in i
                int ilen = 0;
                //number of segments turned on in j
                int jlen = 0;
                //in how many places do i's and j's segments differ
                int diff = 0;
                //for every segment
                for (int k = 0; k < 7; k++) {
                    if (numbers[i][k] != numbers[j][k]) {
                        diff++;
                    }
                    if (numbers[i][k]) {
                        ilen++;
                    }
                    if (numbers[j][k]) {
                        jlen++;
                    }
                }
                //the difference in the number of segments turned on in i and j
                array[i][j][1] = jlen - ilen;
                //the number of moves needed
                array[i][j][0] = (diff + Math.abs(array[i][j][1]))/2;
            }
            //for every target k number
            for (int k=15; k>=0; k--) {
                //for every target number l larger than k
                for (int l=k+1; l<16; l++) {
                    //if l is reachable in less moves than k AND the number of freed segments in k and l is equal AND l is sensible
                    if (array[i][k][1] == array[i][l][1] && array[i][k][0] >= array[i][l][0] && array[i][l][0] != -1) {
                        //move isn't sensible
                        array[i][k][0] = -1;
                        break;
                    }
                }
            }

        }
        return array;
    }

}
