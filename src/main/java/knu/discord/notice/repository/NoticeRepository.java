package knu.discord.notice.repository;

import jakarta.validation.constraints.NotNull;
import knu.discord.notice.Notice;
import knu.discord.notice.Send;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findTop10BySendOrderByUploadDateAsc(Send send);

    List<Notice> findTop3BySendAndUploadDateAfterOrderByVisitedDesc(Send send, LocalDate uploadDate);

}
