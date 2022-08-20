package junction.oreo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FeedDto {
    private List<FeedList> todoList;
    private List<FeedList> doneList;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class FeedList {
        private String name;
        private String imgUrl;
        private Long feedId;
        private String writeDate;
        private String text;
        private boolean isCheck;
        private String subject;
    }
}
