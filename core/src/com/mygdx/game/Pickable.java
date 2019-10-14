package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Артём on 24.12.2017.
 */

public class Pickable {
    public static final int COFFEE=1;
    public static final int COIN=2;
    int pickupType;
    int value;
    Vector2 pos=new Vector2();


    public Pickable(int type){
        pickupType=type;
        switch (pickupType){
            case COFFEE:
                value=20;
                break;
            case COIN:
                value=1;
                break;
        }
    }


}
