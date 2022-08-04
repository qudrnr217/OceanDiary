package com.oceandiary.api.category.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "category_id")
    private String id;

    private String name;
}
