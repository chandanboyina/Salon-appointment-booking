package com.chandan.controller;

import com.chandan.mapper.SalonMapper;
import com.chandan.model.salon;
import com.chandan.payload.dto.SalonDTO;
import com.chandan.payload.dto.UserDTO;
import com.chandan.service.salonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final salonService SalonService;

    // http://localhost:5002/api/salons
    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        salon Salon=SalonService.createsalon(salonDTO, userDTO);
        SalonDTO salonDTO1= SalonMapper.mapToDTO(Salon);


        return ResponseEntity.ok(salonDTO1);
    }

    // http://localhost:5002/api/salons/2
    @PatchMapping("/{id}")
    public ResponseEntity<SalonDTO> updateSalon(
            @PathVariable("id") Long salonId, @RequestBody SalonDTO salonDTO) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        salon Salon=SalonService.updatesalon(salonDTO, userDTO, salonId);
        SalonDTO salonDTO1= SalonMapper.mapToDTO(Salon);


        return ResponseEntity.ok(salonDTO1);
    }

    // https://localhost:5002/api/salons
    @GetMapping()
    public ResponseEntity<List<SalonDTO>> getSalon() throws Exception {

        List<salon> salons=SalonService.getAllSalon();
        List<SalonDTO> salonDTOS=salons.stream().map((Salon)->
        {
            SalonDTO salonDTO= SalonMapper.mapToDTO(Salon);
            return salonDTO;
        }).toList();
        return ResponseEntity.ok(salonDTOS);


    }

    // https://localhost:5002/api/salons/5
    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {


        salon Salon=SalonService.getSalonById(salonId);
        SalonDTO salonDTO= SalonMapper.mapToDTO(Salon);
        return ResponseEntity.ok(salonDTO);



    }

    //  https://localhost:5002/api/salons/search?city=mumbai
    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(
            @RequestParam("city") String city
    ) throws Exception {

        List<salon> salons=SalonService.searchSalonByCity(city);
        List<SalonDTO> salonDTOS=salons.stream().map((Salon)->
        {
            SalonDTO salonDTO= SalonMapper.mapToDTO(Salon);
            return salonDTO;
        }).toList();
        return ResponseEntity.ok(salonDTOS);


    }

    // https://localhost:5002/api/salons/5
    @GetMapping("/owner")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long salonId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        salon Salon=SalonService.getSalonByOwnerId(userDTO.getId());
        SalonDTO salonDTO= SalonMapper.mapToDTO(Salon);
        return ResponseEntity.ok(salonDTO);



    }
}
