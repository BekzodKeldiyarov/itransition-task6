package com.example.demo.helper;
import java.util.Random;

public class MutilateString {
    public static String mutilateWord(String word) {
        Random random = new Random(10);
        int option = random.nextInt(3);
        switch (option) {
            case 0: {
                word = removeRandomLetter(word, random);
                break;
            }
            case 1: {
                word = insertRandomLetter(word, random);
                break;
            }
            default: {
                word = changeRandomLetter(word, random);
                break;
            }
        }
        return word;
    }

    private static String insertRandomLetter(String word, Random random) {
        Random randomForCharacters = new Random(word.hashCode());
        String output = "";
        int position;
        if (word.length() == 0) {
            position = 0;
        } else {
            position = random.nextInt(word.length());
        }
        char characterToAdd = (char) (randomForCharacters.nextInt(26) + 'a');
        output = word.substring(0, position) + characterToAdd + word.substring(position);
        return output;
    }

    private static String changeRandomLetter(String word, Random random) {
        String output = "";
        int position = random.nextInt(word.length());
        if (!Character.isWhitespace(word.charAt(position))) {
            if (position + 1 != word.length() && !Character.isWhitespace(word.charAt(position + 1))) {
                output = word.substring(0, position) + word.charAt(position + 1) + word.charAt(position) + word.substring(position + 2);
            } else if (!Character.isWhitespace(word.charAt(position - 1))) {
                output = word.substring(0, position - 1) + word.charAt(position) + word.charAt(position - 1) + word.substring(position + 1);
            } else {
                char characterToAdd = (char) (random.nextInt(26) + 'a');
                output = word.substring(0, position) + characterToAdd + word.substring(position + 1);
            }
        } else {
            char characterToAdd = (char) (random.nextInt(26) + 'a');
            output = word.substring(0, position) + characterToAdd + word.substring(position);
        }
        return output;
    }

    private static String removeRandomLetter(String word, Random random) {
        if (word.length() == 0) {
            return insertRandomLetter(word, random);
        }
        String output = "";
        int position = random.nextInt(word.length());
        output = word.substring(0, position) + word.substring(position + 1);
        return output;
    }
}
