package junction.oreo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import junction.oreo.dto.CreateFeedDto;
import junction.oreo.dto.FeedDto;
import junction.oreo.dto.MemberDto;
import junction.oreo.dto.UpdateFeedDto;
import junction.oreo.entity.Member;
import junction.oreo.enums.CategoryType;
import junction.oreo.enums.response.CodeEnum;
import junction.oreo.request.CreateFeedRequest;
import junction.oreo.request.UpdateFeedRequest;
import junction.oreo.response.CommonResponse;
import junction.oreo.service.FeedService;
import junction.oreo.service.mock.FeedMockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final FeedMockService feedMockService;

    @Operation(summary = "Feed 조회",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "X-MID",
                            description = "mid",
                            required = true,
                            schema = @Schema(type = "string"))}
    )
    @GetMapping("/{category}")
    public CommonResponse getFeeds(@PathVariable String category,
                                   @RequestParam String startDate,
                                   @RequestParam String endDate,
                                   Member member) {
        MemberDto memberDto = MemberDto.builder()
                                       .member(member)
                                       .build();
        FeedDto feedDto = feedService.getFeed(memberDto,
                                              CategoryType.valueByName(category.toUpperCase()),
                                              startDate, endDate);

        return CommonResponse.builder()
                             .returnCode(CodeEnum.SUCCESS.getCode())
                             .info(feedDto)
                             .build();
    }

    @Operation(summary = "Feed 수정",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "X-MID",
                            description = "mid",
                            required = true,
                            schema = @Schema(type = "string"))}
    )
    @PutMapping
    public CommonResponse updateFeed(@RequestBody UpdateFeedRequest request, Member member) {
        Long feedId = request.getFeedId();
        boolean status = request.isStatus();
        String mid = member.getMid();
        UpdateFeedDto updateFeedDto = UpdateFeedDto.builder()
                                                   .member(member)
                                                   .feedId(feedId)
                                                   .status(status)
                                                   .build();

        log.info("[Feed] update feed. (mid : {}, feedId : {}, status : {} -> {})",
                 mid, feedId, !status, status);

        feedService.updateFeed(updateFeedDto);

        return CommonResponse.builder()
                             .returnCode(CodeEnum.SUCCESS.getCode())
                             .build();
    }

    @PostMapping
    public CommonResponse createFeed(@RequestBody CreateFeedRequest request, Member member) {
        feedService.createFeed(CreateFeedDto.builder()
                                            .mid(member.getMid())
                                            .text(request.getText())
                                            .build());
        return CommonResponse.success();
    }

    @GetMapping("/mock")
    public void mock() {
        feedMockService.mock();
    }
}
