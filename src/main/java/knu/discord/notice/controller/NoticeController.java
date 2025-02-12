package knu.discord.notice.controller;

import jakarta.servlet.http.HttpServletRequest;
import knu.discord.notice.Notice;
import knu.discord.notice.repository.NoticeRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/knu/notice")
@Slf4j
public class NoticeController {

    private final NoticeRepository noticeRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectNotice(@PathVariable("id") Long id, HttpServletRequest request) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다."));

        String ip = getClientIP(request);
        // IP 체크 키: notice:{id}:ip:{ip}
        String ipKey = "notice:" + id + ":ip:" + ip;
        Boolean exists = redisTemplate.hasKey(ipKey);
        if (Boolean.FALSE.equals(exists)) {
            // 해당 IP가 처음 조회한 경우, 조회수 증가
            String viewKey = "notice:" + id + ":views";
            redisTemplate.opsForValue().increment(viewKey, 1);
            // 해당 IP 키에 24시간 TTL 설정 (24시간 후 다시 조회 시 증가 가능)
            redisTemplate.opsForValue().set(ipKey, "1", 24, TimeUnit.HOURS);
        }

        // 실제 공지사항 링크로 리다이렉트 (예: notice.getLink()가 실제 공지사항 주소)
        String safeLink = notice.getLink().replace(">", "%3E");
        URI redirectUri = URI.create(safeLink);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.info("X-FORWARDED-FOR: {}", ip);

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("Proxy-Client-IP: {}", ip);
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP: {}", ip);
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("HTTP_CLIENT_IP: {}", ip);
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("HTTP_X_FORWARDED_FOR: {}", ip);
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
            log.info("getRemoteAddr: {}", ip);
        }

        // 만약 로컬 개발환경이라서 IPv6 루프백 주소가 나오면 IPv4로 변환
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        log.info("Result: IP Address: {}", ip);
        return ip;
    }
}
