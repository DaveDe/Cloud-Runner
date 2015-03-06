package com.noodles.stickrun.stickRun;


import com.noodles.stickrun.framework.Game;
import com.noodles.stickrun.framework.Graphics;
import com.noodles.stickrun.framework.Screen;

public class LoadingScreen extends Screen {

    public LoadingScreen(Game game) {
        super(game);

    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.levelSelect = g.newPixmap("levelSelect.png", Graphics.PixmapFormat.RGB565);
        Assets.menuButton = g.newPixmap("menuButton.png", Graphics.PixmapFormat.RGB565);
        Assets.black = g.newPixmap("black.png", Graphics.PixmapFormat.RGB565);
        Assets.running1 = g.newPixmap("running1.png", Graphics.PixmapFormat.ARGB8888);
        Assets.running2 = g.newPixmap("running2.png", Graphics.PixmapFormat.ARGB8888);
        Assets.running3 = g.newPixmap("running3.png", Graphics.PixmapFormat.ARGB8888);
        Assets.running4 = g.newPixmap("running4.png", Graphics.PixmapFormat.ARGB8888);
        Assets.running5 = g.newPixmap("running5.png", Graphics.PixmapFormat.ARGB8888);
        Assets.running6 = g.newPixmap("running6.png", Graphics.PixmapFormat.ARGB8888);
        Assets.running7 = g.newPixmap("running7.png", Graphics.PixmapFormat.ARGB8888);
        Assets.jump = g.newPixmap("jump.png", Graphics.PixmapFormat.ARGB8888);
        Assets.ready = g.newPixmap("touchScreen.png", Graphics.PixmapFormat.RGB565);
        Assets.pause = g.newPixmap("pause.png", Graphics.PixmapFormat.RGB565);
        Assets.play = g.newPixmap("play.png", Graphics.PixmapFormat.RGB565);
        Assets.gameOver = g.newPixmap("gameOver.png", Graphics.PixmapFormat.RGB565);
        Assets.numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.RGB565);
        Assets.spike = g.newPixmap("sawBlade.png", Graphics.PixmapFormat.RGB565);
        Assets.spike2 = g.newPixmap("sawBlade2.png", Graphics.PixmapFormat.RGB565);
        Assets.spike3 = g.newPixmap("sawBlade3.png", Graphics.PixmapFormat.RGB565);
        Assets.spike4 = g.newPixmap("sawBlade4.png", Graphics.PixmapFormat.RGB565);
        Assets.spike5 = g.newPixmap("sawBlade5.png", Graphics.PixmapFormat.RGB565);
        Assets.spike6 = g.newPixmap("sawBlade6.png", Graphics.PixmapFormat.RGB565);
        Assets.backGround1 = g.newPixmap("clouds.png", Graphics.PixmapFormat.ARGB8888);
        Assets.playButton = g.newPixmap("playButton.png", Graphics.PixmapFormat.RGB565);
        Assets.levelSelectButton = g.newPixmap("levelSelectButton.png", Graphics.PixmapFormat.RGB565);
        Assets.title = g.newPixmap("title.png", Graphics.PixmapFormat.RGB565);
        Assets.soundOff = g.newPixmap("soundOff.png", Graphics.PixmapFormat.RGB565);
        Assets.soundOn = g.newPixmap("soundOn.png", Graphics.PixmapFormat.RGB565);
        Assets.blank = g.newPixmap("blank.png", Graphics.PixmapFormat.RGB565);
        Assets.beginner = g.newPixmap("beginner.png", Graphics.PixmapFormat.RGB565);
        Assets.intermediate = g.newPixmap("intermediate.png", Graphics.PixmapFormat.RGB565);
        Assets.expert = g.newPixmap("expert.png", Graphics.PixmapFormat.RGB565);
        Assets.beginnertxt = g.newPixmap("beginnertxt.png", Graphics.PixmapFormat.RGB565);
        Assets.intermediatetxt = g.newPixmap("intermediatetxt.png", Graphics.PixmapFormat.RGB565);
        Assets.experttxt = g.newPixmap("experttxt.png", Graphics.PixmapFormat.RGB565);

        Assets.buttonSound = game.getAudio().newSound("buttonSound.wav");
        Assets.sawDeath = game.getAudio().newSound("sawDeath.mp3");
        Assets.fallOffScreen = game.getAudio().newSound("fallOffScreen.mp3");
        Assets.jumpSound = game.getAudio().newSound("jumpSound.wav");
        Assets.mainMenuSong = game.getAudio().newMusic("mainMenuSong.wav");
        Level.load(game.getFileIO());
        Level.loadHighScore(game.getFileIO());
        Level.loadSound(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    public void present(float deltaTime) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
