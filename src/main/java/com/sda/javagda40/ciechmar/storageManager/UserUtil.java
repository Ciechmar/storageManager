package com.sda.javagda40.ciechmar.storageManager;

import com.sda.javagda40.ciechmar.storageManager.database.EntityDao;
import com.sda.javagda40.ciechmar.storageManager.database.UserDao;
import com.sda.javagda40.ciechmar.storageManager.model.Address;
import com.sda.javagda40.ciechmar.storageManager.model.AppUser;
import com.sda.javagda40.ciechmar.storageManager.model.CompanyData;

import java.util.*;

public class UserUtil {
    private EntityDao<CompanyData> companyentityDao = new EntityDao<>();
    private EntityDao<Address> addressEntityDao = new EntityDao<>();
    private EntityDao<AppUser> userEntityDao = new EntityDao<>();
    private CompanyData companyData = new CompanyData();
    private Scanner scanner = new Scanner(System.in);
    private AppUser appUser = new AppUser();
    private Address address = new Address();
    private UserDao userDao = new UserDao();


    protected void handleFindUser() {
        System.out.println("Szukaj [nazwisko/telefon/id]]");
        System.out.println(userDao.findByNameOrPhoneOrID(scanner.nextLine()).toString());
    }
    protected void handleDeleteUser() { // Dopisać usunięcie Jego adresu ,jezeli jest jego jedynym właścicielme
        System.out.println("Czy znasz ID użytkownika, którego chcesz usunąć z bazy danych? T/N");
        if (scanner.nextLine().equalsIgnoreCase("T")) {
            System.out.println("Podaj ID użytkownia:");
            Long id = scanner.nextLong();
            userEntityDao.findById(AppUser.class, id).ifPresent(userEntityDao::delete);
        } else {
            handleFindUser();
            System.out.println("Podaj ID użytkownia:");
            Long id = scanner.nextLong();
            userEntityDao.findById(AppUser.class, id).ifPresent(userEntityDao::delete);
        }
        System.out.println("Usunięto użytkownika z bazy");
    }
    protected void handleAdduser() {
        System.out.println("Podaj dane nowego użytkownika:");
        System.out.println("Imie:");
        appUser.setFirstName(scanner.nextLine());
        System.out.println("Nazwisko:");
        appUser.setLastName(scanner.nextLine());
        System.out.println("Numer telefonu:");
        appUser.setPhoneNumber(scanner.nextLine());
        System.out.println("Adres kontaktowy email:");
        appUser.setEmail(scanner.nextLine());
        userEntityDao.saveOrUpdate(appUser);
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
        userEntityDao.saveOrUpdate(appUser);
    }
    protected void handleListUser() {
        userEntityDao.findAll(AppUser.class)
                .forEach((System.out::println));
    }

    private CompanyData handleCompanyData(AppUser appUser) {
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
        companyentityDao.saveOrUpdate(companyData);
        return companyData;
    }

    private Address handleAddAdress(AppUser appUser) {
        Address address = addAddress();
        Set<AppUser> users = new HashSet<>();
        users.add(appUser);
        address.setUssers(users);
        addressEntityDao.saveOrUpdate(address);
        return address;
    }

    private Address handleAddAdress(CompanyData company) {
        Address address = addAddress();
        address.setCompany(company);
        addressEntityDao.saveOrUpdate(address);
        return address;
    }

    private Address addAddress() {
        System.out.println("Podaj dane adresowe użytkownika:");
        System.out.println("Miasto");
        address.setCity(scanner.nextLine());
        System.out.println("Kod pocztowy");
        address.setPostCode(scanner.nextLine());
        System.out.println("ulica:");
        address.setStreet(scanner.nextLine());
        System.out.println("Numer budynku:");
        address.setBuildingNumber(scanner.nextLine());
        addressEntityDao.saveOrUpdate(address);
        return address;
    }


}
