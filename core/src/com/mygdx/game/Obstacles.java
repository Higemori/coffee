package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Артём on 23.12.2017.
 */

public class Obstacles {
    private ArrayList<Vector2> obstacles=new ArrayList<Vector2>();
    private Vector2 lastObstaclePos=new Vector2();

    public Obstacles(){
        Vector2 tmp=new Vector2((float) (800+Math.random()*600),50);
        obstacles.add(tmp);
        lastObstaclePos=tmp;
    }

    public void addObstacle(){
        Vector2 obstaclePos=new Vector2();
        obstaclePos.y=50;
        obstaclePos.x=lastObstaclePos.x+(float) (500+Math.random()*600);
        lastObstaclePos=obstaclePos;
        obstacles.add(obstaclePos);
    }

    public void removeObstacle(){
        obstacles.remove(0);
    }

    public ArrayList<Vector2> getObstacles() {
        return obstacles;
    }

    public Vector2 getLastObstaclePos() {
        return lastObstaclePos;
    }
}
