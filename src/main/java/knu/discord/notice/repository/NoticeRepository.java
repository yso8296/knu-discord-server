package knu.discord.notice.repository;

import knu.discord.notice.Notice;
import knu.discord.notice.Send;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findTop10BySendOrderByUploadDateAsc(Send send);

    List<Notice> findTop3BySendAndUploadDateAfterOrderByVisitedDesc(Send send, LocalDate uploadDate);

    @Modifying
    @Query("update Notice n set n.visited = n.visited + :count where n.id = :id")
    void updateViewCount(@Param("id") Long id, @Param("count") int count);

}
