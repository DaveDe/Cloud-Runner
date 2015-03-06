package com.noodles.stickrun.stickRun;

import com.noodles.stickrun.framework.Game;
import com.noodles.stickrun.framework.Graphics;
import com.noodles.stickrun.framework.Input;
import com.noodles.stickrun.framework.Pixmap;
import com.noodles.stickrun.framework.Screen;

import java.util.List;

public class MainMenuScreen extends Screen {

    Background b;
    Background b2;
    Pixmap soundPixmap;


    public MainMenuScreen(Game game) {
        super(game);
        b = new Background(0);
        b.setDx(-5);
        b2 = new Background(960);
        b2.setDx(-5);
    }

    public void update(float deltaTime) {

        if(Level.sound) {
            soundPixmap = Assets.soundOn;
        }else{
            soundPixmap = Assets.soundOff;
        }
        b.update();
        b2.update();
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                if(inBounds(event, 160, 150, 147, 23) ) {
                    if(Level.sound) {
                        Assets.buttonSound.play(1);
                    }
                    game.setScreen(new GameScreen(game));
                    return;
                }
                if (inBounds(event, 160, 250, 150, 23)) {
                    if(Level.sound) {
                        Assets.buttonSound.play(1);
                    }
                    game.setScreen(new LevelSelection(game));
                    return;
                }
                if (inBounds(event, 430, 270, 50, 50)) {
                    if(!Level.sound){
                        Level.sound = true;
                        soundPixmap = Assets.soundOn;
                        Level.saveSound(game.getFileIO());
                    }else{
                        Level.sound = false;
                        soundPixmap = Assets.soundOff;
                        Level.saveSound(game.getFileIO());
                    }
                }
            }
        }
    }
    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.backGround1, b.getX(), 0);
        g.drawPixmap(Assets.backGround1, b2.getX(), 0);
        g.drawPixmap(Assets.title, 100, 20);
        g.drawPixmap(Assets.playButton, 160, 150);
        g.drawPixmap(Assets.levelSelectButton, 160, 250);
        g.drawPixmap(soundPixmap, 430, 270);
    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
