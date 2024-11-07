package me.jooeon.mybeauty.domain.article.infrastructure;

import me.jooeon.mybeauty.domain.article.model.Scrap;
import me.jooeon.mybeauty.domain.article.model.repository.ScrapRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScrapRepositoryImpl extends JpaRepository<Scrap, Long>, ScrapRepository {

    @Query("select count(s) from Scrap s "
            + "where s.member.id = :memberId ")
    long countByMemberId(@Param("memberId") Long memberId);
}
