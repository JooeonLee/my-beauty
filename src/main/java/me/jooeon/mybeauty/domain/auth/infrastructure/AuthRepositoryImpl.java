package me.jooeon.mybeauty.domain.auth.infrastructure;

import me.jooeon.mybeauty.domain.auth.model.entity.Auth;
import me.jooeon.mybeauty.domain.auth.model.repository.AuthRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepositoryImpl extends JpaRepository<Auth, Long>, AuthRepository {

    Optional<Auth> findByProviderId(String providerId);
}
