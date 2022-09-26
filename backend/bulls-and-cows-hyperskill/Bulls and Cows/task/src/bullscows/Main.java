package bullscows;

import java.util.*;

public class Main {
    final static private Scanner readIp = new Scanner(System.in);
    final static private String symbolSet = "0123456789abcdefghijklmnopqrstuvwxyz";
    static private int cow = 0;
    static private int bull = 0;

    public static void main(String[] args) {
        System.out.println("Input the length of the secret code:");
        String len = readIp.nextLine();
        final int codeSize = checkCodeLength(len);
        if (codeSize != 0) {
            System.out.println("Input the number of possible symbols in the code:");
            String symbolCount = readIp.nextLine();
            final int charset = checkSymbolCount(symbolCount, codeSize);
            if (charset != 0) {
                String code = generateSecretCode(codeSize, charset);
                char[] codeArray = code.toCharArray();
                printMessage(codeSize, charset);
                System.out.println("Okay, let's start a game!");
                int turn = 0;
                boolean isGuessCorrect = false;
                while (!isGuessCorrect) {
                    cow = 0;
                    bull = 0;
                    turn++;
                    System.out.println("Turn " + turn + ":");
                    char[] guessArray = readIp.nextLine().toCharArray();
                    if (checkGuess(guessArray, codeSize)) {
                        isGuessCorrect = analyzeGuess(codeArray, guessArray, codeSize);
                        constructGrade();
                    } else {
                        break;
                    }
                }
                if (isGuessCorrect) {
                    System.out.println("Congratulations! You guessed the secret code.");
                }
            }
        }
    }

    private static boolean checkGuess(char[] guessArray, int codeSize) {
        return guessArray.length == codeSize;
    }

    private static int checkCodeLength(String len) {
        int codeLen = 0;
        try {
            codeLen = Integer.parseInt(len);

            if (codeLen > 36 || codeLen == 0) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols.", codeLen);
                System.out.println();
                codeLen = 0;
            }
        } catch (Exception e) {
            System.out.println("Error: \"" + len + "\" isn't a valid number.");
        }
        return codeLen;
    }

    private static int checkSymbolCount(String symbolCount, int codeSize) {
        int symbolLen = 0;
        try {
            symbolLen = Integer.parseInt(symbolCount);
            if (symbolLen > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                symbolLen = 0;
            }
            if (symbolLen < codeSize) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", codeSize, symbolLen);
                System.out.println();
                symbolLen = 0;
            }
        } catch (Exception e) {
            System.out.println("Error: \"" + symbolCount + "\" isn't a valid number.");
        }
        return symbolLen;
    }

    private static void printMessage(int codeSize, int charset) {
        StringBuilder msg = new StringBuilder("The secret is prepared: ");

        msg.append("*".repeat(Math.max(0, codeSize)));
        msg.append(" (0-9");

        if (charset > 10) {
            msg.append(", ");
            msg.append(String.format("%c-%c", symbolSet.charAt(10), symbolSet.charAt(charset - 1)));
        }
        msg.append(").");
        System.out.println(msg);
    }

    private static String generateSecretCode(int codeSize, int charset) {
        StringBuilder randomNumbers = new StringBuilder();
        while (true) {
            int randomNum = (int) (Math.random() * charset);
            char temp = symbolSet.charAt(randomNum);
            if (randomNumbers.indexOf(String.valueOf(temp)) == -1) {
                randomNumbers.append(temp);
                if (randomNumbers.length() == codeSize) {
                    return randomNumbers.toString();
                }
            }
        }
    }

    private static boolean analyzeGuess(char[] codeArray, char[] guessArray, int codeSize) {
        for (int i = 0; i < guessArray.length; i++) {
            for (int j = 0; j < codeArray.length; j++) {
                if (guessArray[i] == codeArray[j] && i == j) {
                    bull++;
                    break;
                } else if (guessArray[i] == codeArray[j]) {
                    cow++;
                    break;
                }
            }
        }
        return bull == codeSize;
    }

    private static void constructGrade() {
        StringBuilder grade = new StringBuilder("Grade: ");
        if (cow == 0 && bull == 0) {
            grade.append("None");
        } else {
            if (cow == 1) {
                grade.append(String.format("%d cow", cow));
            }
            if (cow > 1) {
                grade.append(String.format("%d cows", cow));
            }
            if (cow != 0 && bull != 0) {
                grade.append(" and ");
            }
            if (bull == 1) {
                grade.append(String.format("%d bull", bull));
            }
            if (bull > 1) {
                grade.append(String.format("%d bulls", bull));
            }
        }
        grade.append(".");
        System.out.println(grade);
    }
}