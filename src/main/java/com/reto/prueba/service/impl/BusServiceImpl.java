package com.reto.prueba.service.impl;

import com.reto.prueba.dto.BusDTO;
import com.reto.prueba.model.Bus;
import com.reto.prueba.repository.BusRepository;
import com.reto.prueba.service.BusService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Autowired
    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public Page<BusDTO> getAllBuses(Pageable pageable) {
        Page<Bus> buses = busRepository.findAll(pageable);
        return buses.map(this::convertToDTO);
    }

    @Override
    public BusDTO getBusById(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found with id: " + id));
        return convertToDTO(bus);
    }

    private BusDTO convertToDTO(Bus bus) {
        BusDTO dto = new BusDTO();
        dto.setId(bus.getId());
        dto.setNumeroBus(bus.getNumeroBus());
        dto.setPlaca(bus.getPlaca());
        dto.setFechaCreacion(bus.getFechaCreacion());
        dto.setCaracteristicas(bus.getCaracteristicas());
        dto.setMarcaNombre(bus.getMarca().getNombre());
        dto.setMarcaId(bus.getMarca().getId());
        dto.setActivo(bus.getActivo());
        return dto;
    }
}