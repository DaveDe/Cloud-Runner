package com.noodles.stickrun.stickRun;

import com.noodles.stickrun.framework.Pixmap;

public class Tile {
    private int x, y, typeFinal;
    private int dx = -5;
    public Pixmap p;
    Animation sanim;

    public Tile(int x, int y, int type) {
        this.x = x;
        this.y = y;
        typeFinal = type;

        if(typeFinal == 1){
            p = Assets.black;
        }
        else if(typeFinal == 3){
            sanim = new Animation();
            sanim.addFrame(Assets.spike,15);
            sanim.addFrame(Assets.spike2,15);
            sanim.addFrame(Assets.spike3,15);
            sanim.addFrame(Assets.spike4,15);
            sanim.addFrame(Assets.spike5,15);
            sanim.addFrame(Assets.spike6,15);
            p = Assets.spike;
        }
    }

    public void update(){
        x+=dx;
        if(typeFinal == 3) {
            p = sanim.getImage();
            sanim.update(5);
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Pixmap getP() {
        return p;
    }

    public int getTypeFinal() {
        return typeFinal;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
