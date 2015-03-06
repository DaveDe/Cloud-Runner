package com.noodles.stickrun.stickRun;

import com.noodles.stickrun.framework.FileIO;
import com.noodles.stickrun.framework.Game;
import com.noodles.stickrun.framework.Graphics;
import com.noodles.stickrun.framework.Pixmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class World {
    //hero is 30x46, floor is 20 high, saw blade is 40x40, jump is 30x38
    private Hero h;
    private Animation anim;
    private Pixmap currentPixmap;
    private boolean collideTop = false;
    private ArrayList<Tile> tileArray = new ArrayList<Tile>();
    private boolean gameOver = false;
    private boolean temp  = false;
    private String [] levels = {"","level1.txt","level2.txt","level3.txt"};
    private int yAdded;
    private int sawX;
    private int sawY;
    private boolean blood = false;

    public World(Game game){

        try {
            loadMap(game.getFileIO());
        }catch (IOException e){
            e.printStackTrace();
        }

        h = new Hero();
        anim = new Animation();
        anim.addFrame(Assets.running1,15);
        anim.addFrame(Assets.running2,15);
        anim.addFrame(Assets.running3,15);
        anim.addFrame(Assets.running4,15);
        anim.addFrame(Assets.running5,15);
        anim.addFrame(Assets.running6,15);
        anim.addFrame(Assets.running7,15);

    }

    public void updateWorld(){
        checkCollision(h);
        if(collideTop) {
            currentPixmap = anim.getImage();
            anim.update(5);
        }else{
            currentPixmap = Assets.jump;
        }
        temp = collideTop;
        h.update(this);
        updateTiles();
        checkGameOver();
    }

    public void checkCollision(Hero h){
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = tileArray.get(i);
            //set proper y value of hero
            if(currentPixmap == Assets.jump){
                yAdded = 38;
            }else{
                yAdded = 46;
            }
            //collide left with black tile

                if (h.getX() + 30 >= t.getX()
                        && h.getX() + 30 < t.getX() + 7
                        && h.getY() <= t.getY() + 25
                        && h.getY() + 46 > t.getY() + 7
                        && t.getTypeFinal() == 1) {
                    h.setDx(-5);

                }
            //collide bottom
            if( h.getY() <= t.getY() + 25
                    && t.getY() < h.getY()
                    && h.getX() + 30 >= t.getX() + 7
                    && h.getX() <= t.getX() + 25
                    && t.getTypeFinal() == 1){
                    h.setY(t.getY() + 26);
                    h.setDy(0);
            }
            //collide top
            if (t.getTypeFinal() == 1
                   && t.getX() + 7<= h.getX() + 30
                    && t.getX() + 25 >= h.getX()
                    && h.getY() + 46 >= t.getY()
                    && h.getY() < t.getY() + 25) {
                collideTop = true;
                h.setY(t.getY() - 46);
                h.setDy(0);
            }
            //collide with spike
            if(t.getTypeFinal() == 3
               && h.getX()+ 30 >= t.getX()
               && h.getX() <= t.getX() + 40)
            {
                 sawX = t.getX() + 20;
                 sawY = t.getY() + 20;
                //bottom right corner
                       int a = Math.abs((sawX - (h.getX() + 30)));
                       int b = Math.abs((sawY - (h.getY() + yAdded)));
                       double c = Math.sqrt(((double)(a*a) + (double)(b*b)));
                       if(c <= 14){
                           gameOver = true;
                           blood = true;
                           if(Level.sound) {
                               Assets.sawDeath.play(2);
                           }
                       }
                //right side
                    int a2 = Math.abs((sawX - (h.getX() + 30)));
                    int b2 = Math.abs((sawY - (h.getY() + yAdded/2)));
                    double c2 = Math.sqrt(((double)(a2*a2) + (double)(b2*b2)));
                    if(c2 <= 14){
                        gameOver = true;
                        blood = true;
                        if(Level.sound) {
                            Assets.sawDeath.play(2);
                        }
                    }
                //bottom left corner
                    int a3 = Math.abs((sawX - (h.getX())));
                    int b3 = Math.abs((sawY - (h.getY() + yAdded)));
                    double c3 = Math.sqrt(((double)(a3*a3) + (double)(b3*b3)));
                    if(c3 <= 14){
                        gameOver = true;
                        blood = true;
                        if(Level.sound) {
                            Assets.sawDeath.play(2);
                        }
                    }
                //top right corner
                int a4 = Math.abs((sawX - (h.getX() + 30)));
                int b4 = Math.abs((sawY - (h.getY())));
                double c4 = Math.sqrt(((double)(a4*a4) + (double)(b4*b4)));
                if(c4 <= 14){
                    gameOver = true;
                    blood = true;
                    if(Level.sound) {
                        Assets.sawDeath.play(2);
                    }
                }
                //top left corner
                int a5 = Math.abs((sawX - (h.getX())));
                int b5 = Math.abs((sawY - (h.getY())));
                double c5 = Math.sqrt(((double)(a5*a5) + (double)(b5*b5)));
                if(c5 <= 14){
                    gameOver = true;
                    blood = true;
                    if(Level.sound) {
                        Assets.sawDeath.play(2);
                    }
                }


            }

        }
    }
    public void checkGameOver(){
        if(h.getX()+30 <=0){
            gameOver = true;
            blood = true;
            if(Level.sound) {
                Assets.sawDeath.play(2);
            }
        }
        if(h.getY() >= 320){
           gameOver = true;
            if(Level.sound) {
                Assets.fallOffScreen.play(1);
            }
        }
    }

    private void updateTiles() {
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = tileArray.get(i);
            t.update();
        }
    }

    private void loadMap(FileIO file) throws IOException {
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        BufferedReader in = null;

        try{
            in = new BufferedReader(new InputStreamReader(
                    file.readAsset(levels[Level.currentLevel])));

        }catch (IOException e) {
        }
        while (true) {
            String line = in.readLine();
            // no more lines to read
            if (line == null) {
                in.close();
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }
        height = lines.size();

        for (int j = 0; j < height; j++) {
            String line = (String) lines.get(j);
            //go through individual lines
            for (int i = 0; i < width; i++) {

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    int w =  Character.getNumericValue(ch);
                    if(w != 0) {
                        Tile t = new Tile(i * 25, j * 25, w);
                        tileArray.add(t);
                    }
                }

            }
        }

    }
    public void jump(){
        h.setJump(true);
    }

    public ArrayList<Tile> getTileArray() {
        return tileArray;
    }
    public Pixmap getCurrentPixmap() {
        return currentPixmap;
    }
    public int getxx(){
        return h.getX();
    }
    public int getyy(){
        return h.getY();
    }
    public void setCollideTop(boolean collideTop) {
        this.collideTop = collideTop;
    }
    public boolean getGameOver(){
        return gameOver;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public boolean getTemp(){
        return temp;
    }
    public boolean getBlood(){
        return blood;
    }
}
