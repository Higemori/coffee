package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.awt.Button;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Артём on 23.12.2017.
 */

public class GameScene extends ScreenAdapter {
    MyGdxGame game;
    Batch batch;
    OrthographicCamera camera;
    AssetManager manager;
    Texture background;
    Animation player;
    Texture player1,player2,player3,dead;
    Texture ground;
    Texture shadow;
    float terrainOffset;
    float objectOffset;
    float playerSpeed;
    Vector2 playerVelocity=new Vector2();
    Vector2 playerPosition=new Vector2();
    Vector2 playerDefaultPosition=new Vector2();
    Texture dmg, coffee, coin;
    Texture coffeeBar;
    Texture logo, gameOver;
    Texture coffeeM, shadowM,coinM;
    float coffeeAmount=50;
    int coffeePercentage;
    boolean jump=false;
    boolean trigger=false;
    GameState state=GameState.INIT;
    float animTime=0;
    Obstacles obstacles = new Obstacles();
    Ground groundArray=new Ground();
    Rectangle playerRect=new Rectangle();
    Rectangle obstRect=new Rectangle();
    Rectangle pickableRect=new Rectangle();
    ArrayList<Pickable> list=new ArrayList<Pickable>();
    Animation old;
    BitmapFont font;
    Rectangle soundBody=new Rectangle();
    boolean spawned=false;
    int tmpCoins;
    float score=0;
    int maxScore;
    int coins;
    Music music;
    Preferences preferences=Gdx.app.getPreferences("preferences");
    Texture back, sound, shopIcon;
    Texture emty, candy, santaHad;
    boolean musicOn=true;
    Vector3 touch=new Vector3();

    public GameScene (MyGdxGame myGdxGame){
        game=myGdxGame;
        batch=myGdxGame.batch;
        camera=myGdxGame.camera;
        manager=myGdxGame.assetManager;
        background=manager.get("background.png",Texture.class);
        player1=manager.get("pl150_1.png",Texture.class);
        player2=manager.get("pl150_2.png",Texture.class);
        player3=manager.get("pl150_3.png",Texture.class);
        ground=manager.get("groundGrass.png",Texture.class);
        shadow=manager.get("shadow.png",Texture.class);
        dmg=manager.get("dmg.png",Texture.class);
        coffeeBar=manager.get("coffee_bar.png",Texture.class);
        player=new Animation(0.08f, player1,player2,player3);
        old=new Animation(0.08f,manager.get("old1.png",Texture.class),manager.get("old2.png",Texture.class),
        manager.get("old3.png",Texture.class),manager.get("old4.png",Texture.class));
        old.setPlayMode(Animation.PlayMode.LOOP);
        logo=manager.get("coffee_mania.png",Texture.class);
        gameOver=manager.get("game_over.png",Texture.class);
        coffee=manager.get("coffee.png",Texture.class);
        coin=manager.get("coin.png",Texture.class);
        shadowM=manager.get("shadow_m.png",Texture.class);
        coffeeM=manager.get("coffee_m.png",Texture.class);
        coinM=manager.get("coin_m.png",Texture.class);
        font=manager.get("font.fnt",BitmapFont.class);
        dead=manager.get("dead.png",Texture.class);
        sound=manager.get("sound_on.png",Texture.class);
        back=manager.get("back.png",Texture.class);
        music=Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
        maxScore=preferences.getInteger("record",0);
        coins=preferences.getInteger("coin",0);
        player.setPlayMode(Animation.PlayMode.LOOP);
        playerDefaultPosition.set(30,50);
        playerPosition.set(30,50);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateScene();
        drawScene();
    }

    public void updateScene(){
        float deltaTime=Gdx.graphics.getDeltaTime();
        animTime+=deltaTime;
        if(state==GameState.INIT){
            camera.position.x=playerPosition.x+370;
            soundBody.set(camera.position.x-375,420,50,50);
            if(playerPosition.x>groundArray.getList().get(1).x){
                groundArray.addGround();
            }
            if(playerPosition.x>groundArray.getList().get(1).x+200){
                groundArray.removeGround();
            }
            playerPosition.x+=400*deltaTime;

            if(Gdx.input.justTouched()){
                touch.set(Gdx.input.getX(),Gdx.input.getY(),0);
                camera.unproject(touch);
                if(soundBody.contains(touch.x,touch.y)){
                    if(musicOn==true){
                        music.pause();
                        sound=manager.get("sound_off.png",Texture.class);
                        musicOn=false;
                    }else{
                        music.play();
                        sound=manager.get("sound_on.png",Texture.class);
                        musicOn=true;
                    }

                }else {
                    resetScene();
                    state=GameState.ACTIVE;
                }
            }
        }



        if(state==GameState.ACTIVE){
            score+=deltaTime;
            camera.position.x=playerPosition.x+370;
            if(playerPosition.x>groundArray.getList().get(1).x){
                groundArray.addGround();
            }
            if(playerPosition.x>groundArray.getList().get(1).x+200){
                groundArray.removeGround();
            }
            playerRect.set(playerPosition.x,playerPosition.y,100,100);
            obstRect.set(obstacles.getObstacles().get(0).x,obstacles.getObstacles().get(0).y,50,50);
            if(!list.isEmpty()){
                pickableRect.set(list.get(0).pos.x,list.get(0).pos.y,40,40);
            }


            if(coffeeAmount<25){
                playerPosition.x+=300*deltaTime;
            } else if(coffeeAmount<50){
                playerPosition.x+=400*deltaTime;
            }else if(coffeeAmount<75){
                playerPosition.x+=500*deltaTime;
            }else {
                playerPosition.x+=600*deltaTime;
            }

            if(playerRect.overlaps(obstRect)){
                obstacles.removeObstacle();
                spawned=false;
                if(coffeeAmount>0){
                    coffeeAmount-=15;
                }
            }

            if(!list.isEmpty()){
                if(playerRect.overlaps(pickableRect)){
                    if(list.get(0).pickupType==1){
                        coffeeAmount+=list.get(0).value;
                    } else {
                        tmpCoins+=1;
                    }
                    list.remove(0);
                }
            }


            if(Gdx.input.justTouched()){
                if(jump==false){
                    jump=true;
                }
            }

            if(jump==true){
                if(trigger==false){
                    playerPosition.y+=350*deltaTime;
                    if(playerPosition.y>200) trigger=true;
                } else{
                    playerPosition.y-=250*deltaTime;
                    if(playerPosition.y<playerDefaultPosition.y){
                        playerPosition.y=playerDefaultPosition.y;
                        trigger=false;
                        jump=false;
                    }
                }
            }

            if(obstacles.getObstacles().get(0).x<playerPosition.x-100){
                obstacles.removeObstacle();
                spawned=false;
            }
            if(!list.isEmpty()){
                if(list.get(0).pos.x<playerPosition.x-100){
                    list.remove(0);
                }
            }
            if(obstacles.getObstacles().get(0).x<camera.position.x){
                obstacles.addObstacle();
                if(MathUtils.randomBoolean()){
                    if(spawned==false){
                        if(coffeeAmount<50){
                            Pickable tmpPick = new Pickable(1);
                            tmpPick.pos.x=(float)(obstacles.getObstacles().get(1).x+175+Math.random()*300);
                            tmpPick.pos.y=(float)(50+Math.random()*150);
                            list.add(tmpPick);
                        } else{
                            Pickable tmpPick = new Pickable(2);
                            tmpPick.pos.x=(float)(obstacles.getObstacles().get(1).x+175+Math.random()*300);
                            tmpPick.pos.y=(float)(50+Math.random()*150);
                            list.add(tmpPick);
                        }
                    }
                }
                spawned=true;
            }

            if(coffeeAmount>0){
                coffeeAmount-=2.5*deltaTime;
            }
            if(coffeeAmount<0){
                coffeeAmount=0;
                state=GameState.GAME_OVER;
            }

            coffeePercentage=(int)(550*coffeeAmount/100);

        }

        if(state==GameState.GAME_OVER){
            if(score>maxScore){
                maxScore=(int)score;
                preferences.putInteger("record",maxScore);
            }
            coins=coins+tmpCoins;
            preferences.putInteger("coin",coins);
            preferences.flush();
            tmpCoins=0;
            if(Gdx.input.justTouched()){
                resetScene();
                state=GameState.INIT;
            }
        }



    }




    public void drawScene(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background,camera.position.x-400,0);


        if(state==GameState.INIT){
            batch.draw(sound, camera.position.x-375,420);
            batch.draw(logo,camera.position.x-400,0);
            font.draw(batch,"Record: "+(int)maxScore ,camera.position.x-50,450);
            font.draw(batch,""+coins ,camera.position.x+325,458);
            batch.draw(coinM,camera.position.x+275,420);
            for(Vector2 vec:groundArray.getList()){
                batch.draw(ground, vec.x,vec.y);
            }
            if(playerPosition.y<75) {
                batch.draw(shadow, playerPosition.x, 50);
            }
            batch.draw((Texture)player.getKeyFrame(animTime),playerPosition.x,playerPosition.y);
        }

        if(state==GameState.ACTIVE){
            for(Vector2 vec:groundArray.getList()){
                batch.draw(ground, vec.x,vec.y);
            }
            if(playerPosition.y<75) {
                batch.draw(shadow, playerPosition.x, 50);
            }
            batch.draw((Texture)player.getKeyFrame(animTime),playerPosition.x,playerPosition.y);
            for(Vector2 vec:obstacles.getObstacles()){
                batch.draw(dmg, vec.x,vec.y);
            }
            batch.setColor(Color.BLACK);
            batch.draw(coffeeBar,camera.position.x-300,420);
            batch.setColor(Color.WHITE);
            batch.draw(coffeeBar,camera.position.x-300,420,0,0, coffeePercentage,50);
            batch.draw(coffeeM,camera.position.x-350,420);
            batch.draw(coinM,camera.position.x+275,420);
            for(Pickable pickable:list){
                if(pickable.pickupType==1){
                    batch.draw(coffee, pickable.pos.x,pickable.pos.y);
                }else{
                    batch.draw(coin,pickable.pos.x,pickable.pos.y);
                }
                if(pickable.pos.y<75){
                    batch.draw(shadowM,pickable.pos.x,50);
                }
            }

            font.draw(batch,""+tmpCoins ,camera.position.x+325,458);
            font.draw(batch,""+(int)score ,camera.position.x-10,400);
        }

        if(state==GameState.GAME_OVER){
            batch.draw(gameOver,camera.position.x-400,0);
            font.draw(batch,""+(int)score ,camera.position.x-10,450);
            for(Vector2 vec:groundArray.getList()){
                batch.draw(ground, vec.x,vec.y);
            }
            batch.draw(shadow, playerPosition.x, 50);
            batch.draw(dead,playerPosition.x,50);
        }
        batch.draw((Texture)old.getKeyFrame(animTime),camera.position.x-400,0);
        batch.end();
    }

    private void resetScene(){
        tmpCoins=0;
        score=0;
        groundArray=new Ground();
        obstacles = new Obstacles();
        list = new ArrayList<Pickable>();
        coffeeAmount=50;
        playerDefaultPosition.set(30,50);
        playerPosition.set(playerDefaultPosition.x,playerDefaultPosition.y);
    }




    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        music.play();
    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }
}
