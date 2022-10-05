package service;

import exception.UserExceptionMessage;
import exception.UserException;
import entity.User;
import repository.UserH2Repository;
import repository.UserRepository;
import validator.UserValidator;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class UserService {
    private static UserRepository userRepository = UserH2Repository.getInstance();

    private UserService() {}

    private static class UserServiceHolder {
        public static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return UserService.UserServiceHolder.INSTANCE;
    }

    public User addUser(Map<String, String> params) {
        if (!userRepository.findUserByUserId(params.get("userId")).isEmpty()) {
            throw new UserException(UserExceptionMessage.DUPLICATE_USER);
        }
        UserValidator.validateUser(params);
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        userRepository.addUser(user);
        return user;
    }

    public User getUser(String id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UserException(UserExceptionMessage.UNSIGNED_USER));
    }

    public User login(Map<String, String> params) {
        Optional<User> user = userRepository.findUserByUserId(params.get("userId"));
        if (userRepository.findUserByUserId(params.get("userId")).isEmpty() || !user.get().getPassword().equals(params.get("password"))) {
            throw new UserException(UserExceptionMessage.UNSIGNED_USER);
        }

        return user.get();
    }

    public Collection<User> getUserList() {
        return userRepository.findAll();
    }
}
