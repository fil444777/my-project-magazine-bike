package ustils;

import hibernate.entity.*;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@UtilityClass
public class TestDataImporter {
    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        Manufacturer honda = saveManufacturer(session, "Honda");
        Manufacturer yamaha = saveManufacturer(session, "Yamaha");
        Manufacturer kawasaki = saveManufacturer(session, "Kawasaki");
        Manufacturer suzuki = saveManufacturer(session, "Suzuki");
        Manufacturer ducati = saveManufacturer(session, "Ducati");

        User admin = saveUser(session, "Админ",
                LocalDate.of(1990, Month.JANUARY, 1),
                "admin@motoshop.ru", "admin123",
                Role.ADMIN, Gender.MALE);

        User alex = saveUser(session, "Алексей",
                LocalDate.of(1995, Month.MARCH, 15),
                "alex123@mail.ru", "password123",
                Role.USER, Gender.MALE);

        User maria = saveUser(session, "Мария",
                LocalDate.of(1998, Month.JULY, 22),
                "maria@gmail.com", "password123",
                Role.USER, Gender.FEMALE);

        User dmitry = saveUser(session, "Дмитрий",
                LocalDate.of(1992, Month.NOVEMBER, 5),
                "dmitry@yandex.ru", "password123",
                Role.USER, Gender.MALE);

        Motorcycle cbr500r = saveMotorcycle(session, "CBR500R",
                honda, 2023, 471, new BigDecimal("6500.00"), 5);

        Motorcycle yzfR3 = saveMotorcycle(session, "YZF-R3",
                yamaha, 2023, 321, new BigDecimal("4800.00"), 8);

        Motorcycle sportsterS = saveMotorcycle(session, "Sportster S",
                suzuki, 2023, 1200, new BigDecimal("12000.00"), 3);

        Motorcycle ninja400 = saveMotorcycle(session, "Ninja 400",
                kawasaki, 2023, 399, new BigDecimal("5500.00"), 6);

        Motorcycle panigaleV4 = saveMotorcycle(session, "Panigale V4",
                ducati, 2023, 1103, new BigDecimal("25000.00"), 2);

        Motorcycle cb650r = saveMotorcycle(session, "CB650R",
                honda, 2022, 649, new BigDecimal("8500.00"), 4);

        saveOrder(session, alex, cbr500r, LocalDate.of(2026, Month.FEBRUARY, 10), new BigDecimal("6500.00"));
        saveOrder(session, alex, ninja400, LocalDate.of(2026, Month.FEBRUARY, 15), new BigDecimal("5500.00"));

        saveOrder(session, maria, yzfR3, LocalDate.of(2026, Month.FEBRUARY, 12), new BigDecimal("4800.00"));

        saveOrder(session, dmitry, sportsterS, LocalDate.of(2026, Month.FEBRUARY, 8), new BigDecimal("12000.00"));
        saveOrder(session, dmitry, panigaleV4, LocalDate.of(2026, Month.FEBRUARY, 20), new BigDecimal("25000.00"));
        saveOrder(session, dmitry, cb650r, LocalDate.of(2026, Month.FEBRUARY, 22), new BigDecimal("8500.00"));

        saveOrder(session, admin, cbr500r, LocalDate.of(2026, Month.FEBRUARY, 1), new BigDecimal("6500.00"));
    }

    private Manufacturer saveManufacturer(Session session, String name) {
        Manufacturer manufacturer = Manufacturer.builder()
                .name(name)
                .build();
        session.save(manufacturer);
        return manufacturer;
    }

    private User saveUser(Session session,
                          String name,
                          LocalDate birthday,
                          String email,
                          String password,
                          Role role,
                          Gender gender) {
        User user = User.builder()
                .personalInfo(PersonalInfo.builder()
                        .name(name)
                        .birthday(new Birthday(birthday))
                        .build())
                .email(email)
                .password(password)
                .role(role)
                .gender(gender)
                .build();
        session.save(user);
        return user;
    }

    private Motorcycle saveMotorcycle(Session session,
                                      String model,
                                      Manufacturer manufacturer,
                                      Integer year,
                                      Integer engineCc,
                                      BigDecimal price,
                                      Integer quantity) {
        Motorcycle motorcycle = Motorcycle.builder()
                .model(model)
                .manufacturer(manufacturer)
                .year(year)
                .engine(engineCc)
                .price(price)
                .quantity(quantity)
                .build();
        session.save(motorcycle);
        return motorcycle;
    }

    private void saveOrder(Session session,
                           User user,
                           Motorcycle motorcycle,
                           LocalDate orderDate,
                           BigDecimal totalPrice) {
        Order order = Order.builder()
                .user(user)
                .motorcycles(motorcycle)
                .orderDate(orderDate)
                .totalPrice(totalPrice)
                .build();
        session.save(order);
    }
}
