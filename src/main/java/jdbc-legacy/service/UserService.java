package jdbc.service;

import jdbc.dao.UserDao;
import jdbc.dto.CreateUserDto;
import jdbc.dto.UserDto;
import jdbc.exception.ValidationException;
import jdbc.mapper.CreateUserMapper;
import jdbc.mapper.UserMapper;
import jdbc.validator.CreateUserValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();

    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getINSTANCE();

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPasssword(email, password).map(userMapper::mapFrom);
    }


    public Integer create(CreateUserDto createUserDto) {
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);
        return user.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
