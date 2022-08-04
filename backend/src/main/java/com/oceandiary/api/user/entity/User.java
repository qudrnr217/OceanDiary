package com.oceandiary.api.user.entity;

import com.oceandiary.api.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String email;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column
    private String name;

    @Column
    private LocalDate birth;

    @Column(name = "last_visited")
    private LocalDateTime visitedAt;

    //@OneToMany(mappedBy = "stamp")
    //private List<Stamp> stamps = new ArrayList<>();

}
