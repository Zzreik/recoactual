package com.ucr.reco.controller;

import com.ucr.reco.model.Reservation;
import com.ucr.reco.model.dto.ReservationDTO;
import com.ucr.reco.repository.ReservationJpaRepository;
import com.ucr.reco.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    ReservationService service;

    @GetMapping("/all")
    public ResponseEntity<?>  getAll(){

        return ResponseEntity.ok(service.findAll());

    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ReservationDTO reservationDTO){

        return ResponseEntity.ok(service.add(reservationDTO));

    }




}
