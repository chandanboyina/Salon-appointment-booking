package com.chandan.service;

import com.chandan.dto.CategoryDTO;
import com.chandan.dto.SalonDTO;
import com.chandan.dto.ServiceDTO;
import com.chandan.model.ServiceOffering;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);
    ServiceOffering updateService(Long serviceid, ServiceOffering service) throws Exception;
    Set<ServiceOffering> getAllServicesBySalonId(Long SalonId,Long categoryId);
    Set<ServiceOffering> getServicesByIds(Set<Long> ids);
    ServiceOffering getServiceById(Long id) throws Exception;

}
