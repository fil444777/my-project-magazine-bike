package jdbc.dao;

import jdbc.entity.Orders;
import jdbc.entity.OrdersItems;
import jdbc.exception.DaoException;
import jdbc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersItemsDao implements Dao<Integer, OrdersItems> {
    private static final OrdersItemsDao INSTANCE = new OrdersItemsDao();
    private final OrdersDao ordersDao = OrdersDao.getInstance();
    private final MotorcyclesDao motorcyclesDao = MotorcyclesDao.getInstance();

    private OrdersItemsDao() {
    }

    public static OrdersItemsDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE orders_items
            SET 
                order_id = ?,
                motorcycle_id = ?,
                quantity = ?,
                price_per_unity = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT o.id, o.order_id, o.motorcycle_id, o.quantity, o.price_per_unity
            FROM orders_items o
            
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE o.id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO orders_items 
            (order_id, motorcycle_id, quantity, price_per_unity)
            VALUES (?, ?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM orders_items
            where id = ?
            """;

    private OrdersItems buildOrdersItems(ResultSet result) throws SQLException {
        return new OrdersItems(result.getInt("id"),
                ordersDao.findById(
                        result.getInt("order_id"),
                        result.getStatement().getConnection()).orElse(null),
                motorcyclesDao.findById(
                        result.getInt("motorcycles_id"),
                        result.getStatement().getConnection()).orElse(null),
                result.getInt("quantity"),
                result.getBigDecimal("price_per_unity"));
    }


    @Override
    public boolean update(OrdersItems ordersItems) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, ordersItems.getOrdersId().getId());
            statement.setInt(2, ordersItems.getMotorcyclesId().getId());
            statement.setInt(3, ordersItems.getQuantity());
            statement.setBigDecimal(4, ordersItems.getPricePerUnity());
            statement.setInt(5, ordersItems.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrdersItems> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<OrdersItems> ordersItems = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                ordersItems.add(buildOrdersItems(result));
            return ordersItems;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<OrdersItems> findById(Integer id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            OrdersItems ordersItems = null;
            var result = statement.executeQuery();
            if (result.next())
                ordersItems = buildOrdersItems(result);
            return Optional.ofNullable(ordersItems);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<OrdersItems> findById(Integer id) {
        try
                (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public OrdersItems save(OrdersItems ordersItems) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, ordersItems.getOrdersId().getId());
            statement.setInt(2, ordersItems.getMotorcyclesId().getId());
            statement.setInt(3, ordersItems.getQuantity());
            statement.setBigDecimal(4, ordersItems.getPricePerUnity());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                ordersItems.setId(keys.getInt("id"));

            return ordersItems;
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
