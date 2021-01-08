package com.melhamra.eatItBackend.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    @Column(length = 20, nullable = false)
    private String fullName;
    @Column(length = 20, nullable = false, unique = true)
    private String email;
    @Column(length = 10, nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String encryptedPassword;
    @Column(nullable = false)
    private String roles;

}
