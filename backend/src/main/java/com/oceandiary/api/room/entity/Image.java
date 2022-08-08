package com.oceandiary.api.room.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    private Room room;

    private String extension;

    private String name;

    @Column(name = "origin_name")
    private String originName;

    private String url;

    private Long size;

    private Integer width;

    private Integer height;
}
