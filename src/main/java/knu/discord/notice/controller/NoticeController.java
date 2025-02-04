package knu.discord.notice.controller;

import knu.discord.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/test")
    public void test() {
        String category = "일반";
        String title = "2024학년도 동계 기술창업 부트캠프 신청자 모집 안내";
        String link = "https://see.knu.ac.kr/content/board/notice.html";
        String createdAt = "2025-01-20 05:46";
        String author = "시스템도서위원회";
        //noticeService.sendNotice(category, title, link, createdAt, author);
    }
}
