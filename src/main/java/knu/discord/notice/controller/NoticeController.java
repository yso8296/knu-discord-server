package knu.discord.notice.controller;

import knu.discord.notice.Notice;
import knu.discord.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/knu/notice")
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectNotice(@PathVariable("id") Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다."));

        // 조회수 증가 (동시성 이슈가 있다면 적절한 동기화 고려)
        notice.setVisited(notice.getVisited() + 1);
        noticeRepository.save(notice);

        String originalLink = notice.getLink();
        String safeLink = originalLink.replace(">", "%3E");

        // 실제 공지사항 링크로 리다이렉트 (예: notice.getLink()가 실제 공지사항 주소)
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(safeLink));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
