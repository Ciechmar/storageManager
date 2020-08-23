package com.sda.javagda40.ciechmar.storageManager.database;

import com.sda.javagda40.ciechmar.storageManager.model.AppUser;
import com.sda.javagda40.ciechmar.storageManager.model.Rent;
import com.sda.javagda40.ciechmar.storageManager.model.Storage;
import com.sda.javagda40.ciechmar.storageManager.model.StorageStatus;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Set;

import static java.time.LocalDate.now;

public class RentDao {
    private final EntityDao<Storage> storageEntityDao = new EntityDao<>();
    private final EntityDao<AppUser> userEntityDao = new EntityDao<>();
    private final EntityDao<Rent> rentEntityDao = new EntityDao<>();
    private final UserDao userDao = new UserDao();
    private final Scanner scanner = new Scanner(System.in);

    public void handleAddRent() {
        AppUser user;
        Storage storage;
        String endanswer;
        LocalDate from;
        LocalDate to;
        float prize;

        //ToDO: pętla by wczytał inne ID klienta/magazynu
        System.out.println("Czy nzasz id klienta? [t/n]");
        if (scanner.nextLine().equalsIgnoreCase("n")) {
            System.out.println("Podaj nazwisko/telefon/email (lub fragment frazy)");
            userDao.findByNameOrPhoneOrEmail(scanner.nextLine()).forEach(System.out::println);
        }
        do {
            System.out.println("Podaj id klienta:");
            Long userId = Long.parseLong(scanner.nextLine());
            user = userEntityDao.findById(AppUser.class, userId).get();
            System.out.println(user);
            System.out.println("Podaj Id wynajmowanego magazynu:");
            Long storageId = Long.parseLong(scanner.nextLine());
            storage = storageEntityDao.findById(Storage.class, storageId).get();
            System.out.println(storage);
            //ToDo:Sprawdzenie czy zajęty w tym terminie
            System.out.println("Od kiedy wynajem? [YYYYY-MM-DD lub dzis] ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("dzis")) {
                from = now();
            } else {
                from = LocalDate.parse(answer);
            }
            System.out.println("Na ile miesięcy wynajem?");
            String answertime = scanner.nextLine();
            to = from.plusMonths(Long.parseLong(answertime));
            float standardPrize = storage.getStandardPrize() * Float.parseFloat(answertime);
            System.out.println("Czy klientowi przysłguje zniżka? T/N");
            if (scanner.nextLine().equalsIgnoreCase("n")) {
                prize = standardPrize;
            } else {
                System.out.println("Ile zniżki dodać?");
                String lowPrize = scanner.nextLine();
                prize = standardPrize - Float.parseFloat(lowPrize);
            }
            System.out.println("Wynajem dla: ");
            System.out.println(user);
            System.out.printf("Wynajem na %s miesięcy na naspujący magazyn:\n", answertime);
            System.out.println(storage);
            System.out.printf("Cena za wynajem magazynu to: %.2f zł", prize); //jak zmusić,by nie było możliwości wpisania 3 miejsc po przecinku?! -nie gubiło tysięcznych złotówki
            System.out.println("Wszystko się zgadza? [t/n]");
            endanswer = scanner.nextLine();
        } while (!endanswer.equalsIgnoreCase("t"));
        Rent rent = new Rent(from, to, now(), prize);
        rent.setUserRent(user);
        rent.setStorage(storage);
        storage.setStatus(StorageStatus.RENT);
        storage.getStorageRentals().add(rent);
        Set<Rent> rentals = user.getRentals();
        rentals.add(rent);
        user.setRentals(rentals);
        storageEntityDao.saveOrUpdate(storage);
        userEntityDao.saveOrUpdate(user);
        rentEntityDao.saveOrUpdate(rent);
        System.out.println(rent);
    }

    public void handleListRent() {
        rentEntityDao.findAll(Rent.class).forEach(System.out::println);
    }
}
