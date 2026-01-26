package com.smartcontractor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String userName;
    @Column(unique = true)
    private String userEmail;
    private String userPass;
    private String isUserActive;
    private String userCreatedAt;
    private String accessToken;
    
    @OneToMany
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private List<Company> companies;
}
