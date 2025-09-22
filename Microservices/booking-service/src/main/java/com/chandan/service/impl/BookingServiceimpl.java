package com.chandan.service.impl;

import com.chandan.Repository.BookingRespository;
import com.chandan.domain.BookingStatus;
import com.chandan.dto.BookRequest;
import com.chandan.dto.SalonDTO;
import com.chandan.dto.ServiceDTO;
import com.chandan.dto.UserDTO;
import com.chandan.model.Booking;
import com.chandan.model.SalonReport;
import com.chandan.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceimpl implements BookingService {

    private final BookingRespository bookingRespository;

    @Override
    public Booking createBooking(BookRequest booking,
                                 UserDTO user,
                                 SalonDTO salon,
                                 Set<ServiceDTO> serviceDTOSet) throws Exception {
        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salon, bookingStartTime, bookingEndTime);
        int totalPrice = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();
        Set<Long> idList = serviceDTOSet.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setServiceIds(idList);
        newBooking.setTotalPrice(totalPrice);
        return bookingRespository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO,
                                       LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime) throws Exception {
        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());
        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingStartTime.toLocalDate().plusDays(1)); // Extend to next day for closeTime

        // Debug logging
        System.out.println("Booking Start: " + bookingStartTime);
        System.out.println("Booking End: " + bookingEndTime);
        System.out.println("Salon Open: " + salonOpenTime);
        System.out.println("Salon Close: " + salonCloseTime);

        if (bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {
            throw new Exception("Booking Time must be within salon's working hour: " + salonOpenTime + " to " + salonCloseTime);
        }

        for (Booking existingBooking : existingBookings) {
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();
            if (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)) {
                throw new Exception("Slot not available, choose a different time");
            }
        }
        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRespository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRespository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRespository.findById(id).orElse(null);
        if (booking == null) {
            throw new Exception("Booking not found");
        }
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRespository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = getBookingsBySalon(salonId);
        if (date == null) {
            return allBookings;
        }
        return allBookings.stream().filter(booking -> isSameData(booking.getStartTime(), date) || isSameData(booking.getEndTime(), date)).collect(Collectors.toList());
    }

    private boolean isSameData(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);
        Double totalEarnings = bookings.stream().mapToDouble(Booking::getTotalPrice).sum();
        Integer totalBooking = bookings.size();
        List<Booking> cancelledBookings = bookings.stream().filter(booking -> booking.getStatus().equals(BookingStatus.CANCELED)).collect(Collectors.toList());
        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
        String salonName = ""; // Should be fetched from Salon entity
        report.setSalonName(salonName);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalBookings(totalBooking);
        report.setTotalEarnings(totalEarnings);
        Integer totalRefund = 0; // Should be calculated based on cancelled bookings
        report.setTotalRefund(totalRefund);
        return report;
    }
}