package com.o7planning.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name ="user_id",referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name ="role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Column(name = "provider_ID")
    private String providerID;
}
