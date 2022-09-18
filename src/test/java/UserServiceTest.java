import exception.UserErrorMessage;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.UserService;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {
    UserService userService = new UserService();

    @BeforeEach
    void initUsers() {
        userService.getUsers().clear();
    }

    private static Stream<Arguments> userParameters() {
        return Stream.of(
                Arguments.of(Map.ofEntries(
                        Map.entry("userId", "javajigi"),
                        Map.entry("password", "password"),
                        Map.entry("name", "박재성"),
                        Map.entry("email", "javajigi@slipp.net"))),
                Arguments.of(Map.ofEntries(
                        Map.entry("userId", "sujeong"),
                        Map.entry("password", "password"),
                        Map.entry("name", "김수정"),
                        Map.entry("email", "sujeong@slipp.net")))
        );
    }

    @DisplayName("유저 생성 테스트")
    @ParameterizedTest
    @MethodSource("userParameters")
    void createUser(Map<String, String> userInfo) {
        User user = userService.addUser(userInfo);
        assertThat(userService.getUsers().contains(user));
    }

    @DisplayName("중복된 유저 생성 안 되어야 함")
    @ParameterizedTest
    @MethodSource("userParameters")
    void createDuplicateUser(Map<String, String> userInfo) {
        userService.addUser(userInfo);
        assertThatThrownBy(() -> {
            userService.addUser(userInfo);
        }).isInstanceOf(RuntimeException.class).hasMessageContaining(UserErrorMessage.DUPLICATE_USER.getMessage());
    }
}
