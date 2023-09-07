package com.o7planning.repository.URepository;

import com.o7planning.entity.user.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TokenRepository extends JpaRepository<Tokens,Long> {

    @Query(value = """
      select t from Tokens t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = true or t.revoked = true)\s
      """)
    List<Tokens>  findAllValidTokenByUser(Long id);

    void deleteTokenByUser_Id(Long id);

    @Query("SELECT t.revoked FROM Tokens t where t.token = :tokens")
    Boolean findRevokedByToken(@Param("tokens") String tokens);

}

