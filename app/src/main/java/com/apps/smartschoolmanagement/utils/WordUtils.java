package com.apps.smartschoolmanagement.utils;

public class WordUtils {
    public static String capitalize(String pWord) {
        StringBuilder sb = new StringBuilder();
        String[] words = pWord.replaceAll("_", " ").split("\\s");
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            if (words[i].length() > 0) {
                sb.append(Character.toUpperCase(words[i].charAt(0)));
                if (words[i].length() > 1) {
                    sb.append(words[i].substring(1));
                }
            }
        }
        return sb.toString();
    }
}
