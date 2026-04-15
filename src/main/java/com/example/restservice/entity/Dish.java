package com.example.restservice.entity;

import com.example.restservice.entity.Enum.DishType;

import java.util.List;

public class Dish {
    private Integer id;
    private String name;
    private double sellingPrice;
    private DishType type;
    private List<Ingredient> ingredients;

    public Dish() {}

    public Dish(Integer id, String name, double sellingPrice, DishType type,List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.type = type;
        this.ingredients = ingredients;
    }

    public Dish(long id, String name, String type, double sellingPrice, Object ingredients) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public DishType getType() {
        return type;
    }

    public void setType(DishType type) {
        this.type = type;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
