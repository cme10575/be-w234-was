package repository;

import entity.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Collection;

public class MemoH2Repository implements MemoRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoH2Repository.class);
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("was");

    private MemoH2Repository() {}

    private static class MemoH2RepositoryHolder {
        public static final MemoH2Repository INSTANCE = new MemoH2Repository();
    }

    public static MemoH2Repository getInstance() {
        return MemoH2Repository.MemoH2RepositoryHolder.INSTANCE;
    }


    @Override
    public void save(Memo memo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.persist(memo);
            tx.commit();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Memo> findAll() {
        EntityManager em = emf.createEntityManager();
        Collection<Memo> memos = em.createQuery("SELECT t FROM Memo t", Memo.class).getResultList();
        em.close();
        return memos;
    }

    @Override
    public void clearAll() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Query query = em.createQuery("DELETE FROM Memo");
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            logger.debug("memo clear failed: " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
