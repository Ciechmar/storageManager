package com.sda.javagda40.ciechmar.storageManager;

import com.sda.javagda40.ciechmar.storageManager.database.HibernateUtil;

public class App {

    public static void main(String[] args) {
        System.out.println("Initial version.");
        HibernateUtil.getOurSessionFactory();
        Menu menu = new Menu();
        menu.menu();

    }


}
