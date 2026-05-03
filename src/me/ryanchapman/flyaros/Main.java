package me.ryanchapman.flyaros;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        System.out.print("> ");
        @SuppressWarnings("resource")
        String prompt = new Scanner(System.in).nextLine();
        switch (prompt.toLowerCase()) {
            case "hello" -> System.out.println("Hello, User!");
            default -> System.out.println("I don't understand.");
        }
    }
}
