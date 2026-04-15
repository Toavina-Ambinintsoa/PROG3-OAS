package entity;

import entity.Enum.IngredientType;

public class Ingredient {
    private Integer id;
    private String name;
    private String category;
    private IngredientType type;
    private double price;

    public Ingredient() {}

    public Ingredient(Integer id, String name, String category, IngredientType type, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.price = price;
    }

    public Ingredient(Integer id, String name, IngredientType type, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public IngredientType getType() {
        return type;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
