package com.melhamra.eatitbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String publicId;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;

}
