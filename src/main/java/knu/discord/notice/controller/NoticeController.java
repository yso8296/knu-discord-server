package knu.discord.notice.controller;

import jakarta.servlet.http.HttpServletRequest;
import knu.discord.notice.Notice;
import knu.discord.notice.controller.dto.NoticeResponse;
import knu.discord.notice.repository.NoticeRepository;
import knu.discord.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/knu/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectNotice(@PathVariable("id") Long id, HttpServletRequest request) {
        NoticeResponse.Link response = noticeService.processNoticeRedirect(id, request);

        // 실제 공지사항 링크로 리다이렉트 (예: notice.getLink()가 실제 공지사항 주소)
        URI redirectUri = URI.create(response.link());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
