package com.o7planning.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tokens")
public class Tokens {

    @Id
    @GeneratedValue
    public Long id;

    public String token;

    @Enumerated(EnumType.STRING) //gia tri cua tokenType duong luu bang String trong DB
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name ="user_id")
    public User user;

}
