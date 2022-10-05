package service;

import entity.Memo;
import repository.MemoH2Repository;
import repository.MemoRepository;

import java.util.Collection;

public class MemoService {
    private MemoRepository memoRepository = MemoH2Repository.getInstance();

    private MemoService() {}

    private static class MemoServiceHolder {
        public static final MemoService INSTANCE = new MemoService();
    }

    public static MemoService getInstance() {
        return MemoService.MemoServiceHolder.INSTANCE;
    }

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
