import entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.UserViewResolver;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserViewResolverTest {
    private UserViewResolver userViewResolver = new UserViewResolver();

    @Test
    @DisplayName("메모 리스트가 html파일에 동적으로 매핑 되는지 확인")
    void userListViewMappingTest() throws IOException {
        String userId = "modi.fy";
        User user = new User(userId, "password!234", "name", "email");

        String html = userViewResolver.getUserListHtml(List.of(user));
        assertThat(html.contains("<td>" + userId + "</td>"));
    }
}
