package jdbc.dto;

import jdbc.entity.Gender;
import jdbc.entity.Role;
import lombok.*;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Long id;
    String name;
    LocalDate birthday;
    String email;
    Role role;
    Gender gender;
}