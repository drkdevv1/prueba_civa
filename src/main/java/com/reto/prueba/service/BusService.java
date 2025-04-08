package com.reto.prueba.service;

import com.reto.prueba.dto.BusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BusService {
    Page<BusDTO> getAllBuses(Pageable pageable);

    BusDTO getBusById(Long id);
}