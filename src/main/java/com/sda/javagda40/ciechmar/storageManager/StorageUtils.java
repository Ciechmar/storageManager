package com.sda.javagda40.ciechmar.storageManager;

import com.sda.javagda40.ciechmar.storageManager.database.EntityDao;
import com.sda.javagda40.ciechmar.storageManager.model.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class StorageUtils {

    protected static void handleRentStorage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id klienta:");
        Long userId = scanner.nextLong();
        Long storageId = scanner.nextLong();
        EntityDao<Storage> storageEntityDao = new EntityDao<>();
        EntityDao<AppUser> userEntityDao = new EntityDao<>();
//        Todo: - wybór kryteriów , wyświetlenie listy, znalezienie po Id, rezerwacja *** połączei danego magazynu z użytkownikiem ****
        Rent rent = new Rent();
        rent.setStorage(storageEntityDao.findById(Storage.class, storageId).get()); //ifPresent jakby nie było magazyn/użytkownika z takim ID
        rent.setUserrent(userEntityDao.findById(AppUser.class, userId).get());
        System.out.println("Od kiedy wynajem? YYYYY-MM-DD");
        rent.setRentFrom(LocalDate.parse(scanner.nextLine()));
        System.out.println("Do kiedy wynajem? YYYYY-MM-DD");
        rent.setRentTo(LocalDate.parse(scanner.nextLine()));
        System.out.println("Czy klientowi przysłguje zniżka? T/N");
        if (scanner.nextLine().equalsIgnoreCase("n")) {
            rent.setFinalPrize(storageEntityDao.findById(Storage.class, storageId).get().getStandardPrize());
        } else {
            float prize = storageEntityDao.findById(Storage.class, storageId).get().getStandardPrize();
            System.out.println("Ile zniżki dodać?");
            prize = prize - scanner.nextFloat();
            System.out.printf("Cena za magazyn to: %.2f", prize); //jak zmusić,by nie było możliwości wpisania 3 miejsc po przecinku?! -nie gubiło tysięcznych złotówki
            rent.setFinalPrize(prize);
        }
        storageEntityDao.findById(Storage.class, storageId).ifPresent(storage -> storage.setStatus(StorageStatus.RENT));
        storageEntityDao.findById(Storage.class, storageId).ifPresent(storage -> storage.getStorageRentals().add(rent));
    }

    protected static void showReservations() {
        EntityDao<Storage> storageEntityDao = new EntityDao<>();
        List<Storage> storageList = storageEntityDao.findAll(Storage.class);
        storageList.stream().filter(storage -> storage.getStatus().equals(StorageStatus.RENT))
                .sorted(Comparator.comparing(Storage::getSize))
                .forEach(System.out::println);
    }

    protected static void handleFindStorage() {
        Scanner scanner = new Scanner(System.in);
        EntityDao<Storage> storageEntityDao = new EntityDao<>();
        List<Storage> storageList = storageEntityDao.findAll(Storage.class);
        System.out.println("Szukaj po: \n1.Rozmiar\n2.Specyfikacja\n3.Numer drzwi\n4.Id");
        switch (scanner.nextLine()) {
            case "1":
            case "rozmiar": {
                System.out.println("Lista magazynów rozmiar: \n1.MINI (1-4m2)\n2.MIDI(6-9 m2)\n3.MAX(15+ m2)\n4.Konkretny rozmiar");
                String answer = scanner.nextLine();
                switch (answer) {
                    case "1":
                    case "mini":
                    case "2":
                    case "midi":
                    case "3":
                    case "max": {
                        findBySizeMinMidiMax(answer);
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
                System.out.println("Lista magazynów typu \n1.garaż\2do użytku sanepidowskiego");
                String answer = scanner.nextLine();
                if (answer.equals("1") || answer.equalsIgnoreCase("garaz") || answer.equalsIgnoreCase("garaż")) {
                    System.out.println("Magazyny garażowe:");
                    storageList.stream().filter(Storage::isGarage).forEach(System.out::println);
                } else {
                    System.out.println("Magazyny do użytku sanepidowskiego:");
                    storageList.stream().filter(Storage::isForSanepidUse).forEach(System.out::println);
                }
                break;
            }
            case "3":
            case "Numer drzwi": {
                System.out.println("Podaj numer drzwi");
                int doorNumber = scanner.nextInt();
//                scanner.nextLine();            usersList.stream().filter(storage -> storage.getDoorNumber(scanner.nextInt())).forEach(System.out::println);
                for (Storage storage : storageList) {
                    if (storage.getDoorNumber() == doorNumber) {
                        System.out.println(storage);
                    }
                }
                break;
            }
            case "4":
            case "id": {
                System.out.println("Podaj id:");
                Long id = scanner.nextLong();
                storageEntityDao.findById(Storage.class, id).ifPresent(System.out::println);
//                return storageEntityDao.findById(Storage.class, id).get();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + scanner.nextLine());
        }


    }

    protected static void handleDeleteStorage() {
        System.out.println("Czy znasz ID użytkownika, którego chcesz usunąć z bazy danych? T/N");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equalsIgnoreCase("T")) {
            System.out.println("Podaj ID użytkownia:");
            Long id = scanner.nextLong();
            EntityDao<AppUser> userEntityDao = new EntityDao<>();
            userEntityDao.findById(AppUser.class, id).ifPresent(userEntityDao::delete);
        } else {
            UserUtil.handleFindUser();
            System.out.println("Podaj ID użytkownia:");
            Long id = scanner.nextLong();
            EntityDao<AppUser> userEntityDao = new EntityDao<>();
            userEntityDao.findById(AppUser.class, id).ifPresent(userEntityDao::delete);
        }


    }

    protected static void handleAddStorage() {
        EntityDao<Storage> storageEntityDao = new EntityDao<>();
        Storage storage = new Storage();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj dane nowego magazynu:");
        do {
            storage.setDoorNumber(0);
            System.out.println("Podaj numer magazynu:");
            storage.setDoorNumber(scanner.nextInt());
            scanner.nextLine();
        } while (storage.getDoorNumber() == 0);
        do {
            System.out.println("Rozmiar: 1/2/3/4/6/9/15/20(w m2)"); //MINI2m2, MINI3m2, MINI4m2, MIDI6m2, MIDI9m3, MAX15m2, MAX20m2;

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
                    storage.setStatus(StorageStatus.FREE);
                    break;
                }
                case "20": {
                    storage.setSize(StorageSize.MAX20m2);
                    storage.setStandardPrize(250);
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

    protected static void handleListStorage() {
        EntityDao<Storage> classEntityDao = new EntityDao();
        classEntityDao.findAll(Storage.class)
//                .stream()
                .forEach((System.out::println));
    }

    protected static Set<Storage> showfreemagazynlistBySize() {
        EntityDao<Storage> storageEntityDao = new EntityDao<>();
        List<Storage> storageList = storageEntityDao.findAll(Storage.class);
        Set<Storage> freeStorageSet = storageList.stream().filter(storage -> storage.getStatus().equals(StorageStatus.FREE)).collect(Collectors.toSet());
        freeStorageSet.stream().sorted(Comparator.comparing(Storage::getSize)).forEach(System.out::println);
        return freeStorageSet;
    }

    private static void findBySize(String size) {
        EntityDao<Storage> storageEntityDao = new EntityDao<>();
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

    private static void findBySizeMinMidiMax(String size) {
        EntityDao<Storage> storageEntityDao = new EntityDao<>();
        List<Storage> storageList = storageEntityDao.findAll(Storage.class);
        switch (size) {
            case "1":
            case "mini": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI1m2)).forEach(System.out::println);
//                Set<Storage> miniSizeSet = storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI1m2)).collect(Collectors.toSet());
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI2m2)).forEach(System.out::println);
//                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI2m2)).collect(Collectors.toSet());
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI3m2)).forEach(System.out::println);
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MINI4m2)).forEach(System.out::println);
                break;
            }
            case "2":
            case "midi": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MIDI6m2)).forEach(System.out::println);
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MIDI9m2)).forEach(System.out::println);
                break;
            }
            case "3":
            case "max": {
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MAX15m2)).forEach(System.out::println);
                storageList.stream().filter(storage -> storage.getSize().equals(StorageSize.MAX20m2)).forEach(System.out::println);
                break;
            }
            default:
                System.out.println("Nie ma takiego rozmiaru magazynu");
        }
    }


}
