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

    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;

    public void mock() {
        Member hana = Member.builder()
                            .mid("1")
                            .name("Hana")
                            .imgUrl("https://mentionzipstorage.blob.core.windows.net/newcontainer/hana.jpeg")
                            .build();
        Member yangwon = Member.builder()
                               .mid("2")
                               .name("Yangwon")
                               .imgUrl("https://mentionzipstorage.blob.core.windows.net/newcontainer/yangwon.png")
                               .build();
        Member seungmi = Member.builder()
                               .mid("3")
                               .name("Seungmi")
                               .imgUrl("https://mentionzipstorage.blob.core.windows.net/newcontainer/seungmi.png")
                               .build();
        Member juyoung = Member.builder()
                               .mid("4")
                               .name("Juyoung")
                               .imgUrl("https://mentionzipstorage.blob.core.windows.net/newcontainer/juyoung.jpeg")
                               .build();
        Member kiyong = Member.builder()
                               .mid("5")
                               .name("Kiyong")
                               .imgUrl("https://mentionzipstorage.blob.core.windows.net/newcontainer/juyoung.jpeg")
                               .build();
        List<Member> membersMock = List.of(hana, yangwon, seungmi, juyoung, kiyong);
        saveMockMember(membersMock);

        saveSlackMock(membersMock);
        saveJiraMock(membersMock);
        saveOutlookMock(membersMock);
    }

    private void saveMockMember(List<Member> membersMock) {
        memberRepository.saveAll(membersMock);
    }

    private void saveSlackMock(List<Member> membersMock) {
        Member hana = membersMock.get(0);
        Member yangwon = membersMock.get(1);
        Member seungmi = membersMock.get(2);
        Member juyoung = membersMock.get(3);
        Member kiyong = membersMock.get(4);

        Feed slack1 = Feed.builder()
                          .category(CategoryType.SLACK)
                          .writeDate(LocalDateTime.now())
                          .text("기드 안녕하세요! 1005 배포인 앱 설정 디자인 피그마 완료되었습니다! 내용 wiki에도 반영하였습니다. 참고 부탁드립니다~")
                          .member(hana)
                          .hostId(5L)
                          .status(true)
                          .subject("8월 Design&Dev 채널")
                          .build();

        Feed slack2 = Feed.builder()
                          .category(CategoryType.SLACK)
                          .writeDate(LocalDateTime.now())
                          .text("기드 안녕하세요~ 설성지면 개선  관련하여 월요일에 리뷰 드렸고 이번주 금요일까지 검토해주시기로 하셨어요~ 리마인드 드립니다!  혹~~시 관련하여 미팅콜 필요하시다면 저랑 주영님 초대 부탁드립니다~!")
                          .member(yangwon)
                          .hostId(5L)
                          .status(true)
                          .subject("8월 배포 통합 채널")
                          .build();

        Feed slack3 = Feed.builder()
                          .category(CategoryType.SLACK)
                          .writeDate(LocalDateTime.now())
                          .text("기드 디자인 버그 티켓이 나왔는데 혹시 확인해주실 수 있으실까요?? 바쁘시겠지만 이번주 목요일까지 확인 부탁드립니다!")
                          .member(seungmi)
                          .hostId(5L)
                          .status(false)
                          .subject("7월 배포 통합 채널")
                          .build();

        Feed slack4 = Feed.builder()
                          .category(CategoryType.SLACK)
                          .writeDate(LocalDateTime.now())
                          .text("기드  전달해주신 서버 API 관련하여 협의가 필요합니다! 혹시 잠시 티탐 가능하실까요?")
                          .member(juyoung)
                          .hostId(5L)
                          .status(false)
                          .subject("8월 배포 통합 채널")
                          .build();

        Feed slack5 = Feed.builder()
                          .category(CategoryType.SLACK)
                          .writeDate(LocalDateTime.now())
                          .text("방금 전 논의한 To do/ done  애니메이션 회의록 공유드립니다!")
                          .member(yangwon)
                          .hostId(5L)
                          .status(false)
                          .subject("8월 Design&Dev 채널")
                          .build();

        feedRepository.saveAll(List.of(slack1, slack2, slack3, slack4, slack5));
    }

    private void saveJiraMock(List<Member> membersMock) {
        Member hana = membersMock.get(0);
        Member yangwon = membersMock.get(1);
        Member seungmi = membersMock.get(2);
        Member juyoung = membersMock.get(3);
        Member kiyong = membersMock.get(4);

        Feed jira1 = Feed.builder()
                         .category(CategoryType.JIRA)
                         .writeDate(LocalDateTime.now())
                         .text("헤더 값 불일치 건 . 아래 내용 참고해주세요")
                         .member(seungmi)
                         .hostId(5L)
                         .status(false)
                         .subject("7월 배포 디자인QA")
                         .build();

        Feed jira2 = Feed.builder()
                         .category(CategoryType.JIRA)
                         .writeDate(LocalDateTime.now())
                         .text("작업 범위 (협의 필요)  및 일정 (API 문서: 08/19 , 베타 배포: 08/20)")
                         .member(hana)
                         .hostId(5L)
                         .status(true)
                         .subject("서버_7월배포_작업티켓")
                         .build();

        Feed jira3 = Feed.builder()
                         .category(CategoryType.JIRA)
                         .writeDate(LocalDateTime.now())
                         .text("서버 API 협의 필요한 내용")
                         .member(juyoung)
                         .hostId(5L)
                         .status(false)
                         .subject("프론트_8월배포_작업티켓")
                         .build();

        Feed jira4 = Feed.builder()
                         .category(CategoryType.JIRA)
                         .writeDate(LocalDateTime.now())
                         .text("프론트에서 토큰 검토 완료, 서버에서 아래 내용에 대해 의견 필요합니다. ")
                         .member(yangwon)
                         .hostId(5L)
                         .status(true)
                         .subject("개발검토_SSO_작업티켓")
                         .build();

        feedRepository.saveAll(List.of(jira1, jira2, jira3, jira4));
    }

    private void saveOutlookMock(List<Member> membersMock) {
        Member hana = membersMock.get(0);
        Member yangwon = membersMock.get(1);
        Member seungmi = membersMock.get(2);
        Member juyoung = membersMock.get(3);
        Member kiyong = membersMock.get(4);

        Feed outlook1 = Feed.builder()
                            .category(CategoryType.OUTLOOK)
                            .writeDate(LocalDateTime.now())
                            .text("안녕하세요, 오레오팀 김하나입니다. 10월 정기배포 리뷰드릴 예정이오니 아래 내용 참고 부탁드립니다.")
                            .member(hana)
                            .hostId(5L)
                            .status(true)
                            .subject("[10월 정기배포] 기획 리뷰 안내")
                            .build();

        Feed outlook2 = Feed.builder()
                            .category(CategoryType.OUTLOOK)
                            .writeDate(LocalDateTime.now())
                            .text("오레오팀 프론트엔드 김양원입니다. API 개선 작업 안내 드립니다. ")
                            .member(yangwon)
                            .hostId(5L)
                            .status(true)
                            .subject("API 개선 프로젝트 공유")
                            .build();

        Feed outlook3 = Feed.builder()
                            .category(CategoryType.OUTLOOK)
                            .writeDate(LocalDateTime.now())
                            .text("안녕하세요, 지난 달 진행하였던 통합 UX 관련하여 사용자 피드백 공유드립니다. ")
                            .member(seungmi)
                            .hostId(5L)
                            .status(false)
                            .subject("통합 UX 리포트 공유")
                            .build();

        Feed outlook4 = Feed.builder()
                            .category(CategoryType.OUTLOOK)
                            .writeDate(LocalDateTime.now())
                            .text("프론트엔드 박주영입니다. 로그인 정책 개선이 필요하여 아래 내용으로 과제 요청드립니다. ")
                            .member(juyoung)
                            .hostId(5L)
                            .status(false)
                            .subject("로그인 정책 개선 공유")
                            .build();
        Feed outlook5 = Feed.builder()
                            .category(CategoryType.OUTLOOK)
                            .writeDate(LocalDateTime.now())
                            .text("안녕하세요, 지난 달 진행하였던 7월 개선건 관련하여 CBT 결과를 전달받았습니다. 공유드립니다. ")
                            .member(seungmi)
                            .hostId(5L)
                            .status(false)
                            .subject("7월 개선건 CBT 결과 공유")
                            .build();
        feedRepository.saveAll(List.of(outlook1, outlook2, outlook3, outlook4, outlook5));
    }
}
