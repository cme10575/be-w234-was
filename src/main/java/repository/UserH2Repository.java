package repository;

import entity.User;
import exception.UserErrorMessage;
import exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserH2Repository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserH2Repository.class);
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("was");

    @Override
    public void addUser(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        EntityManager em = emf.createEntityManager();
        List<User> user = em.createQuery("SELECT t FROM User t where t.userId = :value1", User.class)
                .setParameter("value1", userId).getResultList();
        em.close();
        return user.stream().findAny();
    }

    @Override
    public Optional<User> findUserById(String id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, Long.parseLong(id));
        if (user == null)
            throw new UserException(UserErrorMessage.UNSIGNED_USER);
        return Optional.of(user);
    }

    @Override
    public Collection<User> findAll() {
        EntityManager em = emf.createEntityManager();
        Collection<User> users = em.createQuery("SELECT t FROM User t", User.class).getResultList();
        em.close();
        return users;
    }

    @Override
    public void clearAll() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Collection<User> users = em.createQuery("SELECT t FROM User t", User.class).getResultList();
            users.clear();
            tx.commit();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
