package service;

import entity.Memo;
import repository.MemoH2Repository;
import repository.MemoRepository;

import java.util.Collection;

public class MemoService {
    private static MemoRepository memoRepository = new MemoH2Repository();
    public void addMemo(String content) {
        Memo memo = new Memo();
        memo.setAuthor("temp");
        memo.setContent(content);
        memoRepository.save(memo);
        return;
    }

    public Collection<Memo> getMemoList() {
        return memoRepository.findAll();
    }
}
