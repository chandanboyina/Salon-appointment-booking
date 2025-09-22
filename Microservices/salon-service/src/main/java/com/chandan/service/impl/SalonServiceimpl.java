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
        salon salon = new salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(req.getOwnerId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhone(req.getPhone());
        return salonRepository.save(salon);
    }

    @Override
    public salon updatesalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {
        salon existingSalon = salonRepository.findById(salonId).orElse(null);
        if (existingSalon == null) {
            throw new Exception("Salon with ID " + salonId + " does not exist");
        }
        if (!existingSalon.getOwnerId().equals(user.getId())) {
            throw new Exception("Unauthorized: User does not own this salon");
        }
        existingSalon.setCity(salon.getCity());
        existingSalon.setName(salon.getName());
        existingSalon.setAddress(salon.getAddress());
        existingSalon.setEmail(salon.getEmail());
        existingSalon.setImages(salon.getImages());
        existingSalon.setOpenTime(salon.getOpenTime());
        existingSalon.setCloseTime(salon.getCloseTime());
        existingSalon.setPhone(salon.getPhone());
        existingSalon.setOwnerId(salon.getOwnerId());
        return salonRepository.save(existingSalon);
    }

    @Override
    public List<salon> getAllSalon() {
        return salonRepository.findAll();
    }

    @Override
    public salon getSalonById(Long salonId) throws Exception {
        salon salon = salonRepository.findById(salonId).orElse(null);
        if (salon == null) {
            throw new Exception("Salon does not exist");
        }
        return salon;
    }

    @Override
    public salon getSalonByOwnerId(Long ownerId) {
        salon salon = salonRepository.findByOwnerId(ownerId);
        if (salon == null) {
            throw new RuntimeException("No salon found for owner ID: " + ownerId);
        }
        return salon;
    }

    @Override
    public List<salon> searchSalonByCity(String city) {
        List<salon> salons = salonRepository.searchSalon(city);
        if (salons == null || salons.isEmpty()) {
            throw new RuntimeException("No salons found in city: " + city);
        }
        return salons;
    }
}