package com.waresense.backend.service;

import com.waresense.backend.dto.WarehouseDto;
import com.waresense.backend.entity.Shelf;
import com.waresense.backend.entity.WarehouseZone;
import com.waresense.backend.repository.ShelfRepository;
import com.waresense.backend.repository.WarehouseZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseZoneRepository zoneRepository;
    private final ShelfRepository shelfRepository;

    @Transactional
    public WarehouseDto.ZoneDto createZone(WarehouseDto.ZoneDto dto) {
        WarehouseZone zone = WarehouseZone.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .build();
        WarehouseZone savedZone = zoneRepository.save(zone);
        
        return WarehouseDto.ZoneDto.builder()
                .id(savedZone.getId())
                .name(savedZone.getName())
                .code(savedZone.getCode())
                .build();
    }

    public List<WarehouseDto.ZoneDto> getAllZones() {
        return zoneRepository.findAll().stream()
                .map(zone -> WarehouseDto.ZoneDto.builder()
                        .id(zone.getId())
                        .name(zone.getName())
                        .code(zone.getCode())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public WarehouseDto.ShelfDto createShelf(WarehouseDto.ShelfDto dto) {
        WarehouseZone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        Shelf shelf = Shelf.builder()
                .code(dto.getCode())
                .zone(zone)
                .capacityTier(dto.getCapacityTier())
                .build();
        
        Shelf savedShelf = shelfRepository.save(shelf);

        return WarehouseDto.ShelfDto.builder()
                .id(savedShelf.getId())
                .code(savedShelf.getCode())
                .zoneId(savedShelf.getZone().getId())
                .capacityTier(savedShelf.getCapacityTier())
                .build();
    }
}
