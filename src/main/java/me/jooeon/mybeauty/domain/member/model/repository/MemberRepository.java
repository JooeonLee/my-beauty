package me.jooeon.mybeauty.domain.member.model.repository;

import me.jooeon.mybeauty.domain.member.model.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public <S extends Member> S save(S entity);

    public Optional<Member> findById(Long id);

    public void deleteAllInBatch();

    public List<Member> findByIdIn(Collection<Long> ids);
}
