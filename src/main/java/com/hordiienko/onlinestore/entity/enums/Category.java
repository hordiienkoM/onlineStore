package com.hordiienko.onlinestore.entity.enums;

public enum Category {
    SWEATER("Sweater"),
    DRESS("Dress"),
    HAT("Hat"),
    COAT("Coat"),
    SHIRT("Shirt");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public static Category getById(int id) {
        return Category.values()[id];
    }

    public String getName() {
        return name;
    }
}
