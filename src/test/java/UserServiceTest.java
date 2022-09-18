import exception.UserErrorMessage;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.UserService;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {
    UserService userService = new UserService();

    @BeforeEach
    void initUsers() {
        userService.getUsers().clear();
    }

    @Test
    @DisplayName("유저 생성 테스트")
    void createUser() {
        User user = userService.addUser(Map.ofEntries(
                Map.entry("userId", "javajigi"),
                Map.entry("password", "password"),
                Map.entry("name", "박재성"),
                Map.entry("email", "javajigi@slipp.net")));
        assertThat(userService.getUsers().contains(user));
    }

    @Test
    @DisplayName("중복된 유저 생성 안 되어야 함")
    void createDuplicateUser() {
        userService.addUser(Map.ofEntries(
                Map.entry("userId", "javajigi"),
                Map.entry("password", "password"),
                Map.entry("name", "박재성"),
                Map.entry("email", "javajigi@slipp.net")));
        assertThatThrownBy(() -> {
            userService.addUser(Map.ofEntries(
                    Map.entry("userId", "javajigi"),
                    Map.entry("password", "password"),
                    Map.entry("name", "박재성"),
                    Map.entry("email", "javajigi@slipp.net")));
        }).isInstanceOf(RuntimeException.class).hasMessageContaining(UserErrorMessage.DUPLICATE_USER.getMessage());
    }
}
