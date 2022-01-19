package de.philipberner.bwinf.runde2.aufgabe3;

import de.philipberner.bwinf.runde2.aufgabe3.sevenSegmentDisplay.NumberChanger;
import de.philipberner.bwinf.runde2.aufgabe3.sevenSegmentDisplay.SevenSegmentNumber;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        //Scans File
        File file;
        Scanner scanner;
        try {
            file = new File(args[0]);
            scanner = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Benutzung: ... <Dateipfad> <Schritte[true/false]> <Zwischenergebnisse[true/false]>");
            return;
        }
        //scans in the hex number
        String hex = scanner.next();
        //scans in the number of moves
        int moves = scanner.nextInt();
        boolean steps = false;
        boolean intermediateResult = false;
        //should the different moves be shown
        if (args.length >= 2) {
            steps = Boolean.parseBoolean(args[1]);
        }

        //should the result after every move be shown
        if (args.length >= 3) {
            intermediateResult = Boolean.parseBoolean(args[2]);
        }

        //calculates the maximal hex number in the number of moves
        HexMax max = new HexMax(moves, new HexNumber(hex));
        HexNumber num = max.maxHexNumber();
        System.out.println(num.getHex());


        if (steps || intermediateResult) {
            //calculates the steps
            NumberChanger numberChanger = new NumberChanger(new SevenSegmentNumber(new HexNumber(hex).getDigits()), new SevenSegmentNumber(num.getDigits()));
            List<int[][]> list = numberChanger.calculateSteps();
            //prints the steps
            if (steps) {
                for (int i=0; i<list.size(); i++) {
                    System.out.println(i+1+". "+Arrays.deepToString(list.get(i)));
                }
            }

            //calculates and prints the results after every move
            if (intermediateResult) {
                List<SevenSegmentNumber> list2 = numberChanger.showSteps(list);
                for (int i=0; i<list2.size(); i++) {
                    System.out.println(i+1+ ". " + Arrays.deepToString(list2.get(i).getNumber()));
                }
            }
        }

    }
}
