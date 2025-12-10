package jdbc.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class MotorcyclesDto {
    private Integer id;
    private String model;
    private Integer year;
    private BigDecimal price;

    public MotorcyclesDto(Integer id, String model, Integer year, BigDecimal price) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.price = price;
    }
}
