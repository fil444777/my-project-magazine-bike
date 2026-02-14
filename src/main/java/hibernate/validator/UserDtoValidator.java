package hibernate.validator;


import hibernate.dto.UserDto;
import hibernate.entity.Gender;
import hibernate.entity.Role;
import hibernate.utils.LocalDateFormatter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class UserDtoValidator implements Validator<UserDto> {
    private static final UserDtoValidator INSTANCE = new UserDtoValidator();

    @Override
    public ValidationResult isValid(UserDto userDto) {
        var validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
            log.debug("Validator failed: invalid birthday '{}'", userDto.getBirthday());
        }
        if (Gender.find(userDto.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
            log.debug("Validation failed: invalid gender '{}'", userDto.getGender());
        }
        if (Role.find(userDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
            log.debug("Validation failed: invalid role '{}'", userDto.getRole());
        }
        if (userDto.getName().isEmpty()) {
            validationResult.add(Error.of("invalid.name", "Name is invalid"));
            log.debug("Validation failed: empty name");
        }
        if (userDto.getEmail().isEmpty()) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
            log.debug("Validation failed: empty email");
        }
        if (userDto.getPassword().isEmpty()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
            log.debug("Validation failed: empty password");
        }

        if (!validationResult.isValid()) {
            log.warn("Validation failed for user registration. Errors: {}",
                    validationResult.getErrors().stream()
                            .map(Error::getCode)
                            .collect(Collectors.toList()));
        }
        return validationResult;
    }


    public static UserDtoValidator getInstance() {
        return INSTANCE;
    }
}
