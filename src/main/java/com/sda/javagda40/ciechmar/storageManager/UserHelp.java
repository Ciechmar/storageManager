package com.sda.javagda40.ciechmar.storageManager;

import com.google.protobuf.MapEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UserHelp {

    static void list() {
        Map<String, String> helpList = new TreeMap<>();
        helpList.put("user find       ", "Find user by Id, Surname, telephone Number");
        helpList.put("user add        ", "Add user or company");
        helpList.put("user delete     ", "Delete user");
        helpList.put("user list       ", "Show all user list (id, name, tel number, email, password) ");
//        helpList.put("user address    ", "Changing adress");
//        helpList.put("user change     ", "Changing users data : phone/email/surname");
        helpList.put("storage find    ", "Find storage by Id, size, door number, status ");
        helpList.put("storage add     ", "Add new storage");
        helpList.put("storage delete  ", "Delete storage");
        helpList.put("storage list    ", "Show all storage list (id, size, floor, color, status, for sanepid use, is it garage");
        helpList.put("storage free    ", "Show list of free storages sort by size");
        helpList.put("storage rented  ", "Show list of rent storages sort by size");
        helpList.put("rent add        ", "Rent new storage");
//        helpList.put("rent list       ", "list of rentals");
//        helpList.put("report month    ", "monthly report");
//        helpList.put("report day      ", "dzienny report");
//        helpList.put("report day      ", "annualy report");
        helpList.put("end             ", "Close program");
        System.out.println("Lista komend:");
        for (Map.Entry<String, String> all : helpList.entrySet()) {
            System.out.println(all.getKey() + ": "+ all.getValue());
        }
    }
}
