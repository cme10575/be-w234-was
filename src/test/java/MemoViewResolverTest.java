import entity.Memo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.MemoViewResolver;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoViewResolverTest {

    private MemoViewResolver memoViewResolver = MemoViewResolver.getInstance();

    @Test
    @DisplayName("메모 리스트가 html파일에 동적으로 매핑 되는지 확인")
    void memoListViewMappingTest() throws IOException {
        String content = "메모 테스트";
        String author = "modi.fy";
        Memo memo = new Memo();
        memo.setContent(content);
        memo.setAuthor(author);

        String html = memoViewResolver.getMemoListHtml(List.of(memo));
        assertThat(html.contains("<a href=\"./qna/show.html\">" + content + "</a>"));
    }
}