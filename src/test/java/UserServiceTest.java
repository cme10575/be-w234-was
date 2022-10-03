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
    UserService userService = new UserService();
    UserRepository userRepository = new UserH2Repository();
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


    @BeforeEach
    void initUsers() {
        //System.out.println("init User");
        userRepository.clearAll();
        System.out.println("init User: " + userRepository.findAll());
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
        System.out.println("find All: " + userRepository.findAll());
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
}
