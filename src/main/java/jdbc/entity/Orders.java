package jdbc.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class Orders {
    private Integer id;
    private Customers customersId;
    private LocalDate orderDate;
    private BigDecimal totalPrice;

    public Orders(Integer id, Customers customersId, LocalDate orderDate, BigDecimal totalPrice) {
        this.id = id;
        this.customersId = customersId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customers getCustomersId() {
        return customersId;
    }

    public void setCustomersId(Customers customersId) {
        this.customersId = customersId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(id, orders.id) && Objects.equals(customersId, orders.customersId) && Objects.equals(orderDate, orders.orderDate) && Objects.equals(totalPrice, orders.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customersId, orderDate, totalPrice);
    }

    @Override
    public String toString() {
        return "Orders{" +
               "id=" + id +
               ", customersId=" + customersId +
               ", orderDate=" + orderDate +
               ", totalPrice=" + totalPrice +
               '}';
    }
}
