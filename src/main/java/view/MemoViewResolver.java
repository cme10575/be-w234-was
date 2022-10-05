package view;

import entity.Memo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

public class MemoViewResolver {

    private MemoViewResolver() {}

    private static class MemoViewResolverHolder {
        public static final MemoViewResolver INSTANCE = new MemoViewResolver();
    }

    public static MemoViewResolver getInstance() {
        return MemoViewResolver.MemoViewResolverHolder.INSTANCE;
    }


    public String getMemoListHtml(Collection<Memo> memoList) throws IOException {
        String html = Files.readString(Path.of("./webapp/index.html"));
        StringBuilder stringBuilder = new StringBuilder(html);
        return stringBuilder.insert(stringBuilder.lastIndexOf("<ul class=\"list\">"), getMemoRecords(memoList)).toString();
    }

    private String getMemoRecords(Collection<Memo> memoList) {
        String userRecordFormat = "<li>\n" +
                "                  <div class=\"wrap\">\n" +
                "                      <div class=\"main\">\n" +
                "                          <strong class=\"subject\">\n" +
                "                              <a href=\"./qna/show.html\">%s</a>\n" +
                "                          </strong>\n" +
                "                          <div class=\"auth-info\">\n" +
                "                              <i class=\"icon-add-comment\"></i>\n" +
                "                              <span class=\"time\">2016-01-15 18:47</span>\n" +
                "                              <a href=\"./user/profile.html\" class=\"author\">%s</a>\n" +
                "                          </div>\n" +
                "                          <div class=\"reply\" title=\"댓글\">\n" +
                "                              <i class=\"icon-reply\"></i>\n" +
                "                              <span class=\"point\">%d</span>\n" +
                "                          </div>\n" +
                "                      </div>\n" +
                "                  </div>\n" +
                "              </li>";

        return memoList.stream()
                .map(memo -> String.format(
                        userRecordFormat,
                        memo.getContent(),
                        memo.getAuthor(),
                        memo.getId()
                ))
                .collect(Collectors.joining("\n"));
    }
}
