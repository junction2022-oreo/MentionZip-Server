package junction.oreo.service.mock;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import junction.oreo.entity.Feed;
import junction.oreo.entity.Member;
import junction.oreo.enums.CategoryType;
import junction.oreo.repositories.FeedRepository;
import junction.oreo.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedMockService {
    private final String mockImgUrl =
            "https://mentionzipstorage.blob.core.windows.net/newcontainer/hana.jpeg";

    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;

    public void mock() {
        Member member = Member.builder()
                              .mid("7777")
                              .name("hana")
                              .imgUrl(mockImgUrl)
                              .build();
        saveMockMember(member);

        List<Feed> feedsMock = getFeedMock(member);

        feedRepository.saveAll(feedsMock);
    }

    private void saveMockMember(Member memeber) {
        memberRepository.save(memeber);
    }

    private List<Feed> getFeedMock(Member member) {
        Feed slack1 = Feed.builder()
                          .category(CategoryType.SLACK)
                          .writeDate(LocalDateTime.now())
                          .text("SLACK 1")
                          .member(member)
                          .status(true)
                          .subject("iOS App Desing QA")
                          .build();

        Feed slack2 = Feed.builder()
                          .category(CategoryType.SLACK)
                          .writeDate(LocalDateTime.now())
                          .text("SLACK 2")
                          .member(member)
                          .status(false)
                          .subject("iOS App Desing QA")
                          .build();

        Feed jira1 = Feed.builder()
                         .category(CategoryType.JIRA)
                         .writeDate(LocalDateTime.now())
                         .text("JIRA 1")
                         .member(member)
                         .status(true)
                         .subject("iOS App Desing QA")
                         .build();

        Feed jira2 = Feed.builder()
                         .category(CategoryType.JIRA)
                         .writeDate(LocalDateTime.now())
                         .text("JIRA 2")
                         .member(member)
                         .status(false)
                         .subject("iOS App Desing QA")
                         .build();

        return List.of(slack1, slack2, jira1, jira2);
    }
}
