package com.company.Controllers;

import org.newdawn.slick.GameContainer;

public class Controller {       //Ajout de bouton ?
    protected float angle;
    protected float ratio;

    protected boolean action;
    protected boolean useHand;
    protected boolean openInventory;
    protected boolean switchPerso;
    protected boolean running;
    protected boolean building;
    protected boolean editing;

    Controller(){

    }

    public void use(GameContainer gc){}

    public float getAngle(){
        return angle;
    }

    public float getPowerRatio(){return ratio;}

    public boolean isAction() {return action;}                  //Getter de toutes les actions possibles

    public boolean isUseHand() {return useHand;}

    public boolean isOpenInventory() {return openInventory;}

    public boolean isSwitchPerso() {return switchPerso;}

    public boolean isRunning() {
        return running;
    }

    public boolean isBuilding() {
        return building;
    }

    public boolean isEditing() {
        return editing;
    }
}
