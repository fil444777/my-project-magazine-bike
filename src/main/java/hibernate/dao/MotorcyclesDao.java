package hibernate.dao;

import hibernate.entity.Motorcycles;
import hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class MotorcyclesDao implements Dao<Integer, Motorcycles> {
    private static final MotorcyclesDao INSTANCE = new MotorcyclesDao();

    private MotorcyclesDao() {
    }

    public static MotorcyclesDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Motorcycles motorcycles) {
        return false;
    }

    @Override
    public List<Motorcycles> findAll() {
        return List.of();
    }

    @Override
    public Optional<Motorcycles> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Motorcycles save(Motorcycles motorcycles) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public List<Motorcycles> findAllByUserId(Integer userId) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()){
            return session.createQuery(
                            "SELECT DISTINCT o.motorcycles " +  // DISTINCT чтобы избежать дубликатов
                                    "FROM Order o " +
                                    "WHERE o.user.id = :userId " +
                                    "ORDER BY o.motorcycles.model",
                            Motorcycles.class
                    )
                    .setParameter("userId", userId)
                    .getResultList();
        }

    }
}
