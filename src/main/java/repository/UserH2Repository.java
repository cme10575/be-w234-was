package repository;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserH2Repository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserH2Repository.class);
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("was");

    private UserH2Repository() {}

    private static class UserH2RepositoryHolder {
        public static final UserH2Repository INSTANCE = new UserH2Repository();
    }

    public static UserH2Repository getInstance() {
        return UserH2Repository.UserH2RepositoryHolder.INSTANCE;
    }


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
        return Optional.ofNullable(user);
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
            Query query = em.createQuery("DELETE FROM User");
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            logger.debug("user clear failed: " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
