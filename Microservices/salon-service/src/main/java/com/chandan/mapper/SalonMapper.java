package com.chandan.mapper;

import com.chandan.model.salon;
import com.chandan.payload.dto.SalonDTO;

public class SalonMapper {
    public static SalonDTO mapToDTO(salon Salon){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salonDTO.getId());
        salonDTO.setName(salonDTO.getName());
        salonDTO.setAddress(salonDTO.getAddress());
        salonDTO.setEmail(salonDTO.getEmail());
        salonDTO.setPhone(salonDTO.getPhone());
        salonDTO.setCity(salonDTO.getCity());
        salonDTO.setImages(salonDTO.getImages());
        salonDTO.setOwnerId(salonDTO.getOwnerId());
        salonDTO.setCloseTime(salonDTO.getCloseTime());
        salonDTO.setOpenTime(salonDTO.getOpenTime());
        return salonDTO;
    }
}
