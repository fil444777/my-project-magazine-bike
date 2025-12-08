package jdbc.dao;

import jdbc.entity.Customers;
import jdbc.entity.Manufacturers;
import jdbc.exception.DaoException;
import jdbc.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomersDao implements Dao<Integer, Customers>{

    private static final CustomersDao INSTANCE = new CustomersDao();

    private CustomersDao() {
    }

    public static CustomersDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE customers
            SET 
                name = ?,
                email = ?,
                phone = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT c.id, c.name, c.id, c.name, c.email, c.phone
            FROM customers c
            
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE c.id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO customers 
            (name, email, phone)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM customers
            where id = ?
            """;

    private Customers buildCustomers(ResultSet result) throws SQLException {
        return new Customers(result.getInt("id"),
                result.getString("name"),
                result.getString("email"),
                result.getString("phone"));
    }

    @Override
    public boolean update(Customers customers) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, customers.getName());
            statement.setString(2, customers.getEmail());
            statement.setString(3, customers.getPhone());
            statement.setInt(4, customers.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Customers> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Customers> customers = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                customers.add(buildCustomers(result));
            return customers;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Customers> findById(Integer id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Customers customers = null;
            var result = statement.executeQuery();
            if (result.next())
                customers = buildCustomers(result);
            return Optional.ofNullable(customers);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Customers> findById(Integer id) {
        try
                (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Customers save(Customers customers) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customers.getName());
            statement.setString(2, customers.getEmail());
            statement.setString(3, customers.getPhone());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                customers.setId(keys.getInt("id"));

            return customers;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
