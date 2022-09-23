package Service;

import db.Database;
import exception.UserErrorMessage;
import model.User;

import java.util.Map;

public class UserService { //todo: 싱글톤으로 변경

    public UserService() {}

    public User addUser(Map<String, String> params) {
        if (Database.findUserById(params.get("userId")) != null) {
            throw new RuntimeException(UserErrorMessage.DUPLICATE_USER.getMessage());
        }

        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        Database.addUser(user);
        return user;
    }

    public User login(Map<String, String> params) {
        User user = Database.findUserById(params.get("userId"));
        if (user == null || !user.getPassword().equals(params.get("password"))) {
            throw new RuntimeException(UserErrorMessage.UNSIGNED_USER.getMessage());
        }

        return user;
    }
}
