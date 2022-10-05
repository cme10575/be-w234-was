import exception.UserExceptionMessage;
import exception.UserException;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import repository.UserH2Repository;
import repository.UserRepository;
import service.UserService;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {
    UserService userService = UserService.getInstance();
    UserRepository userRepository = UserH2Repository.getInstance();
    static Map<String, String> userInfo1 = Map.ofEntries(
            Map.entry("userId", "javajigi"),
            Map.entry("password", "password!1234"),
            Map.entry("name", "박재성"),
            Map.entry("email", "javajigi@slipp.net"));
    static Map<String, String> userInfo2 = Map.ofEntries(
            Map.entry("userId", "sujeong"),
            Map.entry("password", "password!1234"),
            Map.entry("name", "김수정"),
            Map.entry("email", "sujeong@slipp.net"));

    Map<String, String> userInfo3 = Map.ofEntries(
            Map.entry("userId", "s"),
            Map.entry("password", "password!1234"),
            Map.entry("name", "김수정"),
            Map.entry("email", "ssujeong@slipp.net"));

    Map<String, String> userInfo4 = Map.ofEntries(
            Map.entry("userId", "rlatnwjd"),
            Map.entry("password", "password"),
            Map.entry("name", "김수정"),
            Map.entry("email", "ssujeong@slipp.net"));

    Map<String, String> userInfo5 = Map.ofEntries(
            Map.entry("userId", "rlatnwjd1"),
            Map.entry("password", "password11111!"),
            Map.entry("name", "김수정"),
            Map.entry("email", "abcd"));

    @BeforeEach
    void initUsers() {
        userRepository.clearAll();
    }

    private static Stream<Arguments> userParameters() {
        return Stream.of(
                Arguments.of(userInfo1),
                Arguments.of(userInfo2)
        );
    }

    @Test
    @DisplayName("유저 생성 테스트")
    void createUser() {
        User user = userService.addUser(userInfo1);
        assertThat(userRepository.findAll().contains(user));
    }

    @DisplayName("중복된 유저 생성 안 되어야 함")
    @ParameterizedTest
    @MethodSource("userParameters")
    void createDuplicateUser(Map<String, String> userInfo) {
        userService.addUser(userInfo);
        assertThatThrownBy(() -> {
            userService.addUser(userInfo);
        }).isInstanceOf(UserException.class).hasMessageContaining(UserExceptionMessage.DUPLICATE_USER.getMessage());
    }

    @Test
    @DisplayName("잘못된 아이디 형식 유저 생성 안 되어야 함")
    void invalidIdTypeUser() {
        assertThatThrownBy(() -> {
            userService.addUser(userInfo3);
        }).isInstanceOf(UserException.class).hasMessageContaining(UserExceptionMessage.INVALID_ID_TYPE.getMessage());
    }

    @Test
    @DisplayName("잘못된 패스워드 형식 유저 생성 안 되어야 함")
    void invalidPasswordTypeUser() {
        assertThatThrownBy(() -> {
            userService.addUser(userInfo4);
        }).isInstanceOf(UserException.class).hasMessageContaining(UserExceptionMessage.INVALID_PASSWORD_TYPE.getMessage());
    }

    @Test
    @DisplayName("잘못된 이메일 형식 유저 생성 안 되어야 함")
    void invalidEmailTypeUser() {
        assertThatThrownBy(() -> {
            userService.addUser(userInfo5);
        }).isInstanceOf(UserException.class).hasMessageContaining(UserExceptionMessage.INVALID_EMAIL_TYPE.getMessage());
    }
}
