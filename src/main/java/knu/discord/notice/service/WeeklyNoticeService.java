package knu.discord.notice.service;

import knu.discord.notice.Notice;
import knu.discord.notice.Send;
import knu.discord.notice.WebhookUrlProperties;
import knu.discord.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeeklyNoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;
    private final WebhookUrlProperties webhookUrlProperties;
    @Value("${server.redirect-url}")
    private String serverBaseUrl;

    /**
     * 매주 일요일 자정에 지난 일주일 간의 조회수가 가장 높은 공지사항 3개를 선정해 Discord 채널에 공지합니다.
     */

    //@Scheduled(cron = "0 */10 * * * *")
    @Scheduled(cron = "0 0 0 ? * SUN") // 매주 일요일 자정 실행
    public void sendWeeklyTopNotices() {
        // 지난 일주일 간의 기준일: 오늘 - 7일
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        List<Notice> topNotices = noticeRepository.findTop3BySendAndUploadDateAfterOrderByVisitedDesc(Send.Y, oneWeekAgo);
        if (topNotices.isEmpty()) {
            System.out.println("지난 주 인기 공지가 없습니다.");
            return;
        }
        String weeklyWebHookUrl = webhookUrlProperties.weeklyUrl();
        noticeService.sendNotice("주간 인기", "지난 주 인기 공지 TOP 3", "", LocalDate.now().toString(), "경북대학교 전자공학부", weeklyWebHookUrl);
        // 전송할 Discord 웹훅 주소 (예: 공지 전용 채널의 웹훅)

        for (int i = 0; i < topNotices.size(); i++) {
            Notice notice = topNotices.get(i);
            // 서버의 리다이렉트 링크 사용
            String redirectLink = serverBaseUrl + notice.getId();
            noticeService.sendNotice(notice.getCategory().getDisplayName(), notice.getTitle(), redirectLink, notice.getUploadDate().toString(), "경북대학교 전자공학부", weeklyWebHookUrl);
        }
    }
}
