package com.noodles.stickrun.stickRun;

import com.noodles.stickrun.framework.Game;
import com.noodles.stickrun.framework.Graphics;
import com.noodles.stickrun.framework.Input;
import com.noodles.stickrun.framework.Screen;

import java.util.List;

public class LevelSelection extends Screen {

Background b;
Background b2;

    public LevelSelection(Game game) {
        super(game);
        b = new Background(-480);
        b.setDx(-5);
        b2 = new Background(480);
        b2.setDx(-5);
    }
//150x24 level buttons
    @Override
    public void update(float deltaTime) {

        b.update();
        b2.update();

        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP)
                if(inBounds(event, 0, 0, 80, 23)) {
                    if(Level.sound){
                        Assets.buttonSound.play(1);
                    }
                game.setScreen(new MainMenuScreen(game));
                    return;
            }
            if(inBounds(event, 180, 80, 150, 24)) {
                if(Level.sound){
                    Assets.buttonSound.play(1);
                }
                Level.currentLevel = 1;
                Level.currentHighScore = 0;
                Level.loadHighScore(game.getFileIO());
                game.setScreen(new GameScreen(game));
                return;
            }
            if(inBounds(event, 180, 180, 150, 24)) {
                if(Level.sound){
                    Assets.buttonSound.play(1);
                }
                Level.currentLevel = 2;
                Level.currentHighScore = 0;
                Level.loadHighScore(game.getFileIO());
                game.setScreen(new GameScreen(game));
                return;
            }
            if(inBounds(event, 180, 280, 150, 24)) {
                if(Level.sound){
                    Assets.buttonSound.play(1);
                }
                Level.currentLevel = 3;
                Level.currentHighScore = 0;
                Level.loadHighScore(game.getFileIO());
                game.setScreen(new GameScreen(game));
                return;
            }
        }
    }



    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.backGround1,b.getX() , 0);
        g.drawPixmap(Assets.backGround1,b2.getX() , 0);
        g.drawPixmap(Assets.menuButton, 0, 0);
        g.drawPixmap(Assets.beginner, 180, 80);
        g.drawPixmap(Assets.intermediate, 180, 180);
        g.drawPixmap(Assets.expert, 180, 280);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }
}
