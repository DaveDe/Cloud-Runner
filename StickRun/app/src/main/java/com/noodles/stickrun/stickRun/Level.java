package com.noodles.stickrun.stickRun;

import com.noodles.stickrun.framework.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Level {
    public static int currentLevel = 1;
    public static String[] highScores = new String[]{"",".highScores1",".highScores2",".highScores3",
            ".highScores4",".highScores5",".highScores6",".highScores7",".highScores8" ,".highScores9",".highScores10",".highScores11",".highScores12"};
    public static int currentHighScore = 0;
    public static boolean sound;

    public static void loadSound(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(".sound")));
            sound = Boolean.parseBoolean(in.readLine());
        } catch (IOException e) {
        } catch (NumberFormatException e) {
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void saveSound(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(".sound")));
            out.write(Boolean.toString(sound));
        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }


    public static void changeHighScores(GameScreen gs){
        if (currentHighScore < gs.getScore2()/10) {
            currentHighScore = gs.getScore2()/10;
        }
    }

    public static void saveHighScore(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(highScores[currentLevel])));
                    out.write(Integer.toString(currentHighScore));
        }catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }

    }

    public static void loadHighScore(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(highScores[currentLevel])));
            currentHighScore = Integer.parseInt(in.readLine());
        }catch (IOException e) {
        }catch (NumberFormatException e) {
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }

    }

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(".currentLevel")));
            currentLevel = Integer.parseInt(in.readLine());
        } catch (IOException e) {
        } catch (NumberFormatException e) {
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(".currentLevel")));
            out.write(Integer.toString(currentLevel));
        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }
}
