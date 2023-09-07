package com.o7planning.repository.URepository;

import com.o7planning.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository  extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserById(Long id);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.providerID = :providerId")
    Optional<User> findUsernameAndProviderID(String username, String providerId);

    void deleteUserById(Long id);

}
