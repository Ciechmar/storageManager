package com.sda.javagda40.ciechmar.storageManager;

import com.sda.javagda40.ciechmar.storageManager.database.EntityDao;
import com.sda.javagda40.ciechmar.storageManager.database.StorageDao;
import com.sda.javagda40.ciechmar.storageManager.database.UserDao;
import com.sda.javagda40.ciechmar.storageManager.model.*;

import java.util.List;
import java.util.Scanner;

public class StorageUtils {
    private final EntityDao<Storage> storageEntityDao = new EntityDao<>();
    private final EntityDao<AppUser> userEntityDao = new EntityDao<>();
    private final EntityDao<Rent> rentEntityDao = new EntityDao<>();
    private final UserDao userDao = new UserDao();
    private final StorageDao storageDao = new StorageDao();
    private final Scanner scanner = new Scanner(System.in);


    protected void showReservations() {
//        List<Storage> storageList = storageEntityDao.findAll(Storage.class);
//        storageList.stream().filter(storage -> storage.getStatus().equals(StorageStatus.RENT))
//                .sorted(Comparator.comparing(Storage::getSize))
        storageDao.findAllRented()
                .forEach(System.out::println);
    }

    protected void handleFindStorage() {
        System.out.println("Szukaj po: \n1.Rozmiar\n2.Specyfikacja\n3.Numer drzwi\n4.Id");
        switch (scanner.nextLine()) {
            case "1":
            case "rozmiar": {
                System.out.println("Lista magazynów rozmiar: \n1.MINI (1-4m2)\n2.MIDI(6-9 m2)\n3.MAX(15+ m2)\n4.Konkretny rozmiar");
                String answer = scanner.nextLine();
                switch (answer) {
                    case "1":
                    case "mini": {
                        storageDao.findBySize(StorageSize.MINI1m2).forEach(System.out::println);
                        storageDao.findBySize(StorageSize.MINI2m2).forEach(System.out::println);
                        storageDao.findBySize(StorageSize.MINI3m2).forEach(System.out::println);
                        storageDao.findBySize(StorageSize.MINI4m2).forEach(System.out::println);
                        break;
                    }
                    case "2":
                    case "midi": {
                        storageDao.findBySize(StorageSize.MIDI6m2).forEach(System.out::println);
                        storageDao.findBySize(StorageSize.MIDI9m2).forEach(System.out::println);
                        break;
                    }
                    case "3":
                    case "max": {
                        storageDao.findBySize(StorageSize.MAX15m2).forEach(System.out::println);
                        storageDao.findBySize(StorageSize.MAX20m2).forEach(System.out::println);
                        break;
                    }
                    case "4": {
                        System.out.println("Podaj poszukiwany rozmiar:1/2/3/4/6/9/15/20");
                        findBySize(scanner.nextLine());
                        break;
                    }
                    default: {
                        System.out.println("Nie ma takiego rozmiaru");
                    }
                }
                break;
            }
            case "2":
            case "specyfikacja": {
                System.out.println("Lista magazynów typu: \n1.garaż\n2.do użytku sanepidowskiego");
                String answer = scanner.nextLine();
                if (answer.equals("1") || answer.equalsIgnoreCase("garaz") || answer.equalsIgnoreCase("garaż")) {
                    System.out.println("Magazyny garażowe:");
                    storageDao.findGarage().forEach(System.out::println);
                } else {
                    System.out.println("Magazyny do użytku sanepidowskiego:");
                    storageDao.findForSanepidUse().forEach(System.out::println);
                }
                break;
            }
            case "3":
            case "Numer drzwi": {
                System.out.println("Podaj numer drzwi");
                int doorNumber = Integer.parseInt(scanner.nextLine()); // TODO: nextInt zastąp na nextLine
                storageDao.findDoorNumber(doorNumber).forEach(System.out::println);
                break;
            }
            case "4":
            case "id": {
                System.out.println("Podaj id:");
                Long id = Long.parseLong(scanner.nextLine());
                storageEntityDao.findById(Storage.class, id).ifPresent(System.out::println);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + scanner.nextLine());
        }


    }

    protected void handleDeleteStorage() {
        System.out.println("Czy znasz ID magazynu, którego chcesz usunąć z bazy danych? T/N");
        if (scanner.nextLine().equalsIgnoreCase("T")) {
            System.out.println("Podaj ID magazynu:");
            Long id = scanner.nextLong();
            storageEntityDao.findById(Storage.class, id).ifPresent(storageEntityDao::delete);
        } else {
            handleFindStorage();
            System.out.println("Podaj ID użytkownia:");
            Long id = scanner.nextLong();
            storageEntityDao.findById(Storage.class, id).ifPresent(storageEntityDao::delete);
        }
        System.out.println("Usunięto magazyn o podanym ID");
    }

    protected void handleAddStorage() {
        Storage storage = new Storage();
        System.out.println("Podaj dane nowego magazynu:");
        do {
            storage.setDoorNumber(0);
            System.out.println("Podaj numer magazynu:");
            storage.setDoorNumber(scanner.nextInt());
            scanner.nextLine();
        } while (storage.getDoorNumber() == 0);
        do {
            System.out.println("Rozmiar: 1/2/3/4/6/9/15/20(w m2)"); //MINI2m2, MINI3m2, MINI4m2, MIDI6m2, MIDI9m3, MAX15m2, MAX20m2;
            System.out.println("Rozmiar 15m2 i 20m2 są zawsze na poziomie 0 (Garaże)"); //MINI2m2, MINI3m2, MINI4m2, MIDI6m2, MIDI9m3, MAX15m2, MAX20m2;
            switch (scanner.nextLine()) {
                case "1": {
                    storage.setSize(StorageSize.MINI1m2);
                    storage.setStandardPrize(69);
                    storage.setGarage(false);
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                case "2": {
                    storage.setSize(StorageSize.MINI2m2);
                    storage.setStandardPrize(92);
                    storage.setGarage(false);
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                case "3": {
                    storage.setSize(StorageSize.MINI3m2);
                    storage.setStandardPrize(113);
                    storage.setGarage(false);
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                case "4": {
                    storage.setSize(StorageSize.MINI4m2);
                    storage.setStandardPrize(123);
                    storage.setGarage(false);
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                case "6": {
                    storage.setSize(StorageSize.MIDI6m2);
                    storage.setStandardPrize(130);
                    storage.setGarage(false);
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                case "9": {
                    storage.setSize(StorageSize.MIDI9m2);
                    storage.setStandardPrize(190);
                    storage.setGarage(false);
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                case "15": {
                    storage.setSize(StorageSize.MAX15m2);
                    storage.setStandardPrize(200);
                    storage.setGarage(true);
                    storage.setFloor(Floor.ZERO);
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                case "20": {
                    storage.setSize(StorageSize.MAX20m2);
                    storage.setStandardPrize(250);
                    storage.setFloor(Floor.ZERO);
                    storage.setGarage(true);
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                default: {
                    System.out.println("Nie ma takiego rozmiaru magazynu");
                    storage.setSize(StorageSize.OTHER);
                }
            }
        } while (storage.getSize().equals(StorageSize.OTHER));

        System.out.println("Czy ma zezwolenia sanepidowskie? T/N");
        switch (scanner.nextLine().toLowerCase()) {
            case "t": {
                storage.setForSanepidUse(true);
                break;
            }
            case "n": {
                storage.setForSanepidUse(false);
                break;
            }
            default:
                System.out.println("Prosze wybrać T lub N");
        }
        System.out.println("Na jakim piętrze znajduje się magazyn? 0/1/2/3");
        switch (scanner.nextLine()) {
            case "0": {
                storage.setFloor(Floor.ZERO);
                break;
            }
            case "1": {
                storage.setFloor(Floor.FIRST);
                break;
            }
            case "2": {
                storage.setFloor(Floor.SECOND);
                break;
            }
            case "3": {
                storage.setFloor(Floor.THIRD);
                break;
            }
            default: {
                System.out.println("Nie ma takiego piętra");
            }
        }

        System.out.println("W którym sektorze się znajduje? RED, YELLOW, BLUE, GREEN, ORANGE");
        switch (scanner.nextLine().toLowerCase()) {
            case "red": {
                storage.setColor(StorageColor.RED);
                break;
            }
            case "yellow": {
                storage.setColor(StorageColor.YELLOW);
                break;
            }
            case "blue": {
                storage.setColor(StorageColor.BLUE);
                break;
            }
            case "orange": {
                storage.setColor(StorageColor.ORANGE);
                break;
            }
            case "green": {
                storage.setColor(StorageColor.GREEN);
                break;
            }
            default: {
                System.out.println("Nie ma takiego sektora");
            }
        }
        storageEntityDao.saveOrUpdate(storage);
    }

    protected void handleListStorage() {
        storageEntityDao.findAll(Storage.class)
                .forEach((System.out::println));
    }

    protected void showfreemagazynlistBySize() {
        storageDao.findAllFree()
                .forEach(System.out::println);
    }

    private void findBySize(String size) {
        List<Storage> storageList = storageEntityDao.findAll(Storage.class);
        switch (size) {
            case "1": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI1m2)).forEach(System.out::println);
                break;
            }
            case "2": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI2m2)).forEach(System.out::println);
                break;
            }
            case "3": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI3m2)).forEach(System.out::println);
                break;
            }
            case "4": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI4m2)).forEach(System.out::println);
                break;
            }
            case "6": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MIDI6m2)).forEach(System.out::println);
                break;
            }
            case "9": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MIDI9m2)).forEach(System.out::println);
                break;
            }
            case "15": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MAX15m2)).forEach(System.out::println);
                break;
            }
            case "20": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MAX20m2)).forEach(System.out::println);
                break;
            }
            default: {
                System.out.println("Brak takiego rozmiaru");
                break;
            }
        }
    }

}
