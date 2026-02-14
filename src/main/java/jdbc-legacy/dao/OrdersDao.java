package jdbc.dao;

import jdbc.entity.Customers;
import jdbc.entity.Orders;
import jdbc.exception.DaoException;
import jdbc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersDao implements Dao <Integer, Orders>{
    private static final OrdersDao INSTANCE = new OrdersDao();
    private final CustomersDao customersDao = CustomersDao.getInstance();

    private OrdersDao() {
    }

    public static OrdersDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE orders
            SET 
                customers_id = ?,
                order_date = ?,
                total_price = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT o.id, o.customers_id, o.order_date, o.total_price
            FROM orders o
            
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE o.id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO orders 
            (customers_id, order_date, total_price)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM orders
            where id = ?
            """;

    private Orders buildOrders(ResultSet result) throws SQLException {
        return new Orders(result.getInt("id"),
                customersDao.findById(
                        result.getInt("customers_id"),
                        result.getStatement().getConnection()).orElse(null),
                result.getDate("order_date").toLocalDate(),
                result.getBigDecimal("total_price"));
    }

    @Override
    public boolean update(Orders orders) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, orders.getCustomersId().getId());
            statement.setDate(2, Date.valueOf(orders.getOrderDate()));
            statement.setBigDecimal(3, orders.getTotalPrice());
            statement.setInt(4, orders.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Orders> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Orders> orders = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                orders.add(buildOrders(result));
            return orders;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Orders> findById(Integer id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Orders orders = null;
            var result = statement.executeQuery();
            if (result.next())
                orders = buildOrders(result);
            return Optional.ofNullable(orders);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Orders> findById(Integer id) {
        try
                (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Orders save(Orders orders) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, orders.getCustomersId().getId());
            statement.setDate(2, Date.valueOf(orders.getOrderDate()));
            statement.setBigDecimal(3, orders.getTotalPrice());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                orders.setId(keys.getInt("id"));

            return orders;
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
