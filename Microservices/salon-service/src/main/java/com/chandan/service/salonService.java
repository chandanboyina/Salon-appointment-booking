package com.chandan.service;

import com.chandan.model.salon;
import com.chandan.payload.dto.SalonDTO;
import com.chandan.payload.dto.UserDTO;

import java.util.List;

public interface salonService {
    salon createsalon(SalonDTO salon, UserDTO user) ;

    salon updatesalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception;
    List<salon> getAllSalon();
    salon getSalonById(Long salonId) throws Exception;
    salon getSalonByOwnerId(Long ownerId);


    List<salon> searchSalonByCity(String city);
}
