package com.project.services;

import com.project.models.Position;
import com.project.repositories.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    /**
     * Get all positions
     *
     * @return list of positions.
     */
    public List<Position> getPositions() {
        log.info("Position get request");
        return this.positionRepository.findAll();
    }
}
