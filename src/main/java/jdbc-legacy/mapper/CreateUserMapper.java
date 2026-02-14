package jdbc.mapper;

import jdbc.dto.CreateUserDto;
import jdbc.entity.Gender;
import jdbc.entity.Role;
import jdbc.entity.User;
import jdbc.utils.LocalDateFormatter;

public class CreateUserMapper implements Mapper<User, CreateUserDto>{
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    private CreateUserMapper() {
    }

    @Override
    public User mapFrom(CreateUserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .birthday(LocalDateFormatter.format(userDto.getBirthday()))
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .gender(Gender.valueOf(userDto.getGender()))
                .role(Role.valueOf(userDto.getRole()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
