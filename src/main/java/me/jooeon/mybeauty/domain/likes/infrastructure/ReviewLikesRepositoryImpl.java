package me.jooeon.mybeauty.domain.likes.infrastructure;

import me.jooeon.mybeauty.domain.likes.model.ReviewLikes;
import me.jooeon.mybeauty.domain.likes.model.repository.ReviewLikesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikesRepositoryImpl extends JpaRepository<ReviewLikes, Long>, ReviewLikesRepository {

}
