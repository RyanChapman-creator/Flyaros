package me.ryanchapman.flyaros;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.print("> ");
            @SuppressWarnings("resource")
            String prompt = new Scanner(System.in).nextLine();
            switch (prompt.toLowerCase()) {
                case "hello" -> System.out.println("Hello, User!");
                case "how are you" -> System.out.println("I am doing good.");
                case "goodbye" -> {
                    running = false;
                    System.out.println("Goodbye, User!");
                }
                default -> System.out.println("I don't understand.");
            }
        }
    }
}
