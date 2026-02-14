package hibernate.service;


import hibernate.dao.UserDao;
import hibernate.dto.UserDto;
import hibernate.entity.User;
import hibernate.exception.ValidationException;
import hibernate.mapper.UserDtoMapper;
import hibernate.validator.UserDtoValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


@Slf4j
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private static final UserDtoMapper userDtoMapper = new UserDtoMapper();

    private static final UserDao userDao = UserDao.getInstance();
    private static final UserDtoValidator userDtoValidator = UserDtoValidator.getInstance();

    public Optional<User> login(String email, String password) {
        log.info("Login attempt for email: {}", email);

        var user = userDao.findByEmailAndPassword(email, password);

        if (user.isPresent()) {
            log.info("User '{}' successfully logged in", email);
        } else log.warn("Login failed for email: {}", email);

        return user;
    }


    public Integer create(UserDto userDto) {
        log.info("User Registration Attempt: {}", userDto.getEmail());

        var validationResult = userDtoValidator.isValid(userDto);

        if (!validationResult.isValid()) {

            log.warn("Validation failed for email: {} : {}",
                    userDto.getEmail(),
                    validationResult.getErrors());
            throw new ValidationException(validationResult.getErrors());
        }

        var user = userDtoMapper.mapFrom(userDto);
        userDao.save(user);
        log.info("User {} successfully registered",
                userDto.getEmail());
        return user.getId();
    }

    public List<User> findAll() {
        log.debug("Loading all users");

        List<User> users = userDao.findAll();

        log.debug("Loaded {} users", users);

        return users;
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
