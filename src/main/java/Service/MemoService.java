package service;

import entity.Memo;
import entity.User;
import exception.UserErrorMessage;
import exception.UserException;
import repository.MemoH2Repository;
import repository.MemoRepository;
import validator.UserValidator;

import java.util.Map;

public class MemoService {
    private static MemoRepository memoRepository = new MemoH2Repository();

    public void addMemo(String content) {
        Memo memo = new Memo();
        memo.setAuthor("temp");
        memo.setContent(content);
        memoRepository.save(memo);
        return;
    }

}
