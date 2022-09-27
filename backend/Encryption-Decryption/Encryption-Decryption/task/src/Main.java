import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    private static final int lastAlphabet = 26;
    public static final int lastValue = 127;
    private static final String smallCaseAlphabets = "abcdefghijklmnopqrstuvwxyz";
    public static final String capitalCaseAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {


        String operation = "enc";
        String input = "";
        int key = 0;
        String outputFileName ="";
        String algorithm = "shift";
        StringBuilder output = new StringBuilder();
        
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode" -> operation = args[++i];
                case "-key" -> key = Integer.parseInt(args[++i]);
                case "-data" -> input = args[++i];
                case "-in" -> input = readFromFile(args[++i]);
                case "-out" -> outputFileName = args[++i];
                case "-alg" -> algorithm = args[++i];
            }
        }

        switch (algorithm) {
            case "unicode" -> {
                switch (operation) {
                    case "enc" -> encodeUnicode(input.toCharArray(), key, output);
                    case "dec" -> decodeUnicode(input.toCharArray(), key, output);
                }
            }
            case "shift" -> {
                switch (operation) {
                    case "enc" -> encodeShift(input.toCharArray(), key, output);
                    case "dec" -> decodeShift(input.toCharArray(), key, output);
                }
            }
        }


        if (outputFileName.equals("")) {
            System.out.println(output);
        } else {
            writeToFile(output.toString(), outputFileName);
        }
    }

    private static void decodeShift(char[] input, int key, StringBuilder output) {
        for (char ch : input) {
            if (smallCaseAlphabets.indexOf(ch) != -1) {
                output.append(smallCaseAlphabets.charAt((lastAlphabet + smallCaseAlphabets.indexOf(ch) - key) % lastAlphabet));
            } else if (capitalCaseAlphabets.indexOf(ch) != -1) {
                output.append(capitalCaseAlphabets.charAt((lastAlphabet + capitalCaseAlphabets.indexOf(ch) - key) % lastAlphabet));
            } else {
                output.append(ch);
            }
        }
    }

    private static void encodeShift(char[] input, int key, StringBuilder output) {
        for (char ch : input) {
            if (smallCaseAlphabets.indexOf(ch) != -1) {
                output.append(smallCaseAlphabets.charAt((smallCaseAlphabets.indexOf(ch) + key) % lastAlphabet));
            } else if (capitalCaseAlphabets.indexOf(ch) != -1) {
                output.append(capitalCaseAlphabets.charAt((capitalCaseAlphabets.indexOf(ch) + key) % lastAlphabet));
            } else {
                output.append(ch);
            }
        }
    }

    private static void decodeUnicode(char[] input, int key, StringBuilder output) {
        for (char ch : input) {
            output.append((char)(((int) ch - key) % lastValue));
        }
    }

    private static void encodeUnicode(char[] input, int key, StringBuilder output) {
        for (char ch : input) {
            output.append((char)(((int) ch + key) % lastValue));
        }
    }

    private static String readFromFile(String fileName) {

        StringBuilder input = new StringBuilder();
        File file = new File(fileName);

        try (Scanner readFile = new Scanner(file)) {
            while (readFile.hasNextLine()) {
                input.append(readFile.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error: File not found");
        }
        return input.toString();
    }

    private static void writeToFile(String output, String fileName) {
        File file = new File(fileName);

        try (PrintWriter printWriter = new PrintWriter(file)) {

            printWriter.println(output);
        } catch (IOException e) {
            System.out.println("Error: Can't write output to file");
        }
    }
}

