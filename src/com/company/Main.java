package com.company;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public static void main(String[] args) {
        try
        {
            int FPS = 60;
            AppGameContainer app = new AppGameContainer(new Game("Open world", FPS));   //Titre et FPS en parametres
            //app.setDisplayMode(screenSize.width, screenSize.height, true); => Full screen
            app.setDisplayMode(900, 900, false);            //Taille de l'écran
            app.setShowFPS(true); // true for display the numbers of FPS
            app.setTargetFrameRate(FPS);
            app.setVSync(true); // false for disable the FPS synchronize            //a approfondir
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}
