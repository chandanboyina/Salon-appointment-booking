package com.chandan.service;

import com.chandan.domain.BookingStatus;
import com.chandan.dto.BookRequest;
import com.chandan.dto.SalonDTO;
import com.chandan.dto.ServiceDTO;
import com.chandan.dto.UserDTO;
import com.chandan.model.Booking;
import com.chandan.model.SalonReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BookingService {
    Booking createBooking(BookRequest booking, UserDTO ser, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception;

    Boolean isTimeSlotAvailable(SalonDTO salonDTO,
                                LocalDateTime bookingStartTime,
                                LocalDateTime bookingEndTime) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long SalonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long  salonId);

}
