package com.noodles.stickrun.stickRun;

import java.util.Random;

public class Blood {

    private int x, y;
    private int dx = 0;
    private int dx2 = 1;
    private Random rand = new Random();
    private int r = rand.nextInt(6)+1;
    private Random rand2 = new Random();
    private int r2 = (rand2.nextInt(4)+1)*-1;
    private Random rand3 = new Random();
    private int r3 = -(rand3.nextInt(15)+1);
    private int dy = r3;
    private boolean floor = false;

    public Blood(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void update(){
        if(!floor) {
            //random constant dx
            dx = (r + r2) * dx2;
            x += dx;
            //random dy, increasing by 1
            dy++;
            y += dy;
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getFloor(){
        return floor;
    }

    public void setFloor(boolean floor) {
        this.floor = floor;
    }
}
