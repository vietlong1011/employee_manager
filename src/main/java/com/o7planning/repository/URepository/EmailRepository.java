package com.o7planning.repository.URepository;

import com.o7planning.entity.user.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email,Long> {

    Optional<Email> findByEmailUser(String emailUser);

    Optional<Email> findEmailByOtp(String otp);

    @Query("SELECT e.user.id FROM Email e where e.emailUser = :email")
    Long findUserIdByNameEmail(String email);
}
