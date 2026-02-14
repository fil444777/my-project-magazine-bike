package jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomersDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
}
