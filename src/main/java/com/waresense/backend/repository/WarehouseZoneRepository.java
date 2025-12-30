package com.waresense.backend.repository;

import com.waresense.backend.entity.WarehouseZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseZoneRepository extends JpaRepository<WarehouseZone, Long> {
    Optional<WarehouseZone> findByCode(String code);
}
