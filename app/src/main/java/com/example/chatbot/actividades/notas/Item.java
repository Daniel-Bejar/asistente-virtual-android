package com.example.chatbot.actividades.notas;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_tableName")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String itemTitle;
    private final String itemDescription;
    private final String itemTime;

    public Item(String itemTitle, String itemDescription, String itemTime) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemTime = itemTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemTime() {
        return itemTime;
    }
}