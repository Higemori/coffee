package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Артём on 25.12.2017.
 */

public class Purchase {
    private int value;
    private Item item;
    private boolean bought;
    private boolean use;

    public Purchase(Item item,int value, boolean bought, boolean use){
        this.bought=bought;
        this.use=use;
    }

    public int getValue() {
        return value;
    }

    public Item getItem() {
        return item;
    }

    public boolean isBought() {
        return bought;
    }

    public boolean isUse() {
        return use;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
