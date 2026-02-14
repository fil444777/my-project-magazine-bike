package jdbc.mapper;

import jdbc.dto.UserDto;
import jdbc.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<UserDto, User> {
    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .build();
    }

    public static UserMapper getINSTANCE() {
        return INSTANCE;
    }
}