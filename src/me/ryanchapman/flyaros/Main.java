package me.ryanchapman.flyaros;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.print("> ");
            @SuppressWarnings("resource")
            String prompt = new Scanner(System.in).nextLine();
            switch (normalize(prompt)) {
                case "hello" -> System.out.println("Hello, User!");
                case "how are you" -> System.out.println("I am doing good.");
                case "what is your name" -> System.out.println("My name is Flyaros.");
                case "goodbye" -> {
                    running = false;
                    System.out.println("Goodbye, User!");
                }
                default -> System.out.println("I don't understand.");
            }
        }
    }

    private static final String normalize(final String x) {
        final StringBuilder sb = new StringBuilder();
        boolean word = false;
        for (char c : x.toCharArray()) {
            if (word && Character.isWhitespace(c)) {
                word = false;
                sb.append(' ');
            }
            else if (Character.isLetterOrDigit(c)) {
                word = true;
                sb.append(c);
            }
        }
        if (sb.charAt(sb.length()-1) == ' ') {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString().toLowerCase();
    }
}
