package service;

import repository.Database;
import exception.UserErrorMessage;
import exception.UserException;
import entity.User;
import validator.UserValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserService { //todo: 싱글톤으로 변경

    public UserService() {}

    public User addUser(Map<String, String> params) {
        if (Database.findUserById(params.get("userId")) != null) {
            throw new UserException(UserErrorMessage.DUPLICATE_USER);
        }
        UserValidator.validateUser(params);
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        Database.addUser(user);
        return user;
    }

    public User login(Map<String, String> params) {
        User user = Database.findUserById(params.get("userId"));
        if (user == null || !user.getPassword().equals(params.get("password"))) {
            throw new UserException(UserErrorMessage.UNSIGNED_USER);
        }

        return user;
    }

    public Collection<User> getUserList() {
        return Database.findAll();
    }
}
