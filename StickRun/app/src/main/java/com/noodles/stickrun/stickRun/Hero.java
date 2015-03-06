package com.noodles.stickrun.stickRun;


public class Hero {
    private int x = 200;
    private int y = 200;
    private double dy = 0;
    private double dx = 0;
    private boolean jump = false;


    public void update(World w){

        if(jump){
            dy = -10;
            if(Level.sound) {
                Assets.jumpSound.play(1);
            }
            jump = false;
        }

        y+=dy;
        dy+=1;
        x+=dx;
        dx = 0;

        w.setCollideTop(false);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }


}
