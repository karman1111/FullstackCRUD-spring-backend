package com.project.controllers;

import com.project.models.Position;
import com.project.services.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class PositionController {
    private final PositionService positionService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Position>> getAllPositions(){
        return ResponseEntity.ok(this.positionService.getPositions());
    }
}
