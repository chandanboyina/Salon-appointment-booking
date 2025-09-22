package com.chandan.controller;

import com.chandan.domain.BookingStatus;
import com.chandan.dto.*;
import com.chandan.mapper.BookingMapper;
import com.chandan.model.Booking;
import com.chandan.model.SalonReport;
import com.chandan.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long salonId, @RequestBody BookRequest bookingRequest) throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);

        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);

        // Dynamically set openTime and closeTime based on current time
        LocalTime now = LocalTime.now(); // Current time: 02:52 PM IST
        LocalTime openTime = now.plusMinutes(12).truncatedTo(java.time.temporal.ChronoUnit.HOURS); // Round up to 3:00 PM
        LocalTime closeTime = openTime.plusHours(10); // 3:00 PM + 10 hours = 1:00 AM next day
        salon.setOpenTime(openTime);
        salon.setCloseTime(closeTime);

        Set<ServiceDTO> serviceDTOSet = bookingRequest.getServiceIds().stream()
                .map(id -> {
                    ServiceDTO serviceDTO = new ServiceDTO();
                    serviceDTO.setId(id);
                    serviceDTO.setPrice(399); // Example price
                    serviceDTO.setDuration(45); // Example duration in minutes
                    serviceDTO.setDescription("Hair cut for men");
                    return serviceDTO;
                }).collect(Collectors.toSet());

        Booking booking = bookingService.createBooking(bookingRequest, user, salon, serviceDTOSet);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer() {
        List<Booking> bookings = bookingService.getBookingsByCustomer(1L);
        return ResponseEntity.ok(getBookingDTO(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon() {
        List<Booking> bookings = bookingService.getBookingsBySalon(1L);
        return ResponseEntity.ok(getBookingDTO(bookings));
    }

    private Set<BookingDTO> getBookingDTO(List<Booking> bookings) {
        return bookings.stream().map(booking -> {
            return BookingMapper.toDTO(booking);
        }).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsById(@PathVariable Long bookingId) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) throws Exception {
        Booking booking = bookingService.updateBooking(bookingId, status);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@PathVariable Long salonId, @RequestParam(required = false) LocalDate date) throws Exception {
        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);
        List<BookingSlotDTO> slotDTOS = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO bookingSlotDTO = new BookingSlotDTO();
                    bookingSlotDTO.setStartTime(booking.getStartTime());
                    bookingSlotDTO.setEndTime(booking.getEndTime());
                    return bookingSlotDTO;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport() throws Exception {
        SalonReport report = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(report);
    }
}