package com.anton46.collectionitempicker;

public class Item {

    public String id;
    public String text;
    public boolean isSelected;

    public Item(String id, String text) {
        this(id, text, false);
        this.id = id;
        this.text = text;
    }

    public Item(String id, String text, boolean isSelected) {
        this.id = id;
        this.text = text;
        this.isSelected = isSelected;
    }
}
