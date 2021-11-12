package com.company.Controllers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import static java.lang.Math.*;

public class Keyboard extends Controller{

    int keyUp, keyDown, keyLeft, keyRight, keyAction, keyUseHand, keyOpenInventoy, keySwitchPerso, keyRun, keyBuild, keyEdit;

    public Keyboard(){
        keyUp = Input.KEY_UP;
        keyDown = Input.KEY_DOWN;
        keyLeft = Input.KEY_LEFT;
        keyRight = Input.KEY_RIGHT;
                                                            //Bouton par défault
        keyRun = Input.KEY_LSHIFT;

        keyAction = Input.KEY_A;
        keyUseHand = Input.KEY_SPACE;
        keyOpenInventoy = Input.KEY_E;
        keySwitchPerso = Input.KEY_S;
        keyBuild = Input.KEY_B;
        keyEdit = Input.KEY_M;

        angle = 0;
    }

    public void selectKey(int keyUp, int keyDown, int keyLeft, int keyRight, int keyAction, int keyUseHand, int keyOpenInventoy, int keySwitchPerso){    //INT...
        this.keyUp = keyUp;
        this.keyDown = keyDown;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;               //Pour redefinir les boutons

        this.keyAction = keyAction;
        this.keyUseHand = keyUseHand;
        this.keyOpenInventoy = keyOpenInventoy;
        this.keySwitchPerso = keySwitchPerso;
    }

    /*@Override
    public void use(GameContainer gc){          //Ajouter la possibilité de choisir les touches !!!
        Input input = gc.getInput();

        int x = 0;
        int y = 0;

        ratio = 0;

        if(input.isKeyDown(Input.KEY_UP)){
            ratio = 1;
            y--;
        }
        if(input.isKeyDown(Input.KEY_DOWN)){
            ratio = 1;
            y++;
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            ratio = 1;
            x--;
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            ratio = 1;
            x++;
        }

        if(x == 0 && y != 0) {
            angle = (float) (y*PI/2);
        }
        else if(x != 0 && y == 0){
            angle = (float) (-PI/2+x*PI/2);
        }                                                   //A débattre    ==>> Fonctionnement a partir d'angle et de ratio
        else if(x != 0 && y != 0){
            if(x>0){
                angle = (float) (y*PI/4);
            }
            else{
                angle = (float) (y*3*PI/4);
            }
        }
    }*/

    @Override
    public void use(GameContainer gc){          //Ajouter la possibilité de choisir les touches !!!
        Input input = gc.getInput();
        float angleSpeed = 0.08F;

        ratio = 0;

        if(input.isKeyDown(keyUp)){
            ratio = 0.5F;
            angle = (float) (-PI/2);
        }
        if(input.isKeyDown(keyDown)){
            ratio = 0.5F;
            angle = (float) (PI/2);
        }
        if(input.isKeyDown(keyLeft)){               //utilisation des boutons
            ratio = 0.5F;
            //angle-=angleSpeed;
            angle = (float) PI;
        }
        if(input.isKeyDown(keyRight)){
            ratio = 0.5F;
            angle = 0;
            //angle+=angleSpeed;
        }

        if(input.isKeyDown(keyRun)){
            ratio*=2;
            running=true;
        }
        else running = false;


        action = input.isKeyPressed(keyAction);
        openInventory = input.isKeyPressed(keyOpenInventoy);
        useHand = input.isKeyPressed(keyUseHand);
        switchPerso = input.isKeyPressed(keySwitchPerso);
        building = input.isKeyPressed(keyBuild);
        editing = input.isKeyPressed(keyEdit);
    }
}
