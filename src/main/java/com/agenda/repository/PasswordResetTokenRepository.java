package com.agenda.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.agenda.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
