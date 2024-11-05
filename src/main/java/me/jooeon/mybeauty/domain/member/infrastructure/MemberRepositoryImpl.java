package me.jooeon.mybeauty.domain.member.infrastructure;

import me.jooeon.mybeauty.domain.member.model.Member;
import me.jooeon.mybeauty.domain.member.model.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoryImpl extends JpaRepository<Member, Long>, MemberRepository {


}
