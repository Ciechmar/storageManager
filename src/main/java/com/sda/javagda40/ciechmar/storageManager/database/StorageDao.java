package com.sda.javagda40.ciechmar.storageManager.database;

import com.sda.javagda40.ciechmar.storageManager.model.Storage;
import com.sda.javagda40.ciechmar.storageManager.model.StorageSize;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.sda.javagda40.ciechmar.storageManager.model.StorageStatus.FREE;
import static com.sda.javagda40.ciechmar.storageManager.model.StorageStatus.RENT;

public class StorageDao {
    private SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();

    public List<Storage> findAllRented() {
        List<Storage> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Storage> criteriaQuery = cb.createQuery(Storage.class);
            Root<Storage> rootTable = criteriaQuery.from(Storage.class);
            criteriaQuery.select(rootTable).where(
                    cb.equal(rootTable.get("status"), RENT)
            ).orderBy(
                    cb.desc(rootTable.get("size"))
            );
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public List<Storage> findAllFree() {
         List<Storage> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Storage> criteriaQuery = cb.createQuery(Storage.class);
            Root<Storage> rootTable = criteriaQuery.from(Storage.class);
            criteriaQuery.select(rootTable).where(
                    cb.equal(rootTable.get("status"), FREE)
            ).orderBy(
                    cb.desc(rootTable.get("size"))
            );
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public List<Storage> findBySize(StorageSize size) {
         List<Storage> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Storage> criteriaQuery = cb.createQuery(Storage.class);
            Root<Storage> rootTable = criteriaQuery.from(Storage.class);
            criteriaQuery.select(rootTable).where(
                    cb.equal(rootTable.get("size"), size)
            );
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public List<Storage> findGarage() {
         List<Storage> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Storage> criteriaQuery = cb.createQuery(Storage.class);
            Root<Storage> rootTable = criteriaQuery.from(Storage.class);
            criteriaQuery.select(rootTable).where(
                    cb.equal(rootTable.get("isGarage"), true)
            );
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public List<Storage> findForSanepidUse() {
         List<Storage> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Storage> criteriaQuery = cb.createQuery(Storage.class);
            Root<Storage> rootTable = criteriaQuery.from(Storage.class);
            criteriaQuery.select(rootTable).where(
                    cb.equal(rootTable.get("forSanepidUse"), true)
            );
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public List<Storage> findDoorNumber(int doorNumber) {
        List<Storage> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Storage> criteriaQuery = cb.createQuery(Storage.class);
            Root<Storage> rootTable = criteriaQuery.from(Storage.class);
            criteriaQuery.select(rootTable).where(
                    cb.equal(rootTable.get("doorNumber"), doorNumber)
            );
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }
}
