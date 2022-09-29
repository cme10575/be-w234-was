package service;

import entity.Memo;
import repository.MemoH2Repository;
import repository.MemoRepository;

import java.util.Collection;

public class MemoService {
    private static MemoRepository memoRepository = new MemoH2Repository();
    public void addMemo(String content, String author) {
        Memo memo = new Memo();
        memo.setAuthor(author);
        memo.setContent(content);
        memoRepository.save(memo);
        return;
    }

    public Collection<Memo> getMemoList() {
        return memoRepository.findAll();
    }
}
