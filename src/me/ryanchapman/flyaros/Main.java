package me.ryanchapman.flyaros;

import java.io.File;
import java.util.Scanner;

public final class Main {
    
    public static void main(final String[] args) {
        final BotModelDAO dao = new BotModelDAO();
        final BotModel botModel = load(dao, args);
        final BotLogic botLogic = new BotLogic(botModel);
        while (running) {
            System.out.print("> ");
            final String prompt = scanner.nextLine();
            final String response = botLogic.respond(prompt);
            if (!response.isBlank())
                System.out.println(response);
        }
        save(dao, botModel);
    }
    
    static boolean running = true;
    private static final Scanner scanner = new Scanner(System.in);
    private static File file = null;
    
    private static final BotModel load(final BotModelDAO dao, final String[] args) {
        if (args.length > 0) {
            file = new File(args[0]);
            final BotModel botModel = dao.load(file);
            System.out.printf("Loaded chatbot state from %s\n", args[0]);
            return botModel;
        }
        else {
            System.out.print("Do you want to load a chatbot? [Y]es [N]o\n> ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
                System.out.print("Enter the file you want to load from.\n> ");
                answer = scanner.nextLine();
                file = new File(answer);
                final BotModel botModel = dao.load(file);
                System.out.printf("Loaded chatbot state from %s\n", answer);
                return botModel;
            }
            else {
                final BotModel botModel = new BotModel("flyaros");
                System.out.println("Loaded default chatbot state");
                return botModel;
            }
        }
    }

    private static final void save(final BotModelDAO dao, final BotModel botModel) {
        System.out.print("Do you want to save the state of the chatbot? [Y]es [N]o\n> ");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
            if (file != null) {
                System.out.print("Do you want use the same file? [Y]es [N]o\n> ");
                answer = scanner.nextLine();
                if (!answer.equalsIgnoreCase("Y") && !answer.equalsIgnoreCase("YES")) {
                    file = null;
                }
            }
            if (file == null) {
                System.out.print("Enter the file you want to save to.\n> ");
                answer = scanner.nextLine();
                file = new File(answer);
            }
            dao.save(file, botModel);
            System.out.printf("Saved chatbot state to %s\n", answer);
        } else {
            System.out.printf("Discarded chatbot state\n");
        }
    }
}
