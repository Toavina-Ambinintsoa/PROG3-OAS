package entity;

import entity.Enum.MovementType;
import entity.Enum.StockUnit;

import java.time.Instant;

public class StockMovement {
    private Long id;
    private Instant createdAt;
    private StockUnit unit;
    private double value;
    private MovementType type;

    public StockMovement() {}

    public StockMovement(Long id, Instant createdAt, StockUnit unit, double value, MovementType type) {
        this.id = id;
        this.createdAt = createdAt;
        this.unit = unit;
        this.value = value;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public StockUnit getUnit() { return unit; }
    public void setUnit(StockUnit unit) { this.unit = unit; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public MovementType getType() { return type; }
    public void setType(MovementType type) { this.type = type; }
}
