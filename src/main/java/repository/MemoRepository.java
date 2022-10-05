package repository;

import entity.Memo;

import java.util.Collection;

public interface MemoRepository {
    void save(Memo memo);
    Collection<Memo> findAll();
    public void clearAll();
}
