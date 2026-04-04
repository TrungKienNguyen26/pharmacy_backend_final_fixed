package com.example.pharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "staffs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Staff {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String phone;

    @Builder.Default
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
