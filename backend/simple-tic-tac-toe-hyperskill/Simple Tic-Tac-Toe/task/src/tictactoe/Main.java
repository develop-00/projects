package tictactoe;

import java.util.Scanner;

public class Main {
    final private static Scanner readIp = new Scanner(System.in);
    final static int fieldSize = 3;
    final static String[][] gameField = new String[fieldSize][fieldSize];

    public static void main(String[] args) {

        // Print empty game grid.
        fillGrid();
        printGrid();

        String ch = "X";
        boolean isEnded;
        while (true) {
            System.out.println("Enter the coordinates: ");
            String xLoc = readIp.next();
            String yLoc = readIp.next();

            String response = updateGrid(ch, xLoc, yLoc);
            if (!response.equals("OK")) {
                System.out.println(response);
            } else {
                printGrid();
                isEnded = analyzeGameField();
                if (isEnded) {
                    break;
                }
                ch = ch.equals("X") ? "O" : "X";
            }
        }
    }


    private static void fillGrid() {
        for (int r = 0; r < fieldSize; r++) {
            for (int c = 0; c < fieldSize; c++) {
                gameField[r][c] = " ";
            }
        }
    }

    private static void printGrid() {
        // Print game field.
        System.out.println("---------");
        for (int r = 0; r < fieldSize; r++) {
            System.out.print("| ");
            for (int c = 0; c < fieldSize; c++) {
                System.out.print(gameField[r][c] + " ");
            }
            System.out.print("| ");
            System.out.println();
        }
        System.out.println("---------");
    }

    private static String updateGrid(String ch, String xLoc, String yLoc) {
        int xPos = -1;
        int yPos = -1;

        try {
            xPos += Integer.parseInt(xLoc);
            yPos += Integer.parseInt(yLoc);
        } catch (Exception e) {
            return "You should enter numbers!";
        }

        if ((xPos < 0 || xPos >= fieldSize) || (yPos < 0 || yPos >= fieldSize)) {
            return "Coordinates should be from 1 to 3!";
        }

        if (!gameField[xPos][yPos].equals(" ")) {
            return "This cell is occupied! Choose another one!";
        }

        gameField[xPos][yPos] = ch;
        return "OK";
    }

    private static boolean analyzeGameField() {

        // Analyze game field for winner.
        if (isTicTacToeWinner("O")) {
            System.out.println("O wins");
        } else if (isTicTacToeWinner("X")) {
            System.out.println("X wins");
        } else if (remainingSpace() < 1) {
            System.out.println("Draw");
        } else {
            return false;   // Game continue.
        }
        return true;
    }

    private static boolean isTicTacToeWinner(String ch) {

        // If any row contain same symbol.
        for (int r = 0; r < fieldSize; r++) {
            int c = 0;
            if (gameField[r][c].equals(ch) && gameField[r][c + 1].equals(ch) && gameField[r][c + 2].equals(ch)) {
                return true;
            }
        }

        // If any column contain same symbol.
        for (int c = 0; c < fieldSize; c++) {
            int r = 0;
            if (gameField[r][c].equals(ch) && gameField[r + 1][c].equals(ch) && gameField[r + 2][c].equals(ch)) {
                return true;
            }
        }

        // If either of the diagonal contain same symbol.
        // False if none of the winning case in grid.
        int r = 0;
        int c = 0;
        if (gameField[r][c].equals(ch) && gameField[r + 1][c + 1].equals(ch) && gameField[r + 2][c + 2].equals(ch)) {
            return true;
        } else return gameField[r][c + 2].equals(ch) && gameField[r + 1][c + 1].equals(ch) && gameField[r + 2][c].equals(ch);
    }

    private static int remainingSpace() {
        int count = 0;
        for (int r = 0; r < fieldSize; r++) {
            for (int c = 0; c < fieldSize; c++) {
                if (gameField[r][c].equals(" ")) {
                    count++;
                }
            }
        }
        return count;
    }
}
