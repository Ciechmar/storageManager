package com.sda.javagda40.ciechmar.storageManager;

import com.sda.javagda40.ciechmar.storageManager.database.EntityDao;
import com.sda.javagda40.ciechmar.storageManager.database.HibernateUtil;
import com.sda.javagda40.ciechmar.storageManager.model.AppUser;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("Initial version.");
        HibernateUtil.getOurSessionFactory();

        Scanner scanner = new Scanner(System.in);

        String command;
        do {
            System.out.println("Wprowadz komende: ");
            command = scanner.nextLine();
            String[] words = command.split(" ");
            if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("list")) {
                handleListUser(words);
            } else if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAdduser(words);
            }

        } while (!command.equalsIgnoreCase("end"));

    }

    private static void handleAdduser(String[] words) {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        AppUser appUser = new AppUser();
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        System.out.println("Podaj dane nowego użytkownika:");
        System.out.println("Imie:");
        appUser.setFirstName(answer);
        System.out.println("Nazwisko:");
        appUser.setLastName(answer);
        System.out.println("Numer telefonu:");
        appUser.setPhoneNumber(answer);
        System.out.println("Adres kontaktowy email:");
        appUser.setEmail(answer);
        System.out.println("Czy jest to klient prywatny/ indywidualny? (T/N)");
        if (answer.equalsIgnoreCase("t")) {
            appUser.setCompany(true);
        } else appUser.setCompany(false);
    }

    private static void handleListUser(String[] words) {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao.findAll(AppUser.class)
                .stream()
                .forEach((System.out::println));
    }
}
