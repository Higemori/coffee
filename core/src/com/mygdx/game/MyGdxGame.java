package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyGdxGame extends com.badlogic.gdx.Game {
	SpriteBatch batch;
	FPSLogger fpsLogger;
	OrthographicCamera camera;
	FitViewport viewport;
	AssetManager assetManager = new AssetManager();

	public static final int screenWidth = 800;
	public static final int screenHeight = 480;

	public MyGdxGame(){
		fpsLogger = new FPSLogger();
		camera = new OrthographicCamera();
		camera.position.set(screenWidth/2, screenHeight/2, 0);
		viewport = new FitViewport(screenWidth,	 screenHeight, camera);
	}

	@Override
	public void create () {
		assetManager.load("background.png", Texture.class);
		assetManager.load("coffee.png",Texture.class);
		assetManager.load("coffee_bar.png",Texture.class);
		assetManager.load("coin.png",Texture.class);
		assetManager.load("coin_m.png",Texture.class);
		assetManager.load("dmg.png",Texture.class);
		assetManager.load("groundGrass.png", Texture.class);
		assetManager.load("pl150_1.png",Texture.class);
		assetManager.load("pl150_2.png",Texture.class);
		assetManager.load("pl150_3.png",Texture.class);
		assetManager.load("shadow.png",Texture.class);
		assetManager.load("coffee_bar.png",Texture.class);
		assetManager.load("coffee_mania.png",Texture.class);
		assetManager.load("game_over.png",Texture.class);
		assetManager.load("coin.png",Texture.class);
		assetManager.load("coffee.png",Texture.class);
		assetManager.load("coffee_m.png",Texture.class);
		assetManager.load("coin_m.png",Texture.class);
		assetManager.load("shadow_m.png",Texture.class);
		assetManager.load("font.fnt", BitmapFont.class);
		assetManager.load("dead.png", Texture.class);
		assetManager.load("old1.png", Texture.class);
		assetManager.load("old2.png", Texture.class);
		assetManager.load("old3.png", Texture.class);
		assetManager.load("old4.png", Texture.class);
		assetManager.load("music.mp3", Music.class);
		assetManager.load("back.png", Texture.class);
		assetManager.load("sound_on.png", Texture.class);
		assetManager.load("sound_off.png", Texture.class);
		assetManager.finishLoading();

		batch = new SpriteBatch();
		setScreen(new GameScene(this));
	}

	@Override
	public void render () {
		fpsLogger.log();
		super.render();
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width,height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}
}
