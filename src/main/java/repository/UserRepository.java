package repository;

import entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    public void addUser(User user);
    public Optional<User> findUserByUserId(String userId);
    public Optional<User> findUserById(String Id);
    public Collection<User> findAll();
    public void clearAll();
}
