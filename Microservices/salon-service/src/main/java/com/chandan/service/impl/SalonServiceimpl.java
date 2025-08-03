package com.chandan.service.impl;

import com.chandan.model.salon;
import com.chandan.payload.dto.SalonDTO;
import com.chandan.payload.dto.UserDTO;
import com.chandan.repository.SalonRepository;
import com.chandan.service.salonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceimpl implements salonService {

    private final SalonRepository salonRepository;


    @Override
    public salon createsalon(SalonDTO req, UserDTO user) {
        salon Salon = new salon();
        Salon.setName(req.getName());
        Salon.setAddress(req.getAddress());
        Salon.setEmail(req.getEmail());
        Salon.setCity(req.getCity());
        Salon.setImages(req.getImages());
        Salon.setOwnerId(req.getOwnerId());
        Salon.setOpenTime(req.getOpenTime());
        Salon.setCloseTime(req.getCloseTime());
        Salon.setPhone(req.getPhone());
        return salonRepository.save(Salon);
    }

    @Override
    public salon updatesalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {
        salon existingSalon=salonRepository.findById(salonId).orElse(null);
        if(existingSalon!=null && salon.getOwnerId().equals(user.getId())) {
            existingSalon.setCity(salon.getCity());
            existingSalon.setName(salon.getName());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setImages(salon.getImages());
            existingSalon.setOpenTime(salon.getOpenTime());
            existingSalon.setCloseTime(salon.getCloseTime());
            existingSalon.setPhone(salon.getPhone());
            existingSalon.setOwnerId(salon.getOwnerId());

        }
        throw new Exception("Salon not exist");
    }

    @Override
    public List<salon> getAllSalon() {
        return salonRepository.findAll();
    }

    @Override
    public salon getSalonById(Long salonId) throws Exception {
        salon Salon=salonRepository.findById(salonId).orElse(null);
        if(Salon==null) {
            throw new Exception("salon not Exist");
        }
        return Salon;
    }

    @Override
    public salon getSalonByOwnerId(Long ownerId) {

        return salonRepository.findByOwnerId(ownerId);
    }



    @Override
    public List<salon> searchSalonByCity(String city) {

        return salonRepository.searchSalon(city);
    }
}
