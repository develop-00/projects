package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static final Scanner readIp = new Scanner(System.in);
    public static final Map<String, String> cards = new LinkedHashMap<>();

    public static final Map<String, Integer> wrongAnswerStats = new LinkedHashMap<>();

    public static final ArrayList<String> log = new ArrayList<>();

    public static boolean exportAtExit = false;
    public static String exitFileName = "";

    public static void main(String[] args) {

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-import" -> importCards(args[i + 1]);
                    case "-export" -> {
                        exportAtExit = true;
                        exitFileName = args[i + 1];
                    }
                }}
        }

        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            log.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String action = readIp.nextLine();
            log.add(action);
            if (action.equals("exit")) {
                System.out.println("Bye bye!");
                log.add("Bye bye!");
                exportCards(exitFileName);
                break;
            }

            switch (action) {
                case "add" -> addCard();
                case "remove" -> removeCard();
                case "import" -> {
                    System.out.println("File name:");
                    log.add("File name:");
                    String fileName = readIp.nextLine();
                    log.add(fileName);importCards(fileName);
                }
                case "export" -> {
                    System.out.println("File name:");
                    log.add("File name:");
                    String fileName = readIp.nextLine();
                    log.add(fileName);
                    exportCards(fileName);
                }
                case "ask" -> askQuestions();
                case "log" -> logData();
                case "hardest card" -> printHardestCard();
                case "reset stats" -> resetStats();
            }
            System.out.println();
            log.add("\n");
        }
    }

    private static void resetStats() {
        wrongAnswerStats.clear();
        System.out.println("Card statistics have been reset.");
        log.add("Card statistics have been reset.");
    }

    private static void printHardestCard() {
        int maxWrongAnswerCount = 0;

        for (String term : wrongAnswerStats.keySet()) {
            if (wrongAnswerStats.get(term) > maxWrongAnswerCount) {
                maxWrongAnswerCount = wrongAnswerStats.get(term);
            }
        }

        ArrayList<String> wrongTerms = new ArrayList<>();
        for (String term : wrongAnswerStats.keySet()) {
            if (wrongAnswerStats.get(term) == maxWrongAnswerCount) {
                wrongTerms.add(term);
            }
        }

        if (wrongTerms.size() == 0) {
            System.out.println("There are no cards with errors.");
            log.add("There are no cards with errors.");
        } else if (wrongTerms.size() == 1) {
            System.out.printf("The hardest card is \"%s\". You have %d errors answering it.\n", wrongTerms.get(0), maxWrongAnswerCount);
            log.add("The hardest card is \"%s\". You have %d errors answering it.\n".formatted(wrongTerms.get(0), maxWrongAnswerCount));
        } else {
            StringBuilder response = new StringBuilder("The hardest cards are ");

            for (String wrongTerm : wrongTerms) {
                response.append("\"%s\", ".formatted(wrongTerm));
            }
            response.replace(response.length() - 2 , response.length(), "");
            response.append(". You have %d errors answering them.\n".formatted(maxWrongAnswerCount));
            System.out.println(response);
            log.add(response.toString());
        }
    }

    private static void logData() {
        System.out.println("File name:");
        log.add("File name:");
        String fileName = readIp.nextLine();
        log.add(fileName);

        File file = new File(fileName);

        try (PrintWriter writer = new PrintWriter(file)) {
            System.out.println("The log has been saved.");
            log.add("The log has been saved.");

            for (String line : log) {
                writer.println(line);
            }

        } catch (IOException e) {
            System.out.println("Can't open file");
            log.add("Can't open file");
        }

    }

    private static void askQuestions() {
        System.out.println("How many times to ask?");
        log.add("How many times to ask?");
        int numberOfQuestions = Integer.parseInt(readIp.nextLine());
        log.add(numberOfQuestions + "");

        List<String> terms = cards.keySet().stream().toList();
        for (int i = 0; i < numberOfQuestions; i++) {
            String term = terms.get(i);
            System.out.printf("Print the definition of \"%s\":\n", term);
            log.add("Print the definition of \"%s\":\n".formatted(term));
            String answer = readIp.nextLine();
            log.add(answer);

            String actualAnswer = cards.getOrDefault(terms.get(i), "");
            if (actualAnswer.equals(answer)) {
                System.out.println("Correct!");
                log.add("Correct!");
            } else {
                wrongAnswerStats.put(term, wrongAnswerStats.getOrDefault(term, 0) + 1);
                String actualTerm = checkForDefinition(answer);
                if (actualTerm.equals("")) {
                    System.out.printf("Wrong. The right answer is \"%s\".\n", actualAnswer);
                    log.add("Wrong. The right answer is \"%s\".\n".formatted(actualAnswer));
                } else {
                    System.out.printf("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".\n", actualAnswer, actualTerm);
                    log.add("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".\n".formatted(actualAnswer, actualTerm));
                }
            }
        }

    }

    private static String checkForDefinition(String definition) {
        for (String term : cards.keySet()) {
            if (cards.getOrDefault(term, "").equals(definition)) {
                return term;
            }
        }
        return "";
    }

    private static void exportCards(String fileName) {

        File file = new File(fileName);

        try (PrintWriter writer = new PrintWriter(file)) {
            // save cards to file.
            for (String term : cards.keySet()) {
                writer.println(term.concat(" : ").concat(cards.get(term).concat(", " + wrongAnswerStats.getOrDefault(term, 0))));

            }

            System.out.printf("%d cards have been saved.\n", cards.size());
            log.add("%d cards have been saved.\n".formatted(cards.size()));

        } catch (IOException e) {
            System.out.println("Can't open file.");
            log.add("Can't open file.");
        }
    }

    private static void importCards(String fileName) {

        File file = new File(fileName);
        try (Scanner readFile = new Scanner(file)) {
            int numberOfCards = 0;

            // load cards from file.
            while (readFile.hasNextLine()) {
                String[] data = readFile.nextLine().split("[:,]");
                // if term already in card, update definition otherwise add card.
                cards.put(data[0].trim(), data[1].trim());
                wrongAnswerStats.put(data[0].trim(), Integer.parseInt(data[2].trim()));
                numberOfCards++;
            }

            System.out.printf("%d cards have been loaded.\n", numberOfCards);
            log.add("%d cards have been loaded.\n".formatted(numberOfCards));

        }  catch (FileNotFoundException e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }

    }

    private static void removeCard() {
        System.out.println("Which card?");
        log.add("Which card?");
        String term = readIp.nextLine();
        log.add(term);

        String definition = cards.getOrDefault(term, "");

        if ("".equals(definition)) {
            System.out.printf("Can't remove \"%s\": there is no such card.\n", term);
            log.add("Can't remove \"%s\": there is no such card.\n".formatted(term));
        } else {
            cards.remove(term);
            System.out.println("The card has been removed.");
            log.add("The card has been removed.");
        }
    }

    private static void addCard() {
        System.out.println("The card:");
        log.add("The card:");
        String term = readIp.nextLine();
        log.add(term);

        if (cards.containsKey(term)) {
            System.out.printf("The card \"%s\" already exists.\n", term);
            log.add("The card \"%s\" already exists.\n".formatted(term));
        } else {
            System.out.println("The definition of the card:");
            log.add("The definition of the card:");
            String definition = readIp.nextLine();
            log.add(definition);

            if (cards.containsValue(definition)) {
                System.out.printf("The definition \"%s\" already exists.\n", definition);
                log.add("The definition \"%s\" already exists.\n".formatted(definition));
            } else {
                cards.put(term, definition);
                System.out.printf("The pair (\"%s\":\"%s\") has been added.\n", term, definition);
                log.add("The pair (\"%s\":\"%s\") has been added.\n".formatted(term, definition));
            }
        }
    }
}
