package com.sda.javagda40.ciechmar.storageManager;

import com.sda.javagda40.ciechmar.storageManager.database.EntityDao;
import com.sda.javagda40.ciechmar.storageManager.model.Address;
import com.sda.javagda40.ciechmar.storageManager.model.AppUser;
import com.sda.javagda40.ciechmar.storageManager.model.CompanyData;

import java.util.List;
import java.util.Scanner;

public class UserUtil {

    protected static void handleFindUser() {
        Scanner scanner = new Scanner(System.in);
        EntityDao<AppUser> userEntityDao = new EntityDao<>();
        List<AppUser> usersList = userEntityDao.findAll(AppUser.class);
        System.out.println("Szukaj po: \n1.Nazwisko\n2.Telefon\n3.Id");
        String answer = scanner.nextLine();
        switch (answer.toLowerCase()) {
            case "1":
            case "nazwisko": {
                System.out.println("Podaj nazwisko:");
                String nameString = scanner.nextLine();
                for (AppUser user : usersList) {
                    if (user.getLastName().equalsIgnoreCase(nameString)) {
                        System.out.println(user);
//                        return user;
                    }
                }
                break;
            }
            case "2":
            case "telefon": {
                System.out.println("Podaj numer telefonu:");
                String telNumber = scanner.nextLine();
                for (AppUser user : usersList) {
                    if (user.getPhoneNumber().equals(telNumber)) {
                        System.out.println(user);
//                        return user;
                    }
                }
                break;
            }

            case "3":
            case "id": {
                System.out.println("Podaj id:");
                Long id = scanner.nextLong();
                userEntityDao.findById(AppUser.class, id).ifPresent(System.out::println);
//                return userEntityDao.findById(AppUser.class, id).get();
                break;
            }
        }

    }

    protected static void handleDeleteUser() {
        System.out.println("Czy znasz ID użytkownika, którego chcesz usunąć z bazy danych? T/N");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equalsIgnoreCase("T")) {
            System.out.println("Podaj ID użytkownia:");
            Long id = scanner.nextLong();
            EntityDao<AppUser> userEntityDao = new EntityDao<>();
            userEntityDao.findById(AppUser.class, id).ifPresent(userEntityDao::delete);
        } else {
            handleFindUser();
            System.out.println("Podaj ID użytkownia:");
            Long id = scanner.nextLong();
            EntityDao<AppUser> userEntityDao = new EntityDao<>();
            userEntityDao.findById(AppUser.class, id).ifPresent(userEntityDao::delete);
        }
    }

    protected static void handleAdduser() {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        AppUser appUser = new AppUser();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj dane nowego użytkownika:");
        System.out.println("Imie:");
        appUser.setFirstName(scanner.nextLine());
        System.out.println("Nazwisko:");
        appUser.setLastName(scanner.nextLine());
        System.out.println("Numer telefonu:");
        appUser.setPhoneNumber(scanner.nextLine());
        System.out.println("Adres kontaktowy email:");
        appUser.setEmail(scanner.nextLine());
        System.out.println("Czy jest to klient firma? (T/N)");
        if (scanner.nextLine().equalsIgnoreCase("t")) {
            appUser.setCompany(true);
            appUser.setAddress(handleAddAdress());
            appUser.getCompanies().add(handleCompanyData());
        } else {
            appUser.setCompany(false);
            appUser.setAddress(handleAddAdress());
        }
        appUserEntityDao.saveOrUpdate(appUser);
    }

    protected static void handleListUser() {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao.findAll(AppUser.class)
//                .stream()
                .forEach((System.out::println));
    }

    private static CompanyData handleCompanyData() {
        Scanner scanner = new Scanner(System.in);
        CompanyData companyData = new CompanyData();
        EntityDao<CompanyData> entityDao = new EntityDao<>();
        System.out.println("Podaj dane firmy:");
        System.out.println("Nazwa firmy");
        companyData.setCompanyName(scanner.nextLine());
        System.out.println("NIP:");
        companyData.setNIP(scanner.nextLine());
        entityDao.saveOrUpdate(companyData);
        return companyData;
    }

    private static Address handleAddAdress() {
        Scanner scanner = new Scanner(System.in);
        Address address = new Address();
        EntityDao<Address> entityDao = new EntityDao<>();
        System.out.println("Podaj dane adresowe użytkownika:");
        System.out.println("Miasto");
        address.setCity(scanner.nextLine());
        System.out.println("Kod pocztowy");
        address.setPostCode(scanner.nextLine());
        System.out.println("ulica:");
        address.setStreet(scanner.nextLine());
        System.out.println("Numer budynku:");
        address.setBuildingNumber(scanner.nextLine());
        entityDao.saveOrUpdate(address);
        return address;

    }


}
