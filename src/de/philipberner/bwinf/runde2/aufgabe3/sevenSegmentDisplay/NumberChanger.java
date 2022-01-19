package de.philipberner.bwinf.runde2.aufgabe3.sevenSegmentDisplay;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NumberChanger {
    private final SevenSegmentNumber start;
    private final SevenSegmentNumber finish;

    public NumberChanger(@NotNull SevenSegmentNumber start,  @NotNull SevenSegmentNumber finish) throws Exception {
        if (finish.getNumber().length != start.getNumber().length) {
            throw new Exception("finish and start need the same length");
        }
        this.start = start;
        this.finish = finish;
    }

    //returns a list of steps
    public List<SevenSegmentNumber> showSteps(List<int[][]> steps) {
        List<SevenSegmentNumber> list = new ArrayList<>();
        for (int[][] step : steps) {
            start.getNumber()[step[0][0]][step[0][1]] = false;
            start.getNumber()[step[1][0]][step[1][1]] = true;
            list.add(new SevenSegmentNumber(start));
        }
        return list;
    }

    //returns a list of moves
    public List<int[][]> calculateSteps() throws Exception {
        //list that is returned
        List<int[][]> list = new ArrayList<>();
        //freeing
        List<int[]> toFalse = new ArrayList<>();
        //placing
        List<int[]> toTrue = new ArrayList<>();
        //fill lists
        for (int i=0; i<finish.getNumber().length; i++) {
            for (int j=0; j<finish.getNumber()[i].length; j++) {
                if (start.getNumber()[i][j] != finish.getNumber()[i][j]) {
                    if (start.getNumber()[i][j]) {
                        toFalse.add(new int[] {i, j});
                    } else {
                        toTrue.add(new int[]{i, j});
                    }
                }
            }
        }
        if (toFalse.size() != toTrue.size()) {
            throw new Exception("Numbers need to have the same number of positions");
        }
        //combine toFalse and toTrue
        for (int i=0; i<toFalse.size(); i++) {
            list.add(new int[][]{toFalse.get(i), toTrue.get(i)});
        }

        return list;
    }
}
