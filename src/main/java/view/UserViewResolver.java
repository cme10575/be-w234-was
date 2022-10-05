package view;

import entity.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UserViewResolver {
    private UserViewResolver() {}

    private static class UserViewResolverHolder {
        public static final UserViewResolver INSTANCE = new UserViewResolver();
    }

    public static UserViewResolver getInstance() {
        return UserViewResolver.UserViewResolverHolder.INSTANCE;
    }
    
    String userRecordFormat = "<tr><th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>";

    public String getUserListHtml(Collection<User> userList) throws IOException {
        String html = Files.readString(Path.of("./webapp/user/list.html"));
        StringBuilder stringBuilder = new StringBuilder(html);
        return stringBuilder.insert(stringBuilder.lastIndexOf("</tbody>"), getUserRecords(userList)).toString();
    }

    private String getUserRecords(Collection<User> userList) {
        AtomicInteger index = new AtomicInteger(3);
        return userList.stream()
                .map(user -> String.format(
                        userRecordFormat,
                        index.getAndIncrement(),
                        user.getUserId(),
                        user.getName(),
                        user.getEmail()
                ))
                .collect(Collectors.joining("\n"));
    }
}
