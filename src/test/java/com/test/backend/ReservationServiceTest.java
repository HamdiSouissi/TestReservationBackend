package com.test.backend;

import com.test.backend.api.dto.BusDto;
import com.test.backend.api.dto.ClientDto;
import com.test.backend.api.dto.ReservationDto;
import com.test.backend.api.dto.TrajetDto;
import com.test.backend.domain.Bus;
import com.test.backend.domain.Client;
import com.test.backend.domain.Reservation;
import com.test.backend.domain.Trajet;
import com.test.backend.entity.ReservationMapper;
import com.test.backend.repository.BusRepository;
import com.test.backend.repository.ReservationRepository;
import com.test.backend.service.IReservationService;
import com.test.backend.service.impl.ReservationServiceImpl;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private BusRepository busRepository;

    private IReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationServiceImpl(reservationRepository, reservationMapper, busRepository);
    }

    @Test
    void createReservation_should_save_and_return_the_reservation_when_trajets_is_not_empty() {
        // given
        BusDto busDto = new BusDto(1L, 200, 30, 5.0);
        TrajetDto trajetDto1 = new TrajetDto(1L, 2, LocalDateTime.now(), busDto);
        ClientDto clientDto = new ClientDto(1L, "Test", "test@gmail.com");
        ReservationDto dto = new ReservationDto(null, LocalDate.now(), Collections.singletonList(trajetDto1), clientDto, 0.0);
        Bus bus = new Bus(1L, 200, 30, 5.0);
        Reservation entity = new Reservation(1L, LocalDate.now(), List.of(new Trajet(1L, 2, LocalDateTime.now(), 2.5, bus)), new Client(), 0.0);
        Set<Integer> busNumbers = dto.trajets().stream()
                .map(TrajetDto::bus)
                .map(BusDto::busNumber)
                .collect(Collectors.toSet());

        // when
        when(busRepository.findByBusNumberIn(busNumbers)).thenReturn(List.of(bus));
        when(reservationMapper.dtoToEntity(any())).thenReturn(entity);
        when(reservationRepository.save(entity)).thenReturn(entity);
        when(reservationMapper.entityToDto(entity)).thenReturn(dto);

        Optional<ReservationDto> result = reservationService.createReservation(dto);

        // then
        verify(reservationRepository).save(entity);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void createReservation_should_throw_exception_when_trajets_is_empty() {
        // given
        ClientDto clientDto = new ClientDto(1L, "Test", "test@gmail.com");
        ReservationDto dto = new ReservationDto(null, LocalDate.now(), Collections.emptyList(), clientDto, 0.0);
        Reservation entity = new Reservation(1L, LocalDate.now(), Collections.emptyList(), new Client(), 0.0);

        // when and then
        assertThrows(NullPointerException.class, () -> reservationService.createReservation(dto));
        verify(reservationRepository, never()).save(entity);
    }

    @Test
    void updateReservation_should_save_and_return_the_reservation_when_trajets_is_not_empty() {
        // given
        BusDto busDto = new BusDto(1L, 200, 30, 5.0);
        TrajetDto trajetDto1 = new TrajetDto(1L, 2, LocalDateTime.now(), busDto);
        ClientDto clientDto = new ClientDto(1L, "Test", "test@gmail.com");
        ReservationDto dto = new ReservationDto(null, LocalDate.now(), Collections.singletonList(trajetDto1), clientDto, 0.0);
        Bus bus = new Bus(1L, 200, 30, 5.0);
        Reservation entity = new Reservation(1L, LocalDate.now(), List.of(new Trajet(1L, 2, LocalDateTime.now(), 2.5, bus)), new Client(), 0.0);
        Set<Integer> busNumbers = dto.trajets().stream()
                .map(TrajetDto::bus)
                .map(BusDto::busNumber)
                .collect(Collectors.toSet());

        // when
        when(busRepository.findByBusNumberIn(busNumbers)).thenReturn(List.of(bus));
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(reservationMapper.dtoToEntity(any())).thenReturn(entity);
        when(reservationRepository.save(entity)).thenReturn(entity);
        when(reservationMapper.entityToDto(entity)).thenReturn(dto);

        Optional<ReservationDto> result = reservationService.updateReservation(1L, dto);

        // then
        verify(reservationRepository).save(entity);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void updateReservation_should_throw_exception_when_id_does_not_exist() {
        // given
        BusDto busDto = new BusDto(1L, 200, 30, 5.0);
        TrajetDto trajetDto1 = new TrajetDto(1L, 2, LocalDateTime.now(),busDto);
        ClientDto clientDto = new ClientDto(1L, "Test", "test@gmail.com");
        ReservationDto dto = new ReservationDto(null, LocalDate.now(), Collections.singletonList(trajetDto1), clientDto, 0.0);

        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class, () -> reservationService.updateReservation(1L, dto));
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void getReservation_should_return_the_reservation_when_the_reservation_id_exists() {
        // given
        ClientDto clientDto = new ClientDto(1L, "Test", "test@gmail.com");
        ReservationDto dto = new ReservationDto(null, LocalDate.now(), Collections.emptyList(), clientDto, 0.0);
        Reservation entity = new Reservation(1L, LocalDate.now(), Collections.emptyList(), new Client(), 0.0);
        Long id = 1L;

        // when
        when(reservationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(reservationMapper.entityToDto(entity)).thenReturn(dto);

        Optional<ReservationDto> result = reservationService.getReservation(id);

        // then
        assertTrue(result.isPresent());
        verify(reservationRepository).findById(id);
    }

    @Test
    void getReservation_should_return_empty_when_the_reservation_id_does_not_exists() {
        // given
        Long id = 1L;

        // when
        when(reservationRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ReservationDto> result = reservationService.getReservation(id);

        // then
        assertTrue(result.isEmpty());
        verify(reservationRepository).findById(id);
    }

    @Test
    void getReservationsByClientId_should_return_empty_list_when_there_is_no_reservation_for_this_client() {
        // given
        List<ReservationDto> emptyList = Collections.emptyList();

        // then
        when(reservationService.getReservationsByClientId(1L)).thenReturn(emptyList);

        List<ReservationDto> result = reservationService.getReservationsByClientId(1L);

        // then
        assertTrue(result.isEmpty());
        verify(reservationRepository).findAllByClient_Id(1L);
    }

    @Test
    void getReservationsByClientId_should_return_reservations_when_reservations_exists_for_this_client() {
        // given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(Reservation.builder().build());
        reservations.add(Reservation.builder().build());

        // when
        when(reservationRepository.findAllByClient_Id(1L)).thenReturn(reservations);

        List<ReservationDto> result = reservationService.getReservationsByClientId(1L);

        // then
        assertFalse(result.isEmpty());
        verify(reservationRepository).findAllByClient_Id(1L);
    }

    @Test
    void deleteReservation_should_delete_reservation_when_reservation_id_exists() {
        // given
        Long id = 1L;

        // when
        doNothing().when(reservationRepository).deleteById(id);

        // then
        assertDoesNotThrow(() -> reservationService.deleteReservation(id));
        verify(reservationRepository).deleteById(id);
    }

    @Test
    void deleteReservation_should_throw_exception_when_reservation_id_does_not_exists() {
        // given
        Long id = 1L;

        // when
        doThrow(EmptyResultDataAccessException.class).when(reservationRepository).deleteById(id);

        // then
        assertThrows(DataAccessException.class, () -> reservationService.deleteReservation(id));
        verify(reservationRepository).deleteById(id);
    }

}
