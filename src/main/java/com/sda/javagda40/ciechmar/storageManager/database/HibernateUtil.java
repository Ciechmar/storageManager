package com.sda.javagda40.ciechmar.storageManager.database;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            System.out.println("Konfiguruję hiberante");
            Configuration configuration = new Configuration();
            System.out.println("wczytuje cfg");
            configuration.configure("/hibernate.cfg.xml");
            System.out.println("buduje sesję");
            SESSION_FACTORY = configuration.buildSessionFactory();
        } catch (HibernateException he) {
            System.err.println(he.getMessage());
            throw he;
        }
    }

    public static SessionFactory getOurSessionFactory() {
        return SESSION_FACTORY;
    }
}


