package com.ihsan.images;

public class ItemModel {

    private String id, itemName, column1, column2;

    public ItemModel(String id, String itemName, String column1, String column2) {
        this.id = id;
        this.itemName = itemName;
        this.column1 = column1;
        this.column2 = column2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }
}
