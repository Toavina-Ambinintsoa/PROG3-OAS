package com.example.restservice.Repository;
import com.example.restservice.DataSource;
import com.example.restservice.entity.Enum.IngredientType;
import com.example.restservice.entity.Ingredient;
import com.example.restservice.entity.StockValue;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class IngredientRepository {
    private final DataSource dataSource;

    public IngredientRepository() {
        this.dataSource = new DataSource();
    }

    public List<Ingredient> findAll() {

        String sql = "SELECT id, name, price, category FROM ingredient";
        List<Ingredient> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        IngredientType.valueOf(rs.getString("category")),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Optional<Ingredient> findById(Long id) {

        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";
        Ingredient ingredient = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ingredient = new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        IngredientType.valueOf(rs.getString("category")),
                        rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(ingredient);
    }

    public Optional<StockValue> findStockById(Long id, LocalDateTime at, String unit) {

        String sql = """
            SELECT COALESCE(SUM(
                CASE WHEN sm.type = 'IN' THEN sm.quantity
                     WHEN sm.type = 'OUT' THEN -sm.quantity
                END), 0) AS stock
            FROM stock_movement sm
            WHERE sm.id_ingredient = ?
              AND sm.unit = ?
              AND sm.creation_datetime <= ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.setString(2, unit);
            stmt.setTimestamp(3, Timestamp.valueOf(at));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                double stock = rs.getDouble("stock");

                return Optional.of(new StockValue(unit, stock));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}