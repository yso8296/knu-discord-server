package knu.discord.notice.service;

import knu.discord.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisViewCountSyncSchedulerService {

    private final RedisTemplate<String, String> redisTemplate;
    private final NoticeRepository noticeRepository;

    /**
     * 3분마다 Redis에 저장된 조회수를 DB에 반영합니다.
     * Redis에 저장된 키 패턴은 "notice:{id}:views"
     */
    /*@Scheduled(fixedRate = 180_000)
    @Transactional
    public void syncViewCounts() {
        Set<String> keys = redisTemplate.keys("notice:*:views");
        if (keys != null) {
            for (String key : keys) {
                // 키 형식: notice:{id}:views
                String[] parts = key.split(":");
                if (parts.length >= 3) {
                    try {
                        Long noticeId = Long.valueOf(parts[1]);
                        String countStr = redisTemplate.opsForValue().get(key);
                        if (countStr != null) {
                            int count = Integer.parseInt(countStr);
                            if (count > 0) {
                                noticeRepository.updateViewCount(noticeId, count);
                            }
                            // 반영 후 키 삭제
                            redisTemplate.delete(key);
                        }
                    } catch (NumberFormatException e) {
                        // 키 파싱에 실패하면 로그 남기기
                        System.err.println("키 파싱 실패: " + key);
                    }
                }
            }
        }
    }*/
}
