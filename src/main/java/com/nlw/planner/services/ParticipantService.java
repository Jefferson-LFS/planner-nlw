package com.nlw.planner.services;

import com.nlw.planner.api.dto.participant.ParticipantRegisterResponseDTO;
import com.nlw.planner.api.dto.participant.ParticipantRequestDTO;
import com.nlw.planner.api.dto.participant.ParticipantResponseDTO;
import com.nlw.planner.model.participant.Participant;
import com.nlw.planner.model.participant.exceptions.ParticipantNotFoundException;
import com.nlw.planner.model.trip.Trip;
import com.nlw.planner.repositories.ParticipantRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;


    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }
    public ParticipantResponseDTO confirmParticipant(UUID id, ParticipantRequestDTO participantRequestDTO) {
        Participant rawParticipant = this.getParticipantById(id);

        rawParticipant.setIsConfirmed(true);
        rawParticipant.setName(participantRequestDTO.name());

        this.participantRepository.save(rawParticipant);

        return new ParticipantResponseDTO(
                rawParticipant.getId(),
                rawParticipant.getName(),
                rawParticipant.getEmail(),
                rawParticipant.getIsConfirmed()
        );
    }

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
    }

    private Participant getParticipantById(UUID participantId) {
        return this.participantRepository
                .findById(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException("Participant not found with ID: " + participantId));
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){}

    public void triggerConfirmationEmailToParticipant(String email){}


}
