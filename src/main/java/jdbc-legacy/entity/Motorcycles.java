package jdbc.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Motorcycles {
    private Integer id;
    private String model;
    private Manufacturers manufacturersId;
    private Integer year;
    private Integer engineCc;
    private BigDecimal price;
    private Integer quantity;

    public Motorcycles(Integer id, String model, Manufacturers manufacturersId,
                       Integer year, Integer engineCc, BigDecimal price, Integer quantity) {
        this.id = id;
        this.model = model;
        this.manufacturersId = manufacturersId;
        this.year = year;
        this.engineCc = engineCc;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturers getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(Manufacturers manufacturersId) {
        this.manufacturersId = manufacturersId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getEngineCc() {
        return engineCc;
    }

    public void setEngineCc(Integer engineCc) {
        this.engineCc = engineCc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Motorcycles that = (Motorcycles) o;
        return Objects.equals(id, that.id) && Objects.equals(model, that.model) && Objects.equals(manufacturersId, that.manufacturersId) && Objects.equals(year, that.year) && Objects.equals(engineCc, that.engineCc) && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, manufacturersId, year, engineCc, price, quantity);
    }

    @Override
    public String toString() {
        return "Motorcycles{" +
               "id=" + id +
               ", model='" + model + '\'' +
               ", manufacturersId=" + manufacturersId +
               ", year=" + year +
               ", engineCc=" + engineCc +
               ", price=" + price +
               ", quantity=" + quantity +
               '}';
    }
}
