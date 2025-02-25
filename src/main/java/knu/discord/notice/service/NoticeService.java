package knu.discord.notice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import knu.discord.global.annotation.RedissonLock;
import knu.discord.global.constants.MessageConstants;
import knu.discord.global.exception.EntityNotFoundException;
import knu.discord.notice.Notice;
import knu.discord.notice.controller.dto.NoticeResponse;
import knu.discord.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final RedisTemplate<String, String> redisTemplate;

    //@Transactional
    @RedissonLock(value = "#noticeId")
    public NoticeResponse.Link processNoticeRedirect(Long noticeId, HttpServletRequest request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.NOTICE_NOT_FOUND_MESSAGE));

        String ip = getClientIP(request);
        // IP 체크 키: notice:{id}:ip:{ip}
        String ipKey = "notice:" + noticeId + ":ip:" + ip;
        String viewKey = "notice:" + noticeId + ":views";

        // IP 키가 존재하지 않으면 조회수 증가
        Boolean exists = redisTemplate.hasKey(ipKey);
        if (Boolean.FALSE.equals(exists)) {
            redisTemplate.opsForValue().increment(viewKey, 1);
            redisTemplate.opsForValue().set(ipKey, "1", 24, TimeUnit.HOURS);
        }

        String safeLink = notice.getLink().replace(">", "%3E");

        return NoticeResponse.Link.from(safeLink);
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
