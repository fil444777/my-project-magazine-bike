package dao;

import hibernate.dao.MotorcyclesDao;
import hibernate.entity.Manufacturer;
import hibernate.entity.Motorcycle;
import hibernate.entity.User;
import hibernate.utils.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ustils.TestDataImporter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MotorcycleDaoTest {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final MotorcyclesDao motorcyclesDao = MotorcyclesDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        HibernateUtil.shutdown();
    }

    @Test
    void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Motorcycle> result = motorcyclesDao.findAll();
        assertThat(result).hasSize(6);

        List<String> models = result.stream().map(Motorcycle::getModel).collect(Collectors.toUnmodifiableList());
        assertThat(models).containsExactlyInAnyOrder("CBR500R", "YZF-R3", "Sportster S", "Ninja 400", "Panigale V4"
                , "CB650R");
    }


    @Test
    void findById() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Optional<Motorcycle> actual = motorcyclesDao.findById(1);

        assertThat(actual)
                .isPresent()
                .get()
                .extracting("model", "year", "engine", "price", "quantity")
                .containsExactly("CBR500R", 2023, 471, new BigDecimal("6500.00"), 5);
    }

    @Test
    void findAllByUserId() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Motorcycle> result = motorcyclesDao.findAllByUserId(4);
        assertThat(result).hasSize(3);

        List<String> models = result.stream().map(Motorcycle::getModel).collect(Collectors.toUnmodifiableList());
        assertThat(models).containsExactlyInAnyOrder("Sportster S", "Panigale V4", "CB650R");
    }
}
