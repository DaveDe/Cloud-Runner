package com.noodles.stickrun.stickRun;

import android.graphics.Color;

import com.noodles.stickrun.framework.Game;
import com.noodles.stickrun.framework.Graphics;
import com.noodles.stickrun.framework.Input;
import com.noodles.stickrun.framework.Pixmap;
import com.noodles.stickrun.framework.Screen;

import java.util.List;
import java.util.Random;

public class GameScreen extends Screen {

    enum GameState {
        Ready,
        Running,
        Paused,
        Dead,
        GameOver,
    }

    GameState state = GameState.Ready;
    World w;
    Background b1;
    Background b2;
    private int q = 0;
    private boolean restart = false;
    private int score = 10;
    private int score2;
    private String score3;
    private Blood[] b = new Blood[300];
    private int j = 0;
    private int deadTimer;
    private int jumpCount = 0;
    private Pixmap level;
    private int x;

    public GameScreen(Game game) {
        super(game);
        b1 = new Background(0);
        b2 = new Background(960);
        w = new World(game);
        if(Level.currentLevel == 1){
            level = Assets.beginnertxt;
            x = 120;
        }
        else if(Level.currentLevel == 2){
            level = Assets.intermediatetxt;
            x = 80;
        }
        else{
            level = Assets.experttxt;
            x = 160;
        }

    }

    @Override
    public void update(float deltaTime) {

        if(state == GameState.Ready)
            updateReady();
        if(state == GameState.Running)
            updateRunning();
        if(state == GameState.Paused)
            updatePaused();
        if(state == GameState.Dead)
            updateDead();
        if(state == GameState.GameOver)
            updateGameOver();

    }

    public void updateReady(){
        if(restart) {
            Level.load(game.getFileIO());
            Level.loadHighScore(game.getFileIO());
            b1 = new Background(0);
            b2 = new Background(960);
            w = new World(game);
            restart = false;
        }

        score = 10;
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_DOWN) {
               state = GameState.Running;
            }
        }
        if(q == 0) {
            for (int i = 0; i < w.getTileArray().size(); i++) {
                Tile t = w.getTileArray().get(i);
                t.setDx(0);
            }
        }
        q = 1;
        w.updateWorld();
    }

    public void updateRunning(){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();

        if(q == 1) {
            for (int j = 0; j < w.getTileArray().size(); j++) {
                Tile t = w.getTileArray().get(j);
                t.setDx(-5);
            }
        }
        q = 0;
        b1.update();
        b2.update();
        w.updateWorld();
        score++;
        score2 = score;
        score2 -= (score%10);


        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, 0, 40, 39)) {
                    state = GameState.Paused;
                }
            }
            if (event.type == Input.TouchEvent.TOUCH_DOWN
                    && !inBounds(event, 0, 0, 40, 39)) {
                    if(w.getTemp() || jumpCount == 1) {
                        w.jump();
                        if(jumpCount == 1){
                            jumpCount = 0;
                        }
                        if(w.getTemp()) {
                            jumpCount++;
                        }
                    }
            }
        }

        if(w.getGameOver()){
            state = GameState.Dead;
            w.setGameOver(false);
        }
    }

    public void updatePaused(){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, 0, 40, 39)) {
                    state = GameState.Running;
                }
                if (inBounds(event, 400, 0, 80, 23)) {
                    if(Level.sound){
                        Assets.buttonSound.play(1);
                    }
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        }
    }

    public void updateDead(){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        //initialize blood
        if(j == 0 && w.getBlood()) {
            createBloodObject();
            j++;
        }
        if(w.getBlood()) {
            for (int q = 0; q < b.length; q++) {
                b[q].update();
            }
        }
        deadTimer++;
        try {
            Thread.sleep(8);
        }catch (InterruptedException e){
        }
        if(deadTimer >= 15){
            deadTimer = 0;
            state = GameState.GameOver;
        }
    }

    public void updateGameOver(){
        if(w.getBlood()) {
            for (int q = 0; q < b.length; q++) {
                b[q].update();
            }
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
            }
        }
            List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);
                if (event.type == Input.TouchEvent.TOUCH_UP) {
                    if (inBounds(event, 400, 0, 80, 23)) {
                        j = 0;
                        if(Level.sound){
                            Assets.buttonSound.play(1);
                        }
                        Level.save(game.getFileIO());
                        Level.changeHighScores(this);
                        Level.saveHighScore(game.getFileIO());
                        game.setScreen(new MainMenuScreen(game));
                    }
                }
                if (event.type == Input.TouchEvent.TOUCH_DOWN && !inBounds(event, 400, 0, 84, 35)) {
                        restart = true;
                        j = 0;
                        Level.save(game.getFileIO());
                        Level.changeHighScores(this);
                        Level.saveHighScore(game.getFileIO());
                        state = GameState.Ready;
                }
            }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.blank, 0, 0);

        if(state == GameState.Ready)
            drawReadyUI();
        if(state == GameState.Running)
            drawRunningUI();
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.Dead)
            drawDeadUI();
        if(state == GameState.GameOver)
            drawGameOverUI();
    }

    private void drawReadyUI(){
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.backGround1,b1.getX(),0);
        g.drawPixmap(Assets.backGround1,b2.getX(),0);
        paintTiles(g);
        g.drawPixmap(w.getCurrentPixmap(), w.getxx(), w.getyy());
        g.drawPixmap(Assets.ready, 80, 70);
    }

    private void drawRunningUI(){
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.backGround1,b1.getX(),0);
        g.drawPixmap(Assets.backGround1,b2.getX(),0);
        paintTiles(g);
        g.drawPixmap(w.getCurrentPixmap(), w.getxx(), w.getyy());
        g.drawPixmap(Assets.pause, 0,0);
        score2/=10;
        score3 = Integer.toString(score2);
        drawText(g, score3, 380, 0);
    }

    private void drawPausedUI(){
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.backGround1,b1.getX(),0);
        g.drawPixmap(Assets.backGround1,b2.getX(),0);
        paintTiles(g);
        g.drawPixmap(w.getCurrentPixmap(), w.getxx(), w.getyy());
        g.drawPixmap(Assets.play, 0,0);
        g.drawPixmap(Assets.menuButton, 400,0);
        g.drawPixmap(level, x, 0);

    }

    private void drawDeadUI(){
        Graphics g = game.getGraphics();
        int a = Integer.parseInt(score3);
        g.drawPixmap(Assets.backGround1,b1.getX(),0);
        g.drawPixmap(Assets.backGround1,b2.getX(),0);
        paintTiles(g);
        if(w.getBlood()) {
        if((Level.currentLevel == 3 && a >= 190)
                || (Level.currentLevel == 2 && a > 430)
                || (Level.currentLevel == 1 && a > 500)) {
            for (int i = 0; i < b.length; i++) {
                g.drawRect(b[i].getX(), b[i].getY(), 3, 3, Color.YELLOW);
            }
        }else{
            for (int i = 0; i < b.length; i++) {
                g.drawRect(b[i].getX(), b[i].getY(), 3, 3, Color.RED);
            }
        }
        }
    }

    private void drawGameOverUI(){
        Graphics g = game.getGraphics();
        int c = Integer.parseInt(score3);
        g.drawPixmap(Assets.backGround1,b1.getX(),0);
        g.drawPixmap(Assets.backGround1,b2.getX(),0);
        paintTiles(g);
        g.drawPixmap(Assets.menuButton, 400,0);
        g.drawPixmap(Assets.gameOver, 40, 0);
        drawText(g,score3,265, 90);
        String a = Integer.toString(Level.currentHighScore);
        drawText(g,a,320,145);
        if(w.getBlood()) {
            if((Level.currentLevel == 3 && c >= 190)
                    || (Level.currentLevel == 2 && c > 430)
                    || (Level.currentLevel == 1 && c > 500)) {
                for (int i = 0; i < b.length; i++) {
                    g.drawRect(b[i].getX(), b[i].getY(), 3, 3, Color.YELLOW);
                }
            }else{
                for (int i = 0; i < b.length; i++) {
                    g.drawRect(b[i].getX(), b[i].getY(), 3, 3, Color.RED);
                }
            }
        }
    }
    public void createBloodObject(){

        for(int i = 0; i<b.length; i++) {
            Random rand = new Random();
            int rx = rand.nextInt(31);
            Random rand2 = new Random();
            int ry = rand2.nextInt(39);
            int x = w.getxx() + rx;
            int y = w.getyy() + ry;
            b[i] = new Blood(x, y);
        }

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

    private void paintTiles(Graphics g) {
        for (int i = 0; i < w.getTileArray().size(); i++) {
            Tile t = w.getTileArray().get(i);
            g.drawPixmap(t.getP(), t.getX(), t.getY());
        }
    }


    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            int srcX = (character - '0') * 30;
            int srcWidth = 30;

            g.drawPixmap(Assets.numbers, x, y, srcX, 5, srcWidth, 30);
            x += srcWidth;
        }
    }

    public int getScore2() {
        return score2;
    }

}
