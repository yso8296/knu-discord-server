package knu.discord.notice.repository;

import knu.discord.notice.Notice;
import knu.discord.notice.Send;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findTop10BySendOrderByUploadDateAsc(Send send);
}
