package com.oceandiary.api.user.entity;

import com.oceandiary.api.common.entity.BaseEntity;
import com.oceandiary.api.diary.dto.DiaryRequest;
import com.oceandiary.api.diary.entity.Stamp;
import com.oceandiary.api.room.entity.Dropout;
import com.oceandiary.api.room.entity.Room;
import com.oceandiary.api.user.domain.Role;
import com.oceandiary.api.user.domain.SocialProvider;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    @Column(name = "oauth_id")
    private String oauthId;

    private String name;

    private LocalDate birth;

    @Column(name = "last_visited")
    private LocalDateTime visitedAt;

    @Enumerated(EnumType.STRING)
    private SocialProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private final List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Dropout> dropouts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Stamp> stamps = new ArrayList<>();

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateVisitedAt() {
        this.visitedAt = LocalDateTime.now();
    }

    public void updateUserInfo(DiaryRequest.UserInfo request){
        if(request.getName() != null){
            this.name = request.getName();
        }
        if(request.getEmail() != null){
            this.email = request.getEmail();
        }
        if(request.getBirth() != null){
            this.birth = request.getBirth();
        }
    }
}
