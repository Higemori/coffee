package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Артём on 25.12.2017.
 */

public class ShopScene extends ScreenAdapter{
    MyGdxGame game;
    Batch batch;
    OrthographicCamera camera;
    AssetManager manager;
    Texture santaHat, candy,santaHatIcon,Candy,Icon;
    Animation player;

    public ShopScene(MyGdxGame myGdxGame) {
        game=myGdxGame;
        batch=myGdxGame.batch;
        camera=myGdxGame.camera;
        manager=myGdxGame.assetManager;
        
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    public void update(){
        float delta = Gdx.graphics.getDeltaTime();

    }

    public void draw(){

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
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
