package com.optimus.animusmagic;

public enum EnchantError {

    NONE(""),
    MANA("You don't have enough mana to do this!"),
    LEVEL_TOO_HIGH("This is higher than the max level for the enchant!"),
    WRONG_ITEM("You need to hold a specific item to do this!"),
    LEVEL("Further study is needed for this enchant! Check your animus leveling menu for more info!");

    String message;

    EnchantError(String message){
        this.message = message;
    }

    public String getMessage(){ return message; }

}
