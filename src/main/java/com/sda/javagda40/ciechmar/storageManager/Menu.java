package com.sda.javagda40.ciechmar.storageManager;

import java.util.Scanner;

public class Menu {

    protected static void menu() {

        System.out.println("\n\n\n\n*************************************************");
        System.out.println("  ***************STORAGE-MANAGER***************");
        System.out.println("*************************************************");
        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            System.out.println("\n  Wprowadz komende (lista komend wpisz: help): ");
            command = scanner.nextLine();
            String[] words = command.split(" ");
            if (words[0].equalsIgnoreCase("help")) {
                UserHelp.list();
            } else if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("list")) {
                UserUtil.handleListUser();
            } else if (words[0].equalsIgnoreCase("show") &&
                    words[1].equalsIgnoreCase("reservations")) {
                StorageUtils.showReservations();
            } else if (words[0].equalsIgnoreCase("storage") &&
                    words[1].equalsIgnoreCase("list")) {
                StorageUtils.handleListStorage();
            } else if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("add")) {
                UserUtil.handleAdduser();
            } else if (words[0].equalsIgnoreCase("storage") &&
                    words[1].equalsIgnoreCase("add")) {
                StorageUtils.handleAddStorage();
            } else if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("delete")) {
                UserUtil.handleDeleteUser();
            } else if (words[0].equalsIgnoreCase("storage") &&
                    words[1].equalsIgnoreCase("delete")) {
                StorageUtils.handleDeleteStorage();
            } else if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("find")) {
                UserUtil.handleFindUser();
            } else if (words[0].equalsIgnoreCase("storage") &&
                    words[1].equalsIgnoreCase("find")) {
                StorageUtils.handleFindStorage();
            } else if (words[0].equalsIgnoreCase("storage") &&
                    words[1].equalsIgnoreCase("rent")) {
                StorageUtils.handleRentStorage();
            }
        } while (!command.equalsIgnoreCase("end"));
    }
}
