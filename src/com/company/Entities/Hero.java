package com.company.Entities;

import com.company.Controllers.Controller;
import com.company.Entities.Items.Bullets.Bullet;
import com.company.InvCraft.Crafting;
import com.company.InvCraft.Inventory;
import com.company.InvCraft.Trade;
import com.company.Entities.Items.Food.Eatable;
import com.company.Entities.Items.Item;
import com.company.Entities.Items.Tools.Tool;
import com.company.Entities.Items.Weapons.HandToHand;
import com.company.Entities.Items.Weapons.Range;
import com.company.Entities.Items.Weapons.Weapon;
import com.company.InvCraft.TradeSection;
import com.company.Obstacles.Obstacle;
import com.company.ToolBox.RandomNumber;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.List;

import static java.awt.geom.Point2D.distance;
import static java.lang.Math.*;

public class Hero extends Entity {
    private Color heroColor;
    public Inventory inventory;         //Inventaire du héro
    private Crafting crafting;

    public boolean inventoryActive;     //Limite l'utilisation des inventaires
    public boolean sideBarActive;

    public boolean craftingActive;

    private float durationMineAnimation = 0.2F;
    private int countMine = 0;
    private boolean isMining = false;

    private int radiusMining;         //Parametre de minage

    public Hero(float x, float y, String name){
        super(x, y, name);

        heroColor = new Color(200,200,200);

        init();
    }

    public Hero(float x, float y, String name, Color heroColor){
        super(x, y, name);

        this.heroColor = heroColor;

        init();
    }

    @Override
    protected void init() {
        super.init();           //tres important

        size = 25;

        inventory = new Inventory();
        inventoryActive = false;
        sideBarActive = false;
        craftingActive = false;

        radiusMining = (int) size;

        if(name == "Zombi" || name == "Neige") invincibility = true;
    }

    public void display(Graphics g){
        g.setColor(heroColor);
        g.fillOval(x-size/2, y-size/2, size, size);             //Graphisme : ajout de rotation pour faciliter l'ajout de détaille ==> rotate
        g.setColor(new Color(50));
        float EA = (float)PI/5;

        if(name == "Zombi"){
            g.setColor(new Color(250,250,250));
        }
        g.fillOval(x+size/2*(float)cos(angle-EA)-size/4, y+size/2*(float)sin(angle-EA)-size/4, size/2, size/2);
        g.setColor(new Color(50));
        g.fillOval(x+size/2*(float)cos(angle+EA)-size/4, y+size/2*(float)sin(angle+EA)-size/4, size/2, size/2);

        displayName(g);
    }

    public void displayInventory(GameContainer gc, Graphics g){         //A part pour des raisons techniques (evoquer dans le render)
        if(!craftingActive) {
            if (sideBarActive) inventory.displaySideBar(g);
            if (inventoryActive) inventory.display(g);
        }
    }

    public void displayName(Graphics g){
        g.setColor(new Color(0));

        float E = g.getFont().getWidth(name)/2;         //Centrage
        g.drawString(name, x-E, y-size);
    }

    public boolean move(Controller controller, List<? extends Obstacle> obstacles){  //Move lié a collision  //Ajout d'un bolean pour plus de controle
        if(!craftingActive && !inventoryActive) {
            float alpha = controller.getAngle();
            float ratio = controller.getPowerRatio();

            angle = alpha;

            vx = (float) (speedMax * ratio * (float) cos(alpha));
            vy = (float) (speedMax * ratio * (float) sin(alpha));

            if (collision(obstacles, vx, vy, true) == null) {             //Test si le héro peut bouger (anticipation)
                x += vx;
                y += vy;

                if(vx !=0 || vy != 0) return true;

                if (controller.isRunning()) updateEnergy(-runEnergyCost);        //Course
                else updateEnergy(energyRegeneration / 2);                    //Quand on  marche
            }
        }
        return false;
    }

    public void mine(Controller controller, List<? extends Obstacle> obstacles){//COULDOWN ?
        if(controller.isUseHand() && !craftingActive) {
            if (inventory.getItemActive() instanceof Tool) {
                ((Tool) inventory.getItemActive()).mine(this, radiusMining, obstacles);
                updateEnergy(-mineEnergyCost);
                inventory.removeNullItem();
            }
        }
    }       //A faire !!!!

    public void shot(Controller controller, List<Bullet> bullets){         //Si on est a main nue
        if(controller.isUseHand() && !craftingActive && !inventoryActive) {
            if (inventory.getItemActive() instanceof Weapon) {
                if (inventory.getItemActive() instanceof Range) {
                    ((Range) inventory.getItemActive()).shot(this, bullets);
                    updateEnergy(-fightEnergyCost);
                }
                inventory.removeNullItem();
            }
        }
    }

    public void hit(Controller controller, List<? extends Entity>... entities){         //Si on est a main nue
        if(controller.isUseHand() && !craftingActive && !inventoryActive) {
            if (inventory.getItemActive() instanceof Weapon) {
                if (inventory.getItemActive() instanceof HandToHand) {
                    boolean hitSuccess = ((HandToHand) inventory.getItemActive()).hit(this, entities);
                    if(hitSuccess) updateEnergy(-fightEnergyCost);
                }
                inventory.removeNullItem();
            }
        }
    }

    public void dropItem(Controller controller, List<Item> loots){    //AMELIORER ?
        if(controller.isAction() && !craftingActive && !inventoryActive) {
            int nb = 2;

            if (inventory.getItemActive() != null) {
                Item itemDrop = (Item) inventory.getItemActive().clone();
                itemDrop.setQuantity(1);
                itemDrop.x = (float) (x + nb * size * cos(angle));                                 //Ajouter Animation + collision ==> mouvement
                itemDrop.y = (float) (y + nb * size * sin(angle));
                itemDrop.isCollected = false;

                loots.add(itemDrop);
                inventory.getItemActive().drop();
                inventory.removeNullItem();
            }
        }
    }

    public void dropAllItem(List<Item> loots){
        Item[][] itemInventory = inventory.getItemInventory();
        Item[] itemSlotBar = inventory.getItemSlotBar();

        RandomNumber rand = new RandomNumber();

        for (int i = 0; i < itemSlotBar.length; i++) {
            if(itemSlotBar[i] != null) {
                Item itemDrop = (Item) itemSlotBar[i].clone();
                itemDrop.x = x + rand.generate(-size, size);                                //Ajouter aleatoire
                itemDrop.y = y + rand.generate(-size, size);
                itemDrop.isCollected = false;

                loots.add(itemDrop);
            }
        }
        for(int x = 0; x<itemInventory.length; x++){
            for(int y = 0; y<itemInventory[0].length; y++){
                if(itemInventory[x][y] != null) {
                    Item itemDrop = (Item) itemInventory[x][y].clone();
                    itemDrop.x = x + rand.generate(-size, size);                                 //Ajouter aleatoire
                    itemDrop.y = y + rand.generate(-size, size);
                    itemDrop.isCollected = false;

                    loots.add(itemDrop);
                }
            }
        }
        inventory.clearAll();
        inventory.removeNullItem();
    }

    public void collectItem(List<? extends Item> items){
        Item loot =  checkItemCollision(items);
        if(loot != null){
            if(inventory.give(loot)) loot.isCollected = true;
        }
    }

    public Item checkItemCollision(List<? extends Item> items){ //Utiliser en tant que entité
        for (Item item : items) {
            if(distance(item.x, item.y, x, y) <= Item.sizeItem /2+size/2) return item;
        }

        return null;
    }

    public void eat(Controller controller){
        if(controller.isUseHand() && !craftingActive && !inventoryActive) {
            if (inventory.getItemActive() instanceof Eatable) {
                ((Eatable) inventory.getItemActive()).eat(this);
                inventory.removeNullItem();
            }
        }
    }

    public void useInventory(Controller controller, GameContainer gc){
        if(controller.isOpenInventory() && !craftingActive) inventoryActive = !inventoryActive;     //Ouverture de l'inventaire

        if(!craftingActive && !inventoryActive) inventory.selectSlot(gc);           //selection du slot actif (sidebar) //Avec le controller ?
        if(!craftingActive && inventoryActive) inventory.switchTwoSlot(controller, gc);
        //IL MANQUE DES INFOS
    }

    public void craft(Controller controller, List<TradeSection> tradeSections, GameContainer gc){
        if(controller.isAction() && inventoryActive){
            craftingActive = !craftingActive;
        }
        if(craftingActive){
            if(crafting == null) crafting = new Crafting(tradeSections);
            crafting.selectCraft(gc);
            if(controller.isUseHand()) crafting.craft(inventory);
        }
    }

    public void displayCrafting(Graphics g) {
        if (craftingActive) {
            crafting.display(g);
        }
        else if (crafting != null) crafting.setTradeSectionSelected(false);   //BONNE ENDROIT ?
    }

    public void build(Controller controller){
        if(controller.isBuilding()){
            System.out.println("Building");
        }
    }
}


