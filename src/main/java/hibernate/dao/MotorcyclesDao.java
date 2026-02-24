package hibernate.dao;

import hibernate.entity.Motorcycle;
import hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class MotorcyclesDao implements Dao<Integer, Motorcycle> {
    private static final MotorcyclesDao INSTANCE = new MotorcyclesDao();

    private MotorcyclesDao() {
    }

    public static MotorcyclesDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Motorcycle motorcycles) {
        return false;
    }

    @Override
    public List<Motorcycle> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    select distinct m 
                    from Motorcycle m
                    left join fetch m.manufacturer""", Motorcycle.class).getResultList();
        }
    }

    @Override
    public Optional<Motorcycle> findById(Integer motorcycleId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                            select distinct m 
                            from Motorcycle m
                            left join fetch m.manufacturer
                            where m.id = :motorcycleId""", Motorcycle.class)
                    .setParameter("motorcycleId", motorcycleId)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Motorcycle save(Motorcycle motorcycles) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public List<Motorcycle> findAllByUserId(Integer userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT m " +
                                    "FROM Motorcycle m " +
                                    "LEFT JOIN FETCH m.manufacturer " +
                                    "JOIN m.orders o " +
                                    "WHERE o.user.id = :userId " +
                                    "ORDER BY m.model",
                            Motorcycle.class
                    )
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
}
