package com.noodles.stickrun.stickRun;

import com.noodles.stickrun.framework.Screen;
import com.noodles.stickrun.framework.impl.AndroidGame;

/**
 * Created by David on 8/7/2014.
 */
public class StickRunGame extends AndroidGame {

    public Screen getStartScreen() {

        return new LoadingScreen(this);
    }
}
