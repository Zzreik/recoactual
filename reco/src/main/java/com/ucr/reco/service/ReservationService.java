package com.ucr.reco.service;

import com.ucr.reco.model.Reservation;
import com.ucr.reco.model.Space;
import com.ucr.reco.model.Status;
import com.ucr.reco.model.User;
import com.ucr.reco.model.dto.ReservationDTO;
import com.ucr.reco.repository.ReservationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationJpaRepository repository;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private UserService userService;

    public List<Reservation> findAll(){

        return repository.findAll();

    }


    public Reservation add(ReservationDTO reservationDTO){

        Space space = spaceService.getById(reservationDTO.getSpaceId());
        User user = userService.getUserByEmail(reservationDTO.getUserEmail());
        Reservation reservationTemp = new Reservation();


        reservationTemp.setSpace(space);
        reservationTemp.setUser(user);
        reservationTemp.setStartDate(reservationDTO.getStartDate());
        reservationTemp.setEndDate(reservationDTO.getEndDate());
        reservationTemp.setStatus(Status.PENDING);
        return repository.save(reservationTemp);


    }

}
