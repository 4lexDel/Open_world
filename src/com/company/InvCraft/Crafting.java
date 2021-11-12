package com.company.InvCraft;

import com.company.Game;
import com.company.Entities.Items.Item;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.List;

public class Crafting {
    private int craftSelected;
    private List<TradeSection> tradeSections;           //Contient toutes les sections
    private TradeSection tradeSectionActive;        //Contient la section active

    private List<Trade> tradesActive;      //Crafts possible

    private boolean tradeSectionSelected = false;

    private float width = Game.width;
    private float height = Game.height;

    private float craftingWidth = 2*width/3;
    private float craftingHeight = height;

    private float lineWidth = 10;
    private float E = craftingWidth/30;             //Parametres d'affichage

    private float x1 = (width-craftingWidth)/2;
    private float y1 = (height-craftingHeight)/2;

    private float dx = craftingWidth-lineWidth-2*E;
    private float dy = (craftingHeight-lineWidth)/4-2*E;

    private int slotCapacity;
    private int scroll = 0;

    public Crafting(List<TradeSection> tradeSections){
        this.tradeSections = tradeSections;

        slotCapacity = (int) (craftingHeight/dy)-1;
        System.out.println(slotCapacity);
    }

    public void display(Graphics g){
        g.setColor(Color.darkGray);

        g.setLineWidth(lineWidth);
        g.fillRect(x1, y1, craftingWidth, craftingHeight);

        g.setLineWidth(0);
        g.setColor(new Color(200,200,200));
        g.fillRect(x1+lineWidth/2, y1+lineWidth/2, craftingWidth-lineWidth, craftingHeight-lineWidth);

        /*AFFICHAGE DES TRADES*/

        if(slotCapacity+scroll < craftSelected) scroll++;
        else if(scroll > craftSelected) scroll--;

        if(!tradeSectionSelected) {
            for (int i = 0; i < tradeSections.size(); i++) {
                float x = x1 + lineWidth / 2 + E;
                float y = (i-scroll) * (dy + E) + (y1 + lineWidth / 2 + E);

                tradeSections.get(i).display(x, y, dx, dy, i == craftSelected, g);
            }
        }
        else{
            for (int i = 0; i < tradesActive.size(); i++) {
                float x = x1 + lineWidth / 2 + E;
                float y = (i-scroll) * (dy + E) + (y1 + lineWidth / 2 + E);

                tradesActive.get(i).display(x, y, dx, dy, i == craftSelected, g);
            }
        }
    }

    public void selectCraft(GameContainer gc){
        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_UP)) craftSelected--;
        if(craftSelected<0)craftSelected=0;

        if(input.isKeyPressed(Input.KEY_DOWN)) craftSelected++;

        if(!tradeSectionSelected) {
            if (craftSelected >= tradeSections.size()) craftSelected = tradeSections.size() - 1;
        }
        else {
            if (craftSelected >= tradesActive.size()) craftSelected = tradesActive.size() - 1;
        }
    }

    public boolean craft(Inventory inventory){
        if(!tradeSectionSelected) {                   //On selectionne dabord une section
            tradeSectionSelected = true;
            tradeSectionActive = tradeSections.get(craftSelected);  //selection de la section de trade

            tradesActive = tradeSectionActive.getTrades();          //Ajout des craft possible

            craftSelected = 0;      //On remonte
        }
        else{
            if(isTradePossible(inventory, tradesActive.get(craftSelected))) {
                for (Item item : tradesActive.get(craftSelected).getInput()) {
                    inventory.removeItem(item);
                }
                inventory.give((Item) tradesActive.get(craftSelected).getOutput().clone());
                return true;
            }
        }

        return false;
    }

    public boolean isTradePossible(Inventory inventory, Trade trade){
        for (Item item : trade.getInput()) {            //Propre
            if(!inventory.searchItem(item)) return false;
        }
        return true;
    }

    public void setTradeSectionSelected(boolean tradeSectionSelected) {
        this.tradeSectionSelected = tradeSectionSelected;
    }
}
