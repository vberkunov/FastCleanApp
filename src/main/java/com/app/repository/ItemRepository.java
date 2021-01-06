package com.app.repository;

import com.app.entity.Item;


import java.util.List;

public final class ItemRepository {

    private List<Item> items;
    private final static ItemRepository INSTANCE = new ItemRepository();


    private ItemRepository() {
    }

    public static ItemRepository getInstance() {
        return INSTANCE;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
