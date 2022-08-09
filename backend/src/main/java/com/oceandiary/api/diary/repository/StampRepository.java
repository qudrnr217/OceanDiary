package com.oceandiary.api.diary.repository;

import com.oceandiary.api.diary.entity.Stamp;
import com.oceandiary.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long> {
    List<Stamp> findAllByUserOrderByIdDesc(User user);
}
