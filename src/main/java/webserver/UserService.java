package webserver;

import exception.UserErrorMessage;
import model.User;

import java.util.ArrayList;
import java.util.Map;

public class UserService {
    private ArrayList<User> users;

    public UserService() {
        users = new ArrayList<>();
    }

    public User addUser(Map<String, String> params) {
        for (User user: users) {
            if (user.getUserId().equals(params.get("userId"))) {
                throw new RuntimeException(UserErrorMessage.DUPLICATE_USER.getMessage());
            }
        }

        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        users.add(user);
        return user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
