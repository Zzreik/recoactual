package com.ucr.reco.service;

import com.ucr.reco.model.Reservation;
import com.ucr.reco.model.Space;
import com.ucr.reco.model.User;
import com.ucr.reco.model.dto.ReservationDTO;
import com.ucr.reco.repository.ReservationJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//Se indica que esta clase de prueba usara Mockito
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    // Simula el repositorio de reservas
    @Mock
    private ReservationJpaRepository repository;

    // Simula el servicio de espacios
    @Mock
    private SpaceService spaceService;

    // Simula el servicio de usuarios
    @Mock
    private UserService userService;

    // Crea el servicio de reservas e inyecta los mocks
    @InjectMocks
    private ReservationService reservationService;


    @Test
    void deberiaLanzarExcepcionCuandoFallaElGuardado() {

        // ARRANGE
        // Datos necesarios para crear la reserva
        ReservationDTO dto = new ReservationDTO(
                1,
                "sebas@gmail.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2)
        );

        Space espacio = new Space(
                1,
                "Sala Multimedia",
                "Primer Piso",
                "Laboratorio",
                15000.0
        );

        User usuario = new User(
                1,
                "Sebas",
                "sebas@gmail.com",
                "Password1@",
                "ADMIN"
        );

        // Simula la búsqueda del espacio solicitado
        when(spaceService.getById(1)).thenReturn(espacio);

        // Simula la búsqueda del usuario
        when(userService.getUserByEmail("sebas@gmail.com")).thenReturn(usuario);

        // Simula un error al intentar guardar la reserva
        when(repository.save(any(Reservation.class))).thenThrow(new RuntimeException("Error al guardar la reserva"));

        // ACT
        // Verifica que se lance una excepción al guardar la reserva
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reservationService.add(dto));

        // ASSERT
        // Comprueba que el mensaje de la excepción sea el esperado
        assertEquals("Error al guardar la reserva", exception.getMessage());

        // Verifica que los métodos necesarios fueron ejecutados
        verify(spaceService).getById(1);
        verify(userService).getUserByEmail("sebas@gmail.com");
        verify(repository).save(any(Reservation.class));
    }
}
