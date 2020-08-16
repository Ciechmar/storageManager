package com.sda.javagda40.ciechmar.storageManager;

import com.sda.javagda40.ciechmar.storageManager.database.EntityDao;
import com.sda.javagda40.ciechmar.storageManager.database.HibernateUtil;
import com.sda.javagda40.ciechmar.storageManager.model.AppUser;

public class App {

    public static void main(String[] args) {
        System.out.println("Initial version.");
        HibernateUtil.getOurSessionFactory();
        System.out.println("Tested Hibernate");

        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao.saveOrUpdate(new AppUser("Maryna", "Zuch", "następny@o2.pl","*****123***", false, null, null));


    }
}
