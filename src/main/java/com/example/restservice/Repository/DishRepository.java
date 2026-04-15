package com.example.restservice.Repository;
import com.example.restservice.DataSource;
import com.example.restservice.entity.Dish;
import com.example.restservice.entity.Enum.DishType;
import com.example.restservice.entity.Enum.IngredientType;
import com.example.restservice.entity.Ingredient;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DishRepository {
    private final DataSource dataSource;

    public DishRepository() {
        this.dataSource = new DataSource();
    }

    public List<Dish> findAll() {
        String sql = "SELECT id, name, dish_type, selling_price FROM dish";
        List<Dish> dishes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Dish dish = new Dish(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("selling_price"),
                        DishType.valueOf(rs.getString("dish_type")),
                        null
                );

                dish.setIngredients(findIngredientsByDishId(dish.getId()));
                dishes.add(dish);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public Optional<Dish> findById(Long id) {

        String sql = "SELECT id, name, dish_type, selling_price FROM dish WHERE id = ?";
        Dish dish = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                dish = new Dish(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("selling_price"),
                        DishType.valueOf(rs.getString("dish_type")),
                        null
                );

                dish.setIngredients(findIngredientsByDishId(dish.getId()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(dish);
    }

    private List<Ingredient> findIngredientsByDishId(Integer dishId) {

        String sql = """
            SELECT i.id, i.name, i.category, i.price
            FROM ingredient i
            JOIN dish_ingredient di ON di.id_ingredient = i.id
            WHERE di.id_dish = ?
            """;

        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dishId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                ingredients.add(new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        IngredientType.valueOf(rs.getString("category")),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    public void updateIngredients(Long dishId, List<Ingredient> ingredients) {

        String deleteSql = "DELETE FROM dish_ingredient WHERE id_dish = ?";
        String checkSql = "SELECT COUNT(*) FROM ingredient WHERE id = ?";
        String insertSql = "INSERT INTO dish_ingredient (id_dish, id_ingredient) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement delete = conn.prepareStatement(deleteSql)) {
            delete.setLong(1, dishId);
            delete.executeUpdate();
            for (Ingredient ing : ingredients) {

                try (PreparedStatement check = conn.prepareStatement(checkSql)) {
                    check.setInt(1, ing.getId());

                    try (ResultSet rs = check.executeQuery()) {

                        if (rs.next() && rs.getInt(1) > 0) {

                            try (PreparedStatement insert = conn.prepareStatement(insertSql)) {
                                insert.setLong(1, dishId);
                                insert.setInt(2, ing.getId());
                                insert.executeUpdate();
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}