package com.nlw.planner.repositories;

import com.nlw.planner.model.participant.Participant;
import com.nlw.planner.model.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

}
