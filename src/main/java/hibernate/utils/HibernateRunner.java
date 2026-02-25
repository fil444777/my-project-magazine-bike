package hibernate.utils;

import hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunner {
    public static void main(String[] args) {

        User user = null;

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            //TestDataImporter.importData(sessionFactory);
            session.beginTransaction();

            user = session.find(User.class, 1);
            var user1 = session.find(User.class, 1);

            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user2 = session.find(User.class, 1);

            session.getTransaction().commit();
        }
    }
}
