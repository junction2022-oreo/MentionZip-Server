package junction.oreo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junction.oreo.dto.CreateFeedDto;
import junction.oreo.dto.FeedDto;
import junction.oreo.dto.FeedDto.FeedList;
import junction.oreo.dto.MemberDto;
import junction.oreo.dto.UpdateFeedDto;
import junction.oreo.entity.Feed;
import junction.oreo.entity.Member;
import junction.oreo.enums.CategoryType;
import junction.oreo.enums.response.CodeEnum;
import junction.oreo.exception.CustomException;
import junction.oreo.repositories.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    @Transactional(readOnly = true)
    public FeedDto getFeed(MemberDto memberDto, CategoryType categoryType,
                           String startDate, String endDate) {
        Member member = memberDto.getMember();
        List<Feed> feeds = feedRepository.findByMemberAndCategoryAndWriteDateIsBetween(
                member, categoryType,
                getLocalDatetimeFromString(startDate),
                getLocalDatetimeFromString(endDate));

        List<FeedList> feedLists = getAllFeed(member, feeds);
        return FeedDto.builder()
                      .todoList(getTodoList(feedLists))
                      .doneList(getDoneList(feedLists))
                      .build();
    }

    private List<FeedList> getAllFeed(Member member, List<Feed> feeds) {
        return feeds.stream()
                    .map(i -> FeedList.builder()
                                      .writeDate(i.getWriteDate())
                                      .feedId(i.getId())
                                      .text(i.getText())
                                      .status(i.isStatus())
                                      .subject(i.getSubject())
                                      .name(member.getName())
                                      .imgUrl(member.getImgUrl())
                                      .build()
                    )
                    .collect(Collectors.toList());
    }

    @Transactional
    public void updateFeed(UpdateFeedDto updateFeedDto) {
        Optional<Feed> feedOtp = feedRepository.findById(updateFeedDto.getFeedId());
        if (feedOtp.isEmpty()) {
            log.error("[updateFeed] Cannot find `feed`. (feedId : {})", updateFeedDto.getFeedId());
            throw new CustomException(CodeEnum.NOT_EXIST_FEED);
        }

        Feed feed = feedOtp.get();
        feed.setStatus(updateFeedDto.isStatus());
    }

    @Transactional
    public void createFeed(CreateFeedDto createFeedDto) {
        Feed feed = Feed.builder()
                        .build();
        feedRepository.save(feed);
    }

    private List<FeedList> getTodoList(List<FeedList> feedLists) {
        return feedLists.stream()
                        .filter(i -> i.isStatus() == false)
                        .collect(Collectors.toList());
    }

    private List<FeedList> getDoneList(List<FeedList> feedLists) {
        return feedLists.stream()
                        .filter(i -> i.isStatus() == true)
                        .collect(Collectors.toList());
    }

    private LocalDateTime getLocalDatetimeFromString(String yyyyMMdd) {
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate ld = LocalDate.parse(yyyyMMdd, DATEFORMATTER);
        return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
    }
}
