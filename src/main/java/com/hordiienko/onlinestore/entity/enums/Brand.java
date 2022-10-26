package com.hordiienko.onlinestore.entity.enums;

public enum Brand {
    AMERICAN_GIANT("American Giant"),
    MATE_THE_LABEL("MATE The Label"),
    HACKWITH_DESIGN_HOUSE("Hackwith Design House"),
    TODD_SHELTON("Todd Shelton"),
    LACAUSA("LACAUSA"),
    GAMINE_WORKWEAR("Gamine Workwear");

    private final String name;

    Brand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Brand getById(int id) {
        return Brand.values()[id];
    }
}
