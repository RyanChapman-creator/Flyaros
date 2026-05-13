package me.ryanchapman.flyaros;

import java.io.File;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        final Scanner scanner = new Scanner(System.in);
        final BotModelDAO dao = new BotModelDAO();
        final BotModel botModel;
        if (args.length > 0) {
            botModel = dao.load(new File(args[0]));
            System.out.printf("Loaded chatbot state from %s\n", args[0]);
        }
        else {
            System.out.print("Do you want to load a chatbot? [Y]es [N]o\n> ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
                System.out.print("Enter the file you want to load from.\n> ");
                answer = scanner.nextLine();
                botModel = dao.load(new File(answer));
                System.out.printf("Loaded chatbot state from %s\n", answer);
            }
            else {
                botModel = new BotModel("flyaros");
                System.out.println("Loaded default chatbot state");
            }
                
        }
        boolean running = true;
        while (running) {
            System.out.print("> ");
            String prompt = scanner.nextLine();
            String normalized = normalize(prompt);
            switch (normalized) {
                case "hello" -> System.out.println("Hello, User!");
                case "how are you" -> System.out.println("I am doing good.");
                case "what is your name" ->
                    System.out.printf("My name is %s.\n", capitalize(botModel.getName(), true));
                case "goodbye" -> {
                    running = false;
                    System.out.println("Goodbye, User!");
                }
                default -> {
                    if (normalized.startsWith("your name is now ")) {
                        botModel.setName(normalized.substring("your name is now ".length()));
                        System.out.println("I accept this new name.");
                    }
                    else if (normalized.startsWith("your name is ")) {
                        botModel.setName(normalized.substring("your name is ".length()));
                        System.out.println("I accept this new name.");
                    }
                    else System.out.println("I don't understand.");
                }
            }
        }
        System.out.print("Do you want to save the state of the chatbot? [Y]es [N]o\n> ");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
            System.out.print("Enter the file you want to save to.\n> ");
            answer = scanner.nextLine();
            dao.save(new File(answer), botModel);
            System.out.printf("Saved chatbot state to %s\n", answer);
        } else {
            System.out.printf("Discarded chatbot state\n");
        }
    }

    static final String normalize(final String x) {
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

    private static final String capitalize(final String x, final boolean allWords) {
        if (x.isEmpty()) return x;
        else if (!allWords)
            return Character.toUpperCase(x.charAt(0)) + x.substring(1).toLowerCase();
        else {
            StringBuilder sb = new StringBuilder(x);
            for (int i = 1; i < sb.length(); i++) {
                if (sb.charAt(i-1) == ' ')
                    sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
            }
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        }
    }
}
