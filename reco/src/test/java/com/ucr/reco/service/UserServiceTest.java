package com.ucr.reco.service;

import com.ucr.reco.model.User;
import com.ucr.reco.model.dto.UserDTO;
import com.ucr.reco.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Habilita el uso de Mockito dentro de esta clase de prueba
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // Simula el repositorio para evitar usar la base de datos real
    @Mock
    private UserJpaRepository repository;

    // Crea una instancia de UserService e inyecta los mocks automáticamente
    @InjectMocks
    private UserService userService;

    private UserDTO dto;

    // Se ejecuta antes de cada prueba para inicializar datos
    @BeforeEach
    void setUp() {

        // Usuario de prueba que será utilizado en los distintos casos
        dto = new UserDTO(
                "Sebas",
                "sebas@gmail.com",
                "Password1@",
                "ADMIN"
        );
    }

    @Test
    void deberiaAgregarUsuarioCorrectamente(){

        // Arrange (Preparar)
        // Crear datos y configurar Mockito
        // Simula que el correo aún no existe en el sistema
        when(repository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        // Usuario que será devuelto al guardar correctamente
        User usuarioGuardado = new User(
                1,
                "Sebas",
                "Sebas@gmail.com",
                "Password1@",
                "ADMIN"
        );

        // Simula el guardado exitoso del usuario
        when(repository.save(any(User.class))).thenReturn(usuarioGuardado);

        // Act (Actuar)
        // Ejecuta el metodo que quiero probar
        User resultado = userService.add(dto);

        // Assert (Verificar)
        // Verifica que el usuario se haya creado correctamente
        assertNotNull(resultado);

        // Verifica que los datos del usuario sean los esperados
        assertEquals("Sebas", resultado.getName());
        assertEquals("Sebas@gmail.com", resultado.getEmail());

        // Comprueba que los métodos del repositorio fueron llamados
        verify(repository).existsByEmail(dto.getEmail());
        verify(repository).save(any(User.class));
    }

    @Test
    void noDeberiaAgregarUsuarioSiCorreoYaExiste(){

        // ARRANGE
        // Simula que el correo ya se encuentra registrado
        when(repository.existsByEmail(dto.getEmail())).thenReturn(true);

        // ACT
        // Ejecuta el metodo de registro
        User resultado = userService.add(dto);

        // ASSERT
        // Verifica que el metodo no registre el usuario
        assertNull(resultado);

        // Comprueba que no se intentó guardar el usuario
        verify(repository).existsByEmail(dto.getEmail());
        verify(repository, never()).save(any(User.class));
    }



}
