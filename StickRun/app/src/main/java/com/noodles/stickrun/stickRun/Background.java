package com.noodles.stickrun.stickRun;


public class Background {

    private int x;
    private int dx = -1;

    public Background(int x){
        this.x = x;
    }

    public void update(){
        x+=dx;
        if(x <= -960){
            x = 960;
        }
    }

    public int getX() {
        return x;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
