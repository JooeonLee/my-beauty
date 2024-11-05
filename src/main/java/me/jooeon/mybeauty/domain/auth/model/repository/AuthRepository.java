package me.jooeon.mybeauty.domain.auth.model.repository;

import me.jooeon.mybeauty.domain.auth.model.entity.Auth;

import java.util.Optional;

public interface AuthRepository {

    Optional<Auth> findByProviderId(String providerId);

    public <S extends Auth> S save(S entity);

}
