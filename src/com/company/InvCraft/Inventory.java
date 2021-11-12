package com.company.InvCraft;

import com.company.Controllers.Controller;
import com.company.Game;
import com.company.Entities.Items.Item;
import org.newdawn.slick.*;

public class Inventory{            //commun avec craft? ==> class parent ""interface?""     //A commenter
    //int nbSlotBar;
    private Item[][] itemInventory;
    private Item[] itemSlotBar;
    private int ratio = 4;

    private Item itemActive;

    private int slotSideBarActive;

    private int opacity;

    private Color colorGrid, colorBackground;

    private int slotselectedX;
    private int slotselectedY;

    private int slotselectedXTransit, slotselectedYTransit;

    private Item item1;

    private boolean slot1Choose;

    private Image gridTexture = null;

    public Inventory(){
        itemSlotBar = new Item[8];
        itemInventory = new Item[8][4];

        /*itemSlotBar[0] = new Wood(64);
        itemSlotBar[1] = new Stone(32);
        itemSlotBar[2] = new Apple(6);*/

        slotSideBarActive = 0;

        opacity = 200;
        colorGrid = new Color(120,120,120, opacity);
        colorBackground = new Color(180,180,180,opacity);

        slotselectedX = 0;
        slotselectedY = 0;

        slotselectedXTransit = 0;
        slotselectedYTransit = 0;

        slot1Choose = false;

        try {
            gridTexture = new Image("textures/inventory_square.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void display(Graphics g){
        float width = Game.width;
        float height = Game.height;

        float inventoryWidth = 3*width/ratio;
        float dx = inventoryWidth/itemInventory.length;
        float dy = dx;
        float inventoryHeight = dy*itemInventory[0].length;

        int E = 5;
        int lineWidth = 6;

        g.pushTransform();
        g.translate(width/2-inventoryWidth/2, height/ratio);
        g.setLineWidth(lineWidth);
        g.setColor(new Color(colorGrid));
        //g.drawRect(0,0, inventoryWidth, inventoryHeight);

        for(int x = 0; x<itemInventory.length; x++){
            for(int y = 0; y<itemInventory[0].length; y++){
                /*g.setColor(new Color(colorGrid));
                g.setLineWidth(lineWidth);
                g.drawRect(x*dx, y*dy, dx, dy);
                g.setColor(new Color(colorBackground));
                g.fillRect(x*dx+lineWidth/2, y*dy+lineWidth/2, dx-lineWidth, dy-lineWidth);*/
                gridTexture.draw(x*dx, y*dy, dx, dy);
                //ITEM!!
                if(itemInventory[x][y] != null)itemInventory[x][y].display(x*dx+E, y*dy+E, dx-2*E, dy-2*E, g);
            }
        }
        if(slotselectedY<itemInventory[0].length){
            g.setColor(Color.red);
            g.setLineWidth(lineWidth/2);
            g.drawRect(slotselectedX*dx, slotselectedY*dy, dx, dy);
        }
        if(slotselectedYTransit<itemInventory[0].length){
            if(slot1Choose){
                g.setColor(new Color(200, 0, 0, 90));
                g.fillRect(slotselectedXTransit*dx+lineWidth/2, slotselectedYTransit*dy+lineWidth/2, dx-lineWidth, dy-lineWidth);
            }
        }
        g.popTransform();
    }

    public void displaySideBar(Graphics g){
        float width = Game.width;
        float height = Game.height;

        float inventoryWidth = 3*width/ratio;
        float dx = inventoryWidth/itemSlotBar.length;
        float dy = dx;

        int E = 7;
        int lineWidth = 6;
        g.pushTransform();
        g.setLineWidth(lineWidth);
        g.translate(width/2-inventoryWidth/2, (float) (height-1.5*dy));
        g.setColor(new Color(colorGrid));
        g.drawRect(0,0, dx*itemSlotBar.length, dy);

        for(int x = 0; x<itemSlotBar.length; x++){
            g.setColor(colorGrid);
            g.setLineWidth(lineWidth);
            g.drawRect(x*dx, 0, dx, dy);
            g.setColor(new Color(colorBackground));
            g.fillRect(x*dx+lineWidth/2, lineWidth/2, dx-lineWidth, dy-lineWidth);
            //Item!
            if(itemSlotBar[x] != null)itemSlotBar[x].display(x*dx+E, E, dx-2*E, dy-2*E, g);
        }
        g.setColor(Color.darkGray);
        g.setLineWidth(1.5F*lineWidth);
        g.drawRect(slotSideBarActive *dx, 0, dx, dy);


        if(slotselectedY>=itemInventory[0].length) {
            g.setLineWidth(lineWidth/2);
            g.setColor(Color.red);
            g.drawRect(slotselectedX *dx, 0, dx, dy);
        }
        if(slotselectedYTransit>=itemInventory[0].length){
            if(slot1Choose){
                g.setColor(new Color(200, 0, 0, 90));
                g.fillRect(slotselectedXTransit*dx+lineWidth/2, 0+lineWidth/2, dx-lineWidth, dy-lineWidth);
            }
        }
        g.popTransform();
    }

    public boolean selectSlot(GameContainer gc){
        boolean output = true;

        Input input = gc.getInput();

        if(input.isKeyPressed(Input.KEY_1)) slotSideBarActive = 0;
        else if(input.isKeyPressed(Input.KEY_2)) slotSideBarActive = 1;
        else if(input.isKeyPressed(Input.KEY_3)) slotSideBarActive = 2;
        else if(input.isKeyPressed(Input.KEY_4)) slotSideBarActive = 3;
        else if(input.isKeyPressed(Input.KEY_5)) slotSideBarActive = 4;
        else if(input.isKeyPressed(Input.KEY_6)) slotSideBarActive = 5;
        else if(input.isKeyPressed(Input.KEY_7)) slotSideBarActive = 6;
        else if(input.isKeyPressed(Input.KEY_8)) slotSideBarActive = 7;
        else output = false;

        return output;
    }

    public void switchTwoSlot(Controller controller, GameContainer gc){
        Input input = gc.getInput();

        if(input.isKeyPressed(Input.KEY_UP)){
            slotselectedY--;
        }
        if(input.isKeyPressed(Input.KEY_DOWN)){
            slotselectedY++;
        }
        if(input.isKeyPressed(Input.KEY_LEFT)) {
            slotselectedX --;
        }
        if(input.isKeyPressed(Input.KEY_RIGHT)){
            slotselectedX ++;
        }
        if(slotselectedX>=itemInventory.length) slotselectedX = itemInventory.length-1;

        else if(slotselectedX<0) slotselectedX = 0;

        if(slotselectedY>=itemInventory[0].length+1) slotselectedY = itemInventory[0].length;
        else if(slotselectedY<0) slotselectedY = 0;

        if(input.isKeyPressed(Input.KEY_D)) {
            //itemInventory[0][0]= new Wood(6);

            Item item1 = itemSlotBar[1];

            itemSlotBar[1] = itemSlotBar[2];
            itemSlotBar[2] = item1;
        }

        if(controller.isUseHand()) {
            if(!slot1Choose) {
                slotselectedXTransit = slotselectedX;
                slotselectedYTransit = slotselectedY;
                System.out.println(1);
                slot1Choose = true;
            }
            else {
                System.out.println(2);
                slot1Choose = false;

                System.out.println("X1 : "+slotselectedXTransit+" | Y1 : "+slotselectedYTransit+" X2 : "+slotselectedX+" | Y2 : "+slotselectedY);

                if (slotselectedY >= itemInventory[0].length) {
                    if(slotselectedYTransit >= itemInventory[0].length){
                        Item trans = itemSlotBar[slotselectedXTransit];
                        itemSlotBar[slotselectedXTransit] = itemSlotBar[slotselectedX];
                        itemSlotBar[slotselectedX] = trans;
                    }
                    else{
                        Item trans = itemInventory[slotselectedXTransit][slotselectedYTransit];
                        itemInventory[slotselectedXTransit][slotselectedYTransit] = itemSlotBar[slotselectedX];
                        itemSlotBar[slotselectedX] = trans;
                    }
                }
                else {
                    if(slotselectedYTransit >= itemInventory[0].length){
                        Item trans = itemSlotBar[slotselectedXTransit];
                        itemSlotBar[slotselectedXTransit] = itemInventory[slotselectedX][slotselectedY];
                        itemInventory[slotselectedX][slotselectedY] = trans;
                    }
                    else{
                        Item trans = itemInventory[slotselectedXTransit][slotselectedYTransit];
                        itemInventory[slotselectedXTransit][slotselectedYTransit] = itemInventory[slotselectedX][slotselectedY];
                        itemInventory[slotselectedX][slotselectedY] = trans;
                    }
                }
            }
        }
    }

    public boolean give(Item item){
        for (int i = 0; i < itemSlotBar.length; i++) {
            if(itemSlotBar[i] != null)
                if(itemSlotBar[i].equals(item)){       //A modifier dans le futur ?
                    int nb = itemSlotBar[i].add(item.getQuantity());
                    if(nb==0) return true;
                    else item.setQuantity(nb);
                }
        }                                                                   //Stackable
        for(int x = 0; x<itemInventory.length; x++){
            for(int y = 0; y<itemInventory[0].length; y++){
                if(itemInventory[x][y] != null)
                    if(itemInventory[x][y].equals(item)){       //A modifier dans le futur ?
                        int nb = itemInventory[x][y].add(item.getQuantity());
                        if(nb==0) return true;
                        else item.setQuantity(nb);
                    }
            }
        }
        //Repetition mais avec les cases vides cette fois
        for (int i = 0; i < itemSlotBar.length; i++) {
            if(itemSlotBar[i] == null){
                itemSlotBar[i] = item;                      //A compléter avec l'inventaire global  //Si tout est full alors le poser au sol !
                return true;
            }
        }                                                                   //Stackable
        for(int x = 0; x<itemInventory.length; x++){
            for(int y = 0; y<itemInventory[0].length; y++){
                if(itemInventory[x][y] == null){
                    itemInventory[x][y] = item;
                    return true;
                }
            }
        }


        return false;   //POSER AU SOL
    }

    public boolean removeItem(Item item) {
        Item itemToRemove = (Item) item.clone();

        for (int i = 0; i < itemSlotBar.length; i++) {
            if (itemSlotBar[i] != null)
                if (itemSlotBar[i].equals(item)) {       //A modifier dans le futur ?
                    int nb = itemSlotBar[i].add(-itemToRemove.getQuantity());
                    if (nb == 0){
                        removeNullItem();
                        return true;
                    }
                    else itemToRemove.setQuantity(nb);
                }
        }
        for (int x = 0; x < itemInventory.length; x++) {
            for (int y = 0; y < itemInventory[0].length; y++) {
                if (itemInventory[x][y] != null)
                    if (itemInventory[x][y].equals(item)) {       //A modifier dans le futur ?
                        int nb = itemInventory[x][y].add(-itemToRemove.getQuantity());
                        if (nb == 0){
                            removeNullItem();
                            return true;
                        }
                        else itemToRemove.setQuantity(nb);
                    }

            }
        }
        removeNullItem();
        return false;
    }

    public void removeNullItem(){
        for (int i = 0; i < itemSlotBar.length; i++) {
            if(itemSlotBar[i] != null)
                if(itemSlotBar[i].getQuantity() <=0 || itemSlotBar[i].getDurability() <=0){
                    itemSlotBar[i] = null;
                }
        }
        for(int x = 0; x<itemInventory.length; x++){
            for(int y = 0; y<itemInventory[0].length; y++){
                if(itemInventory[x][y] != null)
                    if(itemInventory[x][y].getQuantity() <= 0 || itemInventory[x][y].getDurability() <=0){
                        itemInventory[x][y] = null;
                    }
            }
        }
    }
    public boolean searchItem(Item item){
        int nb = item.getQuantity();
        for (int i = 0; i < itemSlotBar.length; i++) {
            if(itemSlotBar[i] != null)
                if(itemSlotBar[i].equals(item)){
                    nb -= itemSlotBar[i].getQuantity();
                    if(nb<=0) return true;
                }
        }
        for(int x = 0; x<itemInventory.length; x++){
            for(int y = 0; y<itemInventory[0].length; y++){
                if(itemInventory[x][y] != null)
                    if(itemInventory[x][y].equals(item)){
                        nb -= itemInventory[x][y].getQuantity();
                        if(nb<=0) return true;
                    }
            }
        }
        return false;   //POSER AU SOL
    }

    public Item getItemActive(){
        return itemSlotBar[slotSideBarActive];
    }

    public void clearAll(){
        //Boucles for //Préciser de quel inventaire on parle
        for (int i = 0; i < itemSlotBar.length; i++) {
            itemSlotBar[i] = null;
        }
        for(int x = 0; x<itemInventory.length; x++){
            for(int y = 0; y<itemInventory[0].length; y++){
                itemInventory[x][y] = null;
            }
        }
    }

    public Item[] getItemSlotBar() {
        return itemSlotBar;
    }

    public Item[][] getItemInventory() {
        return itemInventory;
    }
}

