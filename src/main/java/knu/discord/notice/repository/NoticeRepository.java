package knu.discord.notice.repository;

import knu.discord.notice.Notice;
import knu.discord.notice.Send;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findTop10BySendOrderByUploadDateAsc(Send send);

    List<Notice> findTop3BySendAndUploadDateAfterOrderByVisitedDesc(Send send, LocalDate uploadDate);

}
