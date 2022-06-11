package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Drop game;

    private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;

    private Rectangle bucket;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private int score;

    public GameScreen(Drop game){
        this.game = game;
        score = 0;

        dropImage = new Texture(Gdx.files.internal("drop.png"));
        bucketImage = new Texture(Gdx.files.internal("rapunzel.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        bucket = new Rectangle();
        bucket.x = 400 - 32;
        bucket.y = 20;
        bucket.width = 10;
        bucket.height = 10;

        raindrops = new Array<>();
        spawnRaindrop();

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0.294f, 0.552f, 1);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch,"Drop Collected: " + score,0,480);
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            System.out.println(touchPos.X + " " + touchPos.Y);
            bucket.x = touchPos.x - 32;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();
        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
                score++;
            }
        }
        game.batch.begin();
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();

    }
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
}
