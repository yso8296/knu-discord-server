package knu.discord.notice.service;

import knu.discord.notice.Category;
import knu.discord.notice.Notice;
import knu.discord.notice.Send;
import knu.discord.notice.repository.NoticeRepository;
import knu.discord.notice.util.NoticeUtil;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeSchedulerService {

    private final NoticeRepository noticeRepository;

    //@Value("${server.redirect-url}")
    //"https://api.knu-notice.link/api/v1/knu/notice/"
    private String serverBaseUrl = System.getenv("REDIRECT_URL");

    /**
     * 5분마다 DB에서 send가 N인 공지사항을 조회하여 각 공지사항의 카테고리 웹훅으로 전송하고,
     * 전송 후 send를 Y로 업데이트합니다.
     */
    @Scheduled(fixedRate = 300_000) // 300,000 ms = 5분
    @SchedulerLock(name = "sendPendingNotices", lockAtMostFor = "PT30S")
    public void sendPendingNotices() {
        // send 값이 'N'인 공지사항 목록 조회
        List<Notice> pendingNotices = noticeRepository.findTop10BySendOrderByUploadDateAsc(Send.N);
        for (Notice notice : pendingNotices) {
            // 카테고리 Enum에서 웹훅 URL 가져오기
            String webHookUrl = notice.getCategory().getUrl();
            // 카테고리 전송 시에는 한글 displayName 사용
            String categoryDisplay = notice.getCategory().getDisplayName();
            // 작성일은 uploadDate를 문자열로 변환
            String createdAt = notice.getUploadDate().toString();
            // Notice 엔티티에 작성자 필드가 없다면, 기본값 사용 (예: "작성자 미정")
            String author = "경북대학교 전자공학부";

            // 실제 notice.getLink() 대신 서버 리다이렉션 URL 사용 (예: http://api/v1/knu/notice/1)
            String redirectLink = serverBaseUrl + notice.getId();

            // NoticeService의 sendNotice 함수를 호출하여 Discord 웹훅 전송
            NoticeUtil.sendNotice(categoryDisplay, notice.getTitle(), redirectLink, createdAt, author, webHookUrl);
            NoticeUtil.sendNotice(categoryDisplay, notice.getTitle(), redirectLink, createdAt, author, Category.COM.getUrl());

            // 전송 완료 후 send 값을 Y로 업데이트
            notice.setSend(Send.Y);
        }
        // DB에 업데이트 반영
        noticeRepository.saveAll(pendingNotices);
    }
}
