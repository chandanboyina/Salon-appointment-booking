package com.chandan.service;

import com.chandan.modal.Salon;
import com.chandan.payload.dto.SalonDTO;
import com.chandan.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {


    Salon createSalon(SalonDTO salon, UserDTO user);

    Salon updateSalon(Long salonId, Salon salon) throws Exception;

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId);

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCity(String city);
}
