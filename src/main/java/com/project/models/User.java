package com.project.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToOne
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    private Date date;
}
