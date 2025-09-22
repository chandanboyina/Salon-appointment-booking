package com.chandan.payload.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class SalonDTO {

    private Long id;


    private String name;


    private List<String> images;


    private String address;


    private String phone;


    private String email;


    private String city;


    private Long ownerId;


    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;


    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;
}
