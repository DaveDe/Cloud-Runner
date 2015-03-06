package com.noodles.stickrun.stickRun;


import com.noodles.stickrun.framework.Pixmap;

import java.util.ArrayList;

public class Animation {

    private ArrayList frames;
    private int currentFrame;
    private long animTime;
    private long totalDuration;

    public Animation() {
        frames = new ArrayList();
        totalDuration = 0;

        synchronized (this) {
            animTime = 0;
            currentFrame = 0;
        }
    }

    public synchronized void addFrame(Pixmap image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    public synchronized void update(long elapsedTime) {
        if (frames.size() > 1) {
            animTime += elapsedTime;
            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currentFrame = 0;

            }

            while (animTime > getFrame(currentFrame).endTime) {
                currentFrame++;

            }
        }
    }

    public synchronized Pixmap getImage() {
        if (frames.size() == 0) {
            return null;
        } else {
            return getFrame(currentFrame).image;
        }
    }

    private AnimFrame getFrame(int i) {
        return (AnimFrame) frames.get(i);
    }


    private class AnimFrame {

        Pixmap image;
        long endTime;

        public AnimFrame(Pixmap image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
