import entity.Memo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.MemoH2Repository;
import repository.MemoRepository;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class MemoRepositoryTest {

    private MemoRepository memoRepository = MemoH2Repository.getInstance();

    @Test
    @DisplayName("메모 저장 테스트")
    void saveMemo() {
        String content = "메모 테스트";
        String author = "modi.fy";
        Memo memo = new Memo();
        memo.setContent(content);
        memo.setAuthor(author);

        memoRepository.save(memo);
        Collection<Memo> memos = memoRepository.findAll()
                .stream()
                .filter((m) -> m.getAuthor().equals(author) && m.getContent().equals(content))
                .collect(Collectors.toList());
        assertThat(!memos.isEmpty());
    }
}