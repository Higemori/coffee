package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Артём on 24.12.2017.
 */

public class Ground {
    ArrayList<Vector2> list=new ArrayList<Vector2>();
    Vector2 lastGroundPos=new Vector2();

    public Ground(){
        list.add(new Vector2(0,0));
        list.add(new Vector2(808,0));
        lastGroundPos.x=808;
    }

    public void addGround(){
        lastGroundPos.x=lastGroundPos.x+808;
        list.add(new Vector2(lastGroundPos.x,0));
    }

    public void removeGround(){
        list.remove(0);
    }

    public ArrayList<Vector2> getList() {
        return list;
    }
}
