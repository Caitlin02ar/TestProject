package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public MainMenuScreen mainmenu;
    public GameScreen gameScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        mainmenu = new MainMenuScreen(this);
        gameScreen = new GameScreen(this);
        this.setScreen(mainmenu);

    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
        mainmenu.dispose();
        gameScreen.dispose();
    }
}
