package com.sda.javagda40.ciechmar.storageManager.database;

import com.sda.javagda40.ciechmar.storageManager.model.AppUser;
import com.sda.javagda40.ciechmar.storageManager.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDao {
    private SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
    private List<AppUser> list = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public List<AppUser> findByNameOrPhoneOrID(String searchedData) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AppUser> criteriaQuery = cb.createQuery(AppUser.class);
            Root<AppUser> rootTable = criteriaQuery.from(AppUser.class);
            criteriaQuery.select(rootTable).where(
                    cb.or(
                            cb.like(rootTable.get("lastName"), "%"+searchedData+"%"),
                            cb.like(rootTable.get("phoneNumber"), "%"+searchedData+"%")
//                          ,  cb.like(rootTable.get("id"), "%"+searchedData+"%")
                    )
            ).orderBy(
                    cb.desc(rootTable.get("lastName"))
            );
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }
}
