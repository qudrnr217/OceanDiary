package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long>, ParticipantRepositoryCustom {

}
