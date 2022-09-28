package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static final String sentenceTerminator = "[!?.]\\s*";
    public static final String wordTerminator = ",?\\s+";
    public static final String characterSeparator = "\\s*";
    public static final Scanner readIp = new Scanner(System.in);
    public static final ArrayList<String> words = new ArrayList<>();

    public static void main(String[] args) {

        String fileName = args[0];

        File file = new File(fileName);
        StringBuilder input = new StringBuilder();
        String[] sentences;
        int[] syllables;
        int totalSentences;
        int totalCharacters;
        int totalWords;
        int totalPolysyllables;
        int totalSyllables;
        double readabilityScore;
        String readabilityType;
        String ageGroup;
        int upperAge;
        int totalAge = 0;

        //Read input from file
        try (Scanner readFile = new Scanner(file)) {
            while (readFile.hasNextLine()) {
                input.append(readFile.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }

        //separate input data into sentences.
        sentences = input.toString().split(sentenceTerminator);

        //separate sentences into words
        for (String sentence : sentences) {
            words.addAll(Arrays.asList(sentence.split(wordTerminator)));
        }

        totalSentences = sentences.length;
        totalWords = words.size();
        totalCharacters = input.toString().replaceAll("\\s+", "").split(characterSeparator).length;
        syllables = countSyllables(words);
        totalSyllables = syllables[0];
        totalPolysyllables = syllables[1];

        System.out.println("Words: " + totalWords);
        System.out.println("Sentences: " + totalSentences);
        System.out.println("Characters: " + totalCharacters);
        System.out.println("Syllables: " + totalSyllables);
        System.out.println("Polysyllables: " + totalPolysyllables);

        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        readabilityType = readIp.nextLine();

        switch (readabilityType) {
            case "ARI" -> readabilityScore = automatedReadabilityIndex(totalSentences, totalWords, totalCharacters);
            case "FK" -> readabilityScore = fleschKincaidReadabilityIndex(totalSentences, totalWords, totalSyllables);
            case "SMOG" -> readabilityScore = smogReadabilityIndex(totalSentences, totalPolysyllables);
            case "CL" -> readabilityScore = colemanLiauReadabilityIndex(totalSentences, totalWords, totalCharacters);
            default -> {
                readabilityScore = automatedReadabilityIndex(totalSentences, totalWords, totalCharacters);
                ageGroup = findAgeGroup((int) Math.round(readabilityScore));
                System.out.println(ageGroup);
                upperAge = Integer.parseInt(ageGroup.substring(ageGroup.indexOf("-") + 1));
                totalAge += upperAge;
                System.out.printf("Automated Readability Index: %.2f (about %s-year-olds).\n", readabilityScore, upperAge);

                readabilityScore = fleschKincaidReadabilityIndex(totalSentences, totalWords, totalSyllables);
                ageGroup = findAgeGroup((int) Math.round(readabilityScore));
                upperAge = Integer.parseInt(ageGroup.substring(ageGroup.indexOf("-") + 1));
                totalAge += upperAge;
                System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-year-olds).\n", readabilityScore, ageGroup.substring(ageGroup.indexOf("-") + 1));

                readabilityScore = smogReadabilityIndex(totalSentences, totalPolysyllables);
                ageGroup = findAgeGroup((int) Math.round(readabilityScore));
                upperAge = Integer.parseInt(ageGroup.substring(ageGroup.indexOf("-") + 1));
                totalAge += upperAge;
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).\n", readabilityScore, ageGroup.substring(ageGroup.indexOf("-") + 1));

                readabilityScore = colemanLiauReadabilityIndex(totalSentences, totalWords, totalCharacters);
                ageGroup = findAgeGroup((int) Math.ceil(readabilityScore));
                upperAge = Integer.parseInt(ageGroup.substring(ageGroup.indexOf("-") + 1));
                totalAge += upperAge;
                System.out.printf("Coleman–Liau index: %.2f (about %s-year-olds).\n", readabilityScore, ageGroup.substring(ageGroup.indexOf("-") + 1));

                double avgAge = (double) totalAge / 4;
                System.out.printf("This text should be understood in average by %.2f-year-olds.", avgAge);
            }
        }

        if (!readabilityType.equals("all")) {
            ageGroup = findAgeGroup((int) Math.round(readabilityScore));
            System.out.printf("The score is: %.2f\n", readabilityScore);
            System.out.printf("This text should be understood by %s year-olds.\n", ageGroup);
        }
    }

    private static int[] countSyllables(ArrayList<String> words) {
//        int num = 0;
//        int count = 0;
//        int num2 = 0;
//
//        for (String word : words) {
//            count = word.replaceAll("e\\b", "")
//                    .replaceAll("you", "a")
//                    .replaceAll("[aeiouy]{2}", "a")
//                    .replaceAll(" th "," a ")
//                    .replaceAll(",","")
//                    .replaceAll(" w "," a ")
//                    .replaceAll("[0-9]+", "a")
//                    .replaceAll("[^aeiouy]", "")
//                    .length();
//            if (count > 1) {
//                num2++;
//            }
//            num += count;
//            count = 0;
//
//        }
//        return new int[]{num, num2};
        String vowels = "aeiouy";
        int totalSyllables = 0;
        int totalPolysyllables = 0;
        int syllablesCount = 0;

        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                String ch = word.substring(i, i + 1);
                if (vowels.contains(ch)) {
                    
                    // if e/E is last character
                    if ("eE".contains(ch) && i == word.length() - 1) {
                        break;
                    } else if (i + 1 != word.length() && vowels.contains(word.substring(++i, i + 1))) {
                        //if two vowels are together
                        syllablesCount++;
                    } else {
                        syllablesCount++;
                    }
                }
            }



            if (syllablesCount == 0) {
                syllablesCount = 1;
            }

            totalSyllables += syllablesCount;

            if (syllablesCount > 2) {
                totalPolysyllables++;
            }

            syllablesCount = 0;
        }
        return new int[]{totalSyllables, totalPolysyllables};
    }

    private static String findAgeGroup(int readabilityScore) {
        String ageGroup;

        switch (readabilityScore) {
            case 1 -> ageGroup = "5-6";
            case 2 -> ageGroup = "6-7";
            case 3 -> ageGroup = "7-8";
            case 4 -> ageGroup = "8-9";
            case 5 -> ageGroup = "9-10";
            case 6 -> ageGroup = "10-11";
            case 7 -> ageGroup = "11-12";
            case 8 -> ageGroup = "12-13";
            case 9 -> ageGroup = "13-14";
            case 10 -> ageGroup = "14-15";
            case 11 -> ageGroup = "15-16";
            case 12 -> ageGroup = "16-17";
            case 13 -> ageGroup = "17-18";
            case 14 -> ageGroup = "18-22";
            default -> ageGroup = "22-100";
        }
        return ageGroup;
    }

    private static double fleschKincaidReadabilityIndex(int totalSentences, int totalWords, int totalSyllables) {
        //calculate readability score
        return 0.39 * totalWords / totalSentences + 11.8 * totalSyllables / totalWords - 15.59;
    }

    private static double smogReadabilityIndex(int totalSentences, int totalPolysyllables) {
        //calculate readability score
        return 1.043 * Math.sqrt((double) totalPolysyllables * 30 / totalSentences) + 3.1291;
    }

    private static double colemanLiauReadabilityIndex(int totalSentences, int totalWords, int totalCharacters) {
        double avgCharPerHundredWords = (double) totalCharacters / totalWords * 100;
        double avgSentencePerHundredWords = (double) totalSentences / totalWords * 100;
        //calculate readability score
        return 0.0588 * avgCharPerHundredWords - 0.296 * avgSentencePerHundredWords - 15.8;
    }

    private static double automatedReadabilityIndex(int totalSentences, int totalWords, int totalCharacters) {
        //calculate readability score
        return (4.71 * totalCharacters / totalWords) + (0.5 * totalWords / totalSentences) - 21.43;
    }
}
