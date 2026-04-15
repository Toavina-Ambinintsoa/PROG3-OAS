package com.example.restservice.Controllers;
import com.example.restservice.Repository.IngredientRepository;
import com.example.restservice.Repository.StockMovementRepository;
import com.example.restservice.entity.StockMovement;
import com.example.restservice.services.StockMovementCreationService;
import com.example.restservice.services.StockMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ingredients/{id}/stockMovements")
public class StockMovementController {

    private final StockMovementRepository stockMovementRepository;
    private final IngredientRepository ingredientRepository;

    public StockMovementController(StockMovementRepository stockMovementRepository,
                                   IngredientRepository ingredientRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public ResponseEntity<?> getStockMovements(
            @PathVariable Long id,
            @RequestParam Instant from,
            @RequestParam Instant to) {

        if (ingredientRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(404)
                    .body("Ingredient.id=" + id + " is not found");
        }

        List<StockMovementService> result = stockMovementRepository
                .findByIngredientIdBetween(id, from, to)
                .stream()
                .map(StockMovementService::new)
                .toList();

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> addStockMovements(
            @PathVariable Long id,
            @RequestBody List<StockMovementCreationService> creations) {

        if (ingredientRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(404)
                    .body("Ingredient.id=" + id + " is not found");
        }

        List<StockMovement> toSave = creations.stream().map(dto -> {
            StockMovement sm = new StockMovement();
            sm.setCreatedAt(Instant.now());
            sm.setUnit(dto.getUnit());
            sm.setValue(dto.getValue());
            sm.setType(dto.getType());
            return sm;
        }).toList();

        List<StockMovementService> saved = stockMovementRepository.saveAll(id, toSave)
                .stream()
                .map(StockMovementService::new)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}