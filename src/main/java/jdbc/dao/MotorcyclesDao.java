package jdbc.dao;

import jdbc.entity.Manufacturers;
import jdbc.entity.Motorcycles;
import jdbc.exception.DaoException;
import jdbc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MotorcyclesDao implements Dao<Integer, Motorcycles> {
    private static final MotorcyclesDao INSTANCE = new MotorcyclesDao();
    private final ManufacturersDao manufacturersDao = ManufacturersDao.getInstance();

    private MotorcyclesDao() {
    }

    public static MotorcyclesDao getInstance(){
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE motorcycles
            SET 
                model = ?,
                manufacturers_id = ?,
                year = ?,
                engine_cc = ?,
                price = ?,
                quantity = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT m.id, m.id, m.model, m.manufacturers_id, m.year, m.engine_cc, m.price, m.quantity
            FROM motorcycles m
            
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE m.id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO motorcycles 
            (model, manufacturers_id, year, engine_cc, price, quantity)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM motorcycles
            where id = ?
            """;

    private Motorcycles buildManufacturers(ResultSet result) throws SQLException {
        return new Motorcycles(result.getInt("id"),
                result.getString("model"),
                manufacturersDao.findById(
                        result.getInt("manufacturers_id"),
                        result.getStatement().getConnection()).orElse(null),
                result.getInt("year"),
                result.getInt("engine_cc"),
                result.getBigDecimal("price"),
                result.getInt("quantity"));
    }

    @Override
    public boolean update(Motorcycles motorcycles) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, motorcycles.getModel());
            statement.setInt(2, motorcycles.getManufacturersId().getId());
            statement.setInt(3, motorcycles.getYear());
            statement.setInt(4, motorcycles.getEngineCc());
            statement.setBigDecimal(5, motorcycles.getPrice());
            statement.setInt(6, motorcycles.getQuantity());
            statement.setLong(7, motorcycles.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Motorcycles> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Motorcycles> tickets = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next())
                tickets.add(buildManufacturers(result));


            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Motorcycles> findById(Integer id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Motorcycles motorcycles = null;
            var result = statement.executeQuery();
            if (result.next())
                motorcycles = buildManufacturers(result);
            return Optional.ofNullable(motorcycles);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Motorcycles> findById(Integer id) {
        try
                (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Motorcycles save(Motorcycles motorcycles) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, motorcycles.getModel());
            statement.setInt(2, motorcycles.getManufacturersId().getId());
            statement.setInt(3, motorcycles.getYear());
            statement.setInt(4, motorcycles.getEngineCc());
            statement.setBigDecimal(5, motorcycles.getPrice());
            statement.setInt(6, motorcycles.getQuantity());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                motorcycles.setId(keys.getInt("id"));

            return motorcycles;
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
