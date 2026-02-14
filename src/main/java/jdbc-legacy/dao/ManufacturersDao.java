package jdbc.dao;

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

public class ManufacturersDao implements Dao<Integer, Manufacturers> {
    private static final ManufacturersDao INSTANCE = new ManufacturersDao();

    private ManufacturersDao() {
    }

    public static ManufacturersDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE manufacturers
            SET 
                name = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT m.id, m.name
            FROM manufacturers m
            
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE m.id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO manufacturers 
            (name)
            VALUES (?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM manufacturers
            where id = ?
            """;

    private Manufacturers buildManufacturers(ResultSet result) throws SQLException {
        return new Manufacturers(result.getInt("id"),
                result.getString("name"));
    }

    @Override
    public boolean update(Manufacturers manufacturers) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, manufacturers.getName());
            statement.setInt(2, manufacturers.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Manufacturers> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Manufacturers> manufacturers = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                manufacturers.add(buildManufacturers(result));
            return manufacturers;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Manufacturers> findById(Integer id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Manufacturers manufacturers = null;
            var result = statement.executeQuery();
            if (result.next())
                manufacturers = buildManufacturers(result);
            return Optional.ofNullable(manufacturers);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Manufacturers> findById(Integer id) {
        try
                (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Manufacturers save(Manufacturers manufacturers) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturers.getName());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                manufacturers.setId(keys.getInt("id"));

            return manufacturers;
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
