package com.nlw.planner.repositories;

import com.nlw.planner.model.activities.Activity;
import com.nlw.planner.model.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

}
