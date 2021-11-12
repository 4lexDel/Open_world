package com.company.Entities.Items;

import com.company.Entities.Entity;
import com.company.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import static java.lang.Math.abs;

public class Item extends Entity implements Cloneable{
    protected int quantity;

    public static final float sizeItem = 50;        //Par terre
    public boolean isCollected = true;
    protected float itemRotation = 0;

    protected int limitStack = 64;
    protected int durabilityMax = 20;           //A optimiser
    protected int durability = durabilityMax;

    protected int pvMax = Game.FPS*300;     //5 minutes
    protected int pv = pvMax;
    protected boolean alive = true;

    protected boolean showName = true;

    public Item(int quantity) {
        super(0, 0, "Anonymous");
        this.quantity = quantity;
    }

    public Item(int quantity, String name) {
        super(0, 0, name);
        this.quantity = quantity;
    }

    public void display(float x, float y, float dx, float dy, Graphics g){
        if(texture != null){
            texture.draw(x, y, dx, dy);             //Interessant !
        }

        if(quantity>0){  //A debattre
            g.setColor(Color.white);
            float E = g.getFont().getWidth(String.valueOf(quantity)) / 2;
            g.drawString(String.valueOf(quantity), x + dx / 2 - E, y + dy / 2 - E);     //Affiche la quantité d'item
        }
        if(durability<durabilityMax && isCollected){
            g.setColor(Color.black);
            displayDurability(x, y+dy-dy/20,dx, dy/20, g);
        }

        if(isCollected && showName) displayName(x, y, dx, dy, g);
    }

    public void displayDurability(float x, float y, float dx, float dy, Graphics g){
        int width = (int) (durability*dx/durabilityMax);
        g.fillRect(x, y, width, dy);
    }

    public void displayName(float x, float y, float dx, float dy, Graphics g){
        g.setColor(Color.darkGray);

        float E = g.getFont().getWidth(name)/2;         //Centrage
        g.drawString(name, x + dx / 2 - E, y - dy / 10);
    }

    public void displayLoot(Graphics g) {
        itemRotation += 1;
        g.pushTransform();
        g.rotate(x, y, itemRotation);
        display(x-Item.sizeItem /2, y-Item.sizeItem /2, Item.sizeItem, Item.sizeItem, g);
        g.popTransform();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDurability() {
        return durability;
    }

    public void drop() {
        quantity--;
    }

    public int add(int value) {
        int nbValueOut = 0;
        quantity+=value;
        if(quantity>limitStack){
            nbValueOut = quantity-limitStack;
            quantity = limitStack;
        }
        else if(quantity<0){
            nbValueOut = -quantity;
            quantity=0;
        }
        return abs(nbValueOut);
    }

    public void refreshPV(){
        if(!isCollected) pv --;
        if(pv<=0) alive = false;
        if(isCollected && pv<pvMax) pv = pvMax;
    }

    public boolean isAlive() {
        return alive;
    }

    public Object clone() {
        Object o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver, car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }

    public boolean equals(Item item) {
        if(getClass() == item.getClass() && durabilityMax == item.durabilityMax && name == item.name){
            if(item instanceof Materializable && this instanceof Materializable) {
                if(((Materializable) item).getMaterial() == ((Materializable) this).getMaterial()) return true;
            }
            else return true;
        }
        return false;
    }

    public void setShowName(boolean showName) {
        this.showName = showName;
    }
}
