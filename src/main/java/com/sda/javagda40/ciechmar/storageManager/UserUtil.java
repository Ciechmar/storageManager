package com.sda.javagda40.ciechmar.storageManager;

import com.sda.javagda40.ciechmar.storageManager.database.EntityDao;
import com.sda.javagda40.ciechmar.storageManager.model.Address;
import com.sda.javagda40.ciechmar.storageManager.model.AppUser;
import com.sda.javagda40.ciechmar.storageManager.model.CompanyData;

import java.util.*;

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
        appUserEntityDao.saveOrUpdate(appUser);
        System.out.println("Wprowadzić nowy adres? T/N");
        if (scanner.nextLine().equalsIgnoreCase("t")) { //gdy nowy musimy utowrzyć set i wprowadzić dane
            Address address = handleAddAdress(appUser);
            appUser.setAddress(address);
        } else { // gdy adres istnieje to szukamy po id.
            EntityDao<Address> addressEntityDao = new EntityDao<>();
            System.out.println("Podaj id adresu:");
            Optional<Address> addressById = addressEntityDao.findById(Address.class, scanner.nextLong());
            scanner.nextLine();
            appUser.setAddress(addressById.get());
        }
        System.out.println("Czy jest to klient firma? (T/N)");
        if (scanner.nextLine().equalsIgnoreCase("t")) {
            appUser.setCompany(true);
            CompanyData companyData = handleCompanyData(appUser);
            Set<CompanyData> companylist = new HashSet<>();
            companylist.add(companyData);
            appUser.setCompanies(companylist);

        } else {
            appUser.setCompany(false);
        }
        appUserEntityDao.saveOrUpdate(appUser);
    }

    protected static void handleListUser() {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao.findAll(AppUser.class)
//                .stream()
                .forEach((System.out::println));
    }

    private static CompanyData handleCompanyData(AppUser appUser) {
        Scanner scanner = new Scanner(System.in);
        CompanyData companyData = new CompanyData();
        EntityDao<CompanyData> entityDao = new EntityDao<>();
        System.out.println("Podaj dane firmy:");
        System.out.println("Nazwa firmy");
        companyData.setCompanyName(scanner.nextLine());
        System.out.println("NIP:");
        companyData.setNIP(scanner.nextLine());
        System.out.println("Czy adres jest zgodny z adresem użytkownika? T/N");
        if (scanner.nextLine().equalsIgnoreCase("t")) {
            companyData.setAddress(appUser.getAddress());
        } else companyData.setAddress(handleAddAdress(companyData));
        companyData.setUser(appUser);
        entityDao.saveOrUpdate(companyData);
        return companyData;
    }

    private static Address handleAddAdress(AppUser appUser) {
        Address address = addAddress();
        EntityDao<Address> entityDao = new EntityDao<>();
        Set<AppUser> users = new HashSet<>();
        users.add(appUser);
        address.setUssers(users);
        entityDao.saveOrUpdate(address);
        return address;
    }

    private static Address handleAddAdress(CompanyData company) {
        Address address = addAddress();
        EntityDao<Address> entityDao = new EntityDao<>();
        address.setCompany(company);
        entityDao.saveOrUpdate(address);
        return address;
    }

    private static Address addAddress() {
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
