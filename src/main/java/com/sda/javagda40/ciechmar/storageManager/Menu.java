package com.sda.javagda40.ciechmar.storageManager;

import java.util.Scanner;

public class Menu {

    protected static void menu() {

        System.out.println("\n\n\n\n*************************************************");
        System.out.println("  ***************STORAGE-MANAGER***************");
        System.out.println("*************************************************");
        Scanner scanner = new Scanner(System.in);

        StorageUtils storageUtils = new StorageUtils();
        UserUtil userUtil = new UserUtil();
        String command;
        do {
            System.out.println("\n  Wprowadz komende (lista komend wpisz: help): ");
            command = scanner.nextLine();
            String[] words = command.split(" ");

            if (words[0].equalsIgnoreCase("help")) {
                UserHelp.list();
            } else if (words[0].equalsIgnoreCase("user") ) {
                if (words[1].equalsIgnoreCase("add")){userUtil.handleAdduser();}
                else if (words[1].equalsIgnoreCase("delete")) { userUtil.handleDeleteUser();}
                else if (words[1].equalsIgnoreCase("find")) { userUtil.handleFindUser();  }
                else if (words[1].equalsIgnoreCase("list")) { userUtil.handleListUser();  }
//                else if (words[1].equalsIgnoreCase("address")) { userUtil.handleAddressUser();  }
//                else if (words[1].equalsIgnoreCase("change")) { userUtil.handleChangeDataUser();  }

            } else if (words[0].equalsIgnoreCase("storage")) {
                 if (words[1].equalsIgnoreCase("delete")) {  storageUtils.handleDeleteStorage();}
                else if (words[1].equalsIgnoreCase("find")) { storageUtils.handleFindStorage();  }
                else if (words[1].equalsIgnoreCase("add")) { storageUtils.handleAddStorage();  }
                else if (words[1].equalsIgnoreCase("list")) { storageUtils.handleListStorage();  }
                else if (words[1].equalsIgnoreCase("free")) { storageUtils.showfreemagazynlistBySize();  }
                else if (words[1].equalsIgnoreCase("rented")) { storageUtils.showReservations(); }
                else if (words[1].equalsIgnoreCase("rent")) { storageUtils.handleRentStorage();  }
            }
        } while (!command.equalsIgnoreCase("end"));

    }
}
