package com.company.ToolBox;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Button{                //A améliorer
    public static final int PUSH_MODE = 0;
    public static final int SWITCH_MODE = 1;

    private float x, y;
    private float dx, dy;

    private boolean mouseReleased = false;

    private String text = null;
    private Color backgroundColor;
    private Color textColor;

    private int mode;
    private boolean buttonPressed = false;

    public Button(float x, float y, float dx, float dy, int mode) {
        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

        this.mode = mode;

        backgroundColor = new Color(70,70,70);
        textColor = new Color(200,200,200);
    }

    public Button(String text, float x, float y, float dx, float dy, int mode) {
        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

        this.mode = mode;

        this.text = text;

        backgroundColor = new Color(70,70,70);
        textColor = new Color(200,200,200);
    }

    public void display(GameContainer gc, Graphics g) {
        Input input = gc.getInput();

        g.setColor(backgroundColor);
        g.fillRect(x, y, dx, dy);

        if(text != null) {
            g.setColor(textColor);
            //textSize(50);
            //textAlign(CENTER, CENTER);
            float Ex = g.getFont().getWidth(text)/2;         //Pour centrer
            float Ey = g.getFont().getHeight(text)/2;

            g.drawString(text, x+dx/2-Ex, y+dy/2-Ey);
        }

        if(input.isMouseButtonDown(0) && collision(gc)){            //Purement graphique fonctionne bien
            backgroundColor = new Color(200,200,200);
            textColor = new Color(70,70,70);
        }
        else if(collision(gc)){
            backgroundColor = new Color(70,70,70,200);
            textColor = new Color(200,200,200, 200);
        }
        else{
            backgroundColor = new Color(70,70,70);
            textColor = new Color(200,200,200);
        }

        if(mode==1) {
            if (buttonPressed) {                           //Concerne les interrupteurs
                backgroundColor = new Color(200, 200, 200);
                textColor = new Color(70, 70, 70);
            }
            else {
                backgroundColor = new Color(70, 70, 70);
                textColor = new Color(200, 200, 200);
            }
        }
    }

    public boolean isActive(GameContainer gc){              //Detecte l'utilisation du bouton
        Input input = gc.getInput();

        if(input.isMouseButtonDown(0) && !mouseReleased) {
            mouseReleased = true;
        }

        if(!collision(gc)) mouseReleased = false;

        if(!input.isMouseButtonDown(0) && mouseReleased && collision(gc)){
            mouseReleased = false;

            if(mode==1) buttonPressed = !buttonPressed;

            return true;
        }
        else return false;
    }

    public boolean collision(GameContainer gc){
        Input input = gc.getInput();

        if(input.getMouseX() < x+dx && input.getMouseX() > x && input.getMouseY() < y+dy && input.getMouseY() > y){
            return true;
        }
        return false;
    }

    public boolean isButtonPressed() {
        return buttonPressed;
    }

    public void setButtonPressed(boolean buttonPressed) {
        this.buttonPressed = buttonPressed;
    }
}
