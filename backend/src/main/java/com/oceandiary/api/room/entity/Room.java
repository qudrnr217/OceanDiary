package com.oceandiary.api.room.entity;

import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.common.entity.BaseEntity;
import com.oceandiary.api.file.entity.Image;
import com.oceandiary.api.room.request.RoomRequest;
import com.oceandiary.api.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(of = {"id", "title"})
@Table(name = "room")
public class Room extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private final List<Dropout> dropouts = new ArrayList<>();

    private String title;

    private String rule;

    @Column(name = "max_num")
    private Integer maxNum;

    @Column(name = "is_open")
    private Boolean isOpen;

    private String pw;

    public void updateInfo(RoomRequest.UpdateRoom request, Image newImage) {
        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }
        if (request.getRule() != null) {
            this.rule = request.getRule();
        }
        if (request.getMaxNum() != null) {
            this.maxNum = request.getMaxNum();
        }
        if (request.getIsOpen() != null) {
            this.isOpen = request.getIsOpen();
        }
        if (request.getPw() != null) {
            this.pw = request.getPw();
        }
        if (newImage != null) {
            this.image = newImage;
        }
    }
}
