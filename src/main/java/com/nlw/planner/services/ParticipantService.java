package com.nlw.planner.services;

import com.nlw.planner.api.dto.ParticipantRegisterResponseDTO;
import com.nlw.planner.api.dto.ParticipantResponseDTO;
import com.nlw.planner.model.participant.Participant;
import com.nlw.planner.model.trip.Trip;
import com.nlw.planner.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip) {
       List<Participant> participants =  participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

       this.participantRepository.saveAll(participants);

    }

    public ParticipantRegisterResponseDTO registerParticipantToTrip(String email, Trip trip) {
        Participant newParticipant = new Participant(email, trip);
        this.participantRepository.save(newParticipant);

        return new ParticipantRegisterResponseDTO(newParticipant.getId());
    }

    public List<ParticipantResponseDTO> getAllParticipantsFromTrip (UUID tripId) {

        return this.participantRepository.findByTripId(tripId).stream().map(participant ->
                        new ParticipantResponseDTO(
                                participant.getId(),
                                participant.getName(),
                                participant.getEmail(),
                                participant.getIsConfirmed()
                        )).toList();
    };

    public void triggerConfirmationEmailToParticipants(UUID tripId){};

    public void triggerConfirmationEmailToParticipant(String email){};


}
