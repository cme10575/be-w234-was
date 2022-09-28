package repository;

import entity.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;

public class MemoH2Repository implements MemoRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoH2Repository.class);
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("was");

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
}
