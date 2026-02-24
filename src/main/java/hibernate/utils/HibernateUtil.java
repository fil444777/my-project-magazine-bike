package hibernate.utils;

import hibernate.converter.BirthdayConverter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .configure()
                        .addAttributeConverter(new BirthdayConverter())
                        .buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
