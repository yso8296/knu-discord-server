package knu.discord.notice;

import jakarta.servlet.http.HttpServletRequest;
import knu.discord.notice.repository.NoticeRepository;
import knu.discord.notice.service.NoticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RaceConditionTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private NoticeRepository noticeRepository;

    private Notice testNotice;

    @BeforeEach
    public void setUp() {
        // 테스트용 공지사항 생성 (테스트 DB 또는 인메모리 DB 사용)
        testNotice = Notice.builder()
                .title("title")
                .link("http://example.com")
                .send(Send.N)
                .visited(0)
                .category(Category.CLG)
                .uploadDate(LocalDate.now())
                .build();
        noticeRepository.save(testNotice);

        // Redis의 기존 키 제거 (테스트 간섭 방지)
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }

    @Test
    @DisplayName("동일 ip로 게시글 조회 시 조회수 증가 테스트")
    public void testConcurrentViewCountIncrement() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 여러 스레드에서 동시에 조회수 증가를 시뮬레이션
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    MockHttpServletRequest mockRequest = new MockHttpServletRequest();
                    mockRequest.setRemoteAddr("127.0.0.1"); // 원격 주소 설정
                    noticeService.processNoticeRedirect(testNotice.getId(), mockRequest);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // Redis에 저장된 조회수 키 확인 (모의 IP 127.0.0.1의 키는 단 한 번만 증가되어야 함)
        String viewKey = "notice:" + testNotice.getId() + ":views";
        String viewCountStr = redisTemplate.opsForValue().get(viewKey);
        int redisCount = viewCountStr != null ? Integer.parseInt(viewCountStr) : 0;

        // 모의 로직에 따르면, 한 IP에 대해 여러 번 호출해도 최초 1회만 증가하도록 되어 있음
        assertEquals(1, redisCount, "동일 IP로 여러 호출해도 조회수는 1만 증가되어야 한다.");
    }

    @Test
    @DisplayName("조회수 동시성 제어")
    public void testConcurrentViewCountaIncrement() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    MockHttpServletRequest mockRequest = new MockHttpServletRequest();
                    mockRequest.setRemoteAddr("192.168.1." + finalI); // 원격 주소 설정
                    noticeService.processNoticeRedirect(testNotice.getId(), mockRequest);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // Redis에 저장된 조회수 키 확인
        String viewKey = "notice:" + testNotice.getId() + ":views";
        String viewCountStr = redisTemplate.opsForValue().get(viewKey);
        int redisCount = viewCountStr != null ? Integer.parseInt(viewCountStr) : 0;

        // 모든 스레드에서 조회수 증가가 1회씩 이루어졌으므로 최종 조회수는 10이어야 함
        assertEquals(100, redisCount, "동일 게시물에 대한 여러 IP의 동시 조회 시 조회수는 정확히 증가해야 합니다.");
    }
}
