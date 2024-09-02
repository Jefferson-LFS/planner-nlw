package com.nlw.planner.services;

import com.nlw.planner.api.dto.LinkRegisterResponseDTO;
import com.nlw.planner.api.dto.LinkRequestDTO;
import com.nlw.planner.api.dto.LinkResponseDTO;
import com.nlw.planner.model.link.Link;
import com.nlw.planner.model.trip.Trip;
import com.nlw.planner.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public LinkRegisterResponseDTO registerLink(LinkRequestDTO linkRequestDTO, Trip trip) {

        Link newLink =  new Link(linkRequestDTO.title(), linkRequestDTO.url(), trip);

        this.linkRepository.save(newLink);

        return new LinkRegisterResponseDTO(newLink.getId());

    }



    public List<LinkResponseDTO> getAllLinksFromTrip (UUID tripId) {

        return this.linkRepository.findByTripId(tripId).stream().map(link ->
                        new LinkResponseDTO(
                                link.getId(),
                                link.getTitle(),
                                link.getUrl()
                        )).toList();
    };




}
