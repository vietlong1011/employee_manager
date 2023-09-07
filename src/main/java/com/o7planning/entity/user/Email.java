package com.o7planning.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String emailUser;

    private String otp;

    private Boolean active;

    private LocalDateTime otpGeneratedTime;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name ="user_id")
    public User user;
}
