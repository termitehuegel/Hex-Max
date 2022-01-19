package de.philipberner.bwinf.runde2.aufgabe3.sevenSegmentDisplay;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SevenSegmentNumber {
    private final boolean[][] number;

    public SevenSegmentNumber(int[] digitValues) {
        SevenSegmentDisplay sevenSegmentDisplay = new SevenSegmentDisplay();
        this.number = new boolean[digitValues.length][7];
        for (int i=0; i<digitValues.length; i++) {
            number[i] = sevenSegmentDisplay.numbers[digitValues[i]];
        }
    }

    public SevenSegmentNumber(boolean[][] number) {
        this.number = number;
    }

    public boolean[][] getNumber() {
        return number;
    }

    public boolean equals(SevenSegmentNumber o) {
        if (this == o) return true;
        return Arrays.deepEquals(number, o.number);
    }

    //builds a SevenSegmentNumber as a copy of o
    public SevenSegmentNumber (@NotNull SevenSegmentNumber o) {
        number = new boolean[o.getNumber().length][o.getNumber()[0].length];
        for (int i=0; i<o.getNumber().length; i++) {
            number[i] = o.getNumber()[i].clone();
        }
    }
}
