package jdbc.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class OrdersItems {
    private Integer id;
    private Orders ordersId;
    private Motorcycles motorcyclesId;
    private Integer quantity;
    private BigDecimal pricePerUnity;

    public OrdersItems(Integer id, Orders ordersId, Motorcycles motorcyclesId, Integer quantity, BigDecimal pricePerUnity) {
        this.id = id;
        this.ordersId = ordersId;
        this.motorcyclesId = motorcyclesId;
        this.quantity = quantity;
        this.pricePerUnity = pricePerUnity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orders getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Orders ordersId) {
        this.ordersId = ordersId;
    }

    public Motorcycles getMotorcyclesId() {
        return motorcyclesId;
    }

    public void setMotorcyclesId(Motorcycles motorcyclesId) {
        this.motorcyclesId = motorcyclesId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerUnity() {
        return pricePerUnity;
    }

    public void setPricePerUnity(BigDecimal pricePerUnity) {
        this.pricePerUnity = pricePerUnity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrdersItems that = (OrdersItems) o;
        return Objects.equals(id, that.id) && Objects.equals(ordersId, that.ordersId) && Objects.equals(motorcyclesId, that.motorcyclesId) && Objects.equals(quantity, that.quantity) && Objects.equals(pricePerUnity, that.pricePerUnity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ordersId, motorcyclesId, quantity, pricePerUnity);
    }

    @Override
    public String toString() {
        return "OrdersItems{" +
               "id=" + id +
               ", ordersId=" + ordersId +
               ", motorcyclesId=" + motorcyclesId +
               ", quantity=" + quantity +
               ", pricePerUnity=" + pricePerUnity +
               '}';
    }
}
