package com.example.engg1420project;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void title(String text) {
        line();
        System.out.println(text.toUpperCase());
        line();
    }

    public void section(String text) {
        System.out.println();
        System.out.println("-- " + text + " --");
    }

    public void line() {
        System.out.println("========================================");
    }

    public void message(String text) {
        System.out.println(text);
    }

    public int choose(String prompt, List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        return readInt(prompt, 1, options.size());
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + " ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Please enter a number.");
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= min && choice <= max) {
                return choice;
            }

            System.out.println("Please choose a number from " + min + " to " + max + ".");
        }
    }

    public String readText(String prompt) {
        System.out.print(prompt + " ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? "Player" : input;
    }
}
