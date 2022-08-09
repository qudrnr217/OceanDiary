package com.oceandiary.api.diary.entity;

import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.common.entity.BaseEntity;
import com.oceandiary.api.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "stamp")
public class Stamp extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stamp_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "enter_time")
    private LocalDateTime enterTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @Column(name = "total_time")
    private String totalTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private void setUser(User user) {
        this.user = user;
    }

    public static Stamp create(User user, Category category, LocalDateTime enterTime, LocalDateTime exitTime) {
        Stamp stamp = Stamp.builder()
                .category(category)
                .enterTime(enterTime)
                .exitTime(exitTime)
                .totalTime(calcTime(enterTime, exitTime))
                .build();
        stamp.setUser(user);
        return stamp;
    }

    private static String calcTime(LocalDateTime enterTime, LocalDateTime exitTime) {
        Duration duration = Duration.between(enterTime,exitTime);
        long time =  duration.getSeconds();
        StringBuilder sb = new StringBuilder();
        String tmp = "";
        for(int i = 0; i < 3; i++) {
            tmp = String.valueOf(time%60);
            if(tmp.length() == 1)
                tmp = "0"+tmp;
            sb.insert(0,tmp);
            if(i != 2)
                sb.insert(0, ":");
                time /= 60;
        }
        return sb.toString();
    }
}