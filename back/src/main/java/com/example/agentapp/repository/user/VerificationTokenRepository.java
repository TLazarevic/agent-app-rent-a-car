package com.example.agentapp.repository.user;

import com.example.agentapp.model.user.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken save(VerificationToken verificationToken);



    @Query("select vT from VerificationToken vT where vT.token = ?1")
    VerificationToken findToken(String token);
}
