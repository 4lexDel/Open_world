package com.company;

import com.company.Controllers.Keyboard;
import com.company.Entities.*;
import com.company.Entities.Animals.*;
import com.company.Entities.Items.Bullets.Arrow;
import com.company.Entities.Items.Bullets.Bullet;
import com.company.Entities.Items.Bullets.Pebble;
import com.company.Entities.Items.Food.Apple;
import com.company.Entities.Items.Food.Meat;
import com.company.InvCraft.Trade;
import com.company.Entities.Items.*;
import com.company.Entities.Items.Tools.Axe;
import com.company.Entities.Items.Tools.Pickaxe;
import com.company.Entities.Items.Weapons.Bow;
import com.company.Entities.Items.Weapons.StoneThrower;
import com.company.Entities.Items.Weapons.Sword;
import com.company.InvCraft.TradeSection;
import com.company.Obstacles.*;
import com.company.Obstacles.Structures.Structure;
import com.company.Obstacles.Vegetations.Rock;
import com.company.Obstacles.Vegetations.Tree;
import com.company.Obstacles.Vegetations.Vegetation;
import com.company.ToolBox.RandomNumber;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.TrueTypeFont;

public class Game extends BasicGame {
    private Thread threadUpdateMap;
    //private Thread threadGenerateMap;

    public static int FPS;

    public static boolean hideEntities = false;

    //Joystick joystick1;
    private Keyboard keyboard1;

    //Button switchButton;
    //Button inventoryButton;

    private List<TradeSection> tradeSections;

    private Hero loki, neige, zombi, nawak, nitch, shappy, chien;
    private List<Hero> heroes;

    private List<Animal> animals;               //Actuellement la génération est static il faudrait la rendre dynamique a terme

    private List<Obstacle> obstacles;                           //Les variables importantes : les listes

    private List<House> houses;

    private List<Bullet> bullets;

    private List<Item> loots;

    public static int chunkX, chunkY;         //Découpes l'univers

    //private boolean heroSelected;
    private int heroSelection;              //Pour switcher de héros

    private int tickSpeed;
    private int time;

    private boolean timeDirection;

    public static float width;
    public static float height;

    private int[][] mark;       //Pour se repérer dans l'espace (pathfinding)
    private int markDiameter = 20;
    private int markDensity = 15; //Ecart entre 2 balises
    private int nbmark;
    private int markArea;

    private float markX, markY;

    private boolean markUpdated = true;

    private int count = 0;

    private boolean[][] chunkStateGeneration;

    public static Font titlefont, infoFont;
    public static TrueTypeFont titleTrueFont, infoTrueFont;

    private boolean editingMode;
    private Editor editor;

    public Game(String title, int FPS) {
        super(title);
        Game.FPS = FPS;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        width = gc.getWidth();
        height = gc.getHeight();

        titlefont = new Font("Courier new", Font.BOLD, 50);
        infoFont = new Font("Courier new", Font.BOLD, 20);

        titleTrueFont = new TrueTypeFont(titlefont, true);
        infoTrueFont = new TrueTypeFont(infoFont, true);

        //joystick1 = new Joystick(width-200, height-200, 100);                 //Version portable ?
        //switchButton = new Button("Switch hero", 50, height-100, 100, 40);
        //inventoryButton = new Button("Inventory", 50, height-200, 100, 40);
        editingMode = false;
        editor = new Editor();          //Chargement de l'editeur

        keyboard1 = new Keyboard();

        tradeSections = new ArrayList<>();
        createTrade(tradeSections);

        loki = new Hero(200, 200, "Loki", new Color(123, 36, 28));
        loki.inventory.give(new StoneThrower());
        loki.inventory.give(new Bow());
        loki.inventory.give(new Pickaxe(Material.Diamonds));
        loki.inventory.give(new Axe(Material.Wood));
        loki.inventory.give(new Sword(Material.Iron));
        loki.inventory.give(new Pebble());
        loki.inventory.give(new Arrow(15));
        loki.inventory.give(new Apple(1));

        neige = new Hero(600, 200, "Neige", Color.white);
        neige.inventory.give(new Pickaxe(Material.Wood));
        neige.inventory.give(new Pickaxe(Material.Stone));
        neige.inventory.give(new Pickaxe(Material.Copper));
        neige.inventory.give(new Pickaxe(Material.Iron));
        neige.inventory.give(new Pickaxe(Material.Diamonds));

        zombi = new Hero(1000, 200, "Zombi", new Color(243,120,16));
        zombi.inventory.give(new Wood(20));
        zombi.inventory.give(new Stone(42));
        zombi.inventory.give(new Apple(5));
        zombi.inventory.give(new Meat(25));
        zombi.inventory.give(new Pebble());
        zombi.inventory.give(new Arrow(15));
        zombi.inventory.give(new Bow());
        zombi.inventory.give(new StoneThrower());


        /*nawak = new Hero(200, 600, "Nawak", new Color(151,80,20));
        nitch = new Hero(600, 600, "Nitch", new Color(100,100,100));
        shappy = new Hero(600, 1000, "Shappy", Color.black);
        chien = new Hero(350, 350, "Chien", Color.blue);*/

        //ryu = new Hero(200, 1000, "Ryu", new Color(180,180,180));

        heroes = new ArrayList<>();
        heroes.add(loki);
        heroes.add(neige);
        heroes.add(zombi);
        /*heroes.add(nawak);          //Ajouter a la liste héro
        heroes.add(nitch);
        heroes.add(shappy);
        heroes.add(chien);*/
        //heroes.add(ryu);

        animals = new ArrayList<>();

        obstacles = new ArrayList<>();

        houses = new ArrayList<>();

        bullets = new ArrayList<>();                //Autres entitées

        loots = new ArrayList<>();

        heroSelection = 0;

        refreshChunkPosition(heroes.get(heroSelection));

        //tickSpeed = 1;
        //time = 0;

        //timeDirection = true;

        markArea = 1500;                //Creer décalge dans les bordures de chunk

        nbmark = (int)(markArea/markDensity)+1;
        mark = new int[nbmark][nbmark];
        //System.out.println(nbmark);

        initMarkMapping(obstacles, heroes.get(heroSelection));

        chunkStateGeneration = new boolean[50][50];

        clearGeneration();

        dynamicGeneration();

        createFolders();            //Dossier save
    }

    public void createFolders(){
        File dossier = new File("./save");
        boolean res = dossier.mkdir();

        if(res) {
            System.out.println("Le dossier a été créé.");
        }
        else {
            System.out.println("Le dossier existe déja.");
        }
    }

    public void initMarkMapping(List<Obstacle> obstacles, Entity target){
        /*//int targetX = (int) ((target.x-markArea/2)/markDensity);
        //int targetY = (int) ((target.y-markArea/2)/markDensity);
        //int targetX = (int)(nbmark/2);
        //int targetY = (int)(nbmark/2);      //Centré        -1 a débattre

        //markX = target.x - markArea/2;
        //markY = target.y - markArea/2;*/

        int E = (int) ((markArea-width)/2);

        markX = chunkX*width-E-E%markDensity;            //Probleme de calibrage au balise persistant   ==> solution modulo pour calibrer
        markY = chunkY*height-E-E%markDensity;

        int targetX = (int) ((target.x - markX)/markDensity);
        int targetY = (int) ((target.y - markY)/markDensity);

        if(targetX>=0 && targetY >= 0) {
            for (int x = 0; x < nbmark; x++) {
                for (int y = 0; y < nbmark; y++) {
                    mark[x][y] = 0;
                    for (Obstacle obstacle : obstacles)     ////PROBLEME LA LIST EST UTILIS2 PAR 2 THREAD DIFFERENT
                        if(obstacle instanceof Impassable) {
                            if (obstacle.collision(x * markDensity + markX, y * markDensity + markY, markDiameter / 2)) {
                                mark[x][y] = -1;
                                break;
                            }
                        }
                }
            }
            int i = 1;
            boolean update = true;
            mark[targetX][targetY] = i;                     //GAFFE AUX OUT OF BOUND
            while (update) {                      //A modif
                update = false;
                for (int x = 0; x < nbmark - 1; x++) {
                    for (int y = 0; y < nbmark - 1; y++) {
                        if (mark[x][y] == i) {
                            //DOWN
                            if (y < nbmark - 1 && mark[x][y + 1] == 0) {
                                mark[x][y + 1] = i + 1;
                                update = true;
                            }
                            //UP
                            if (y > 0 && mark[x][y - 1] == 0) {
                                mark[x][y - 1] = i + 1;
                                update = true;
                            }
                            //RIGHT
                            if (x < nbmark - 1 && mark[x + 1][y] == 0) {
                                mark[x + 1][y] = i + 1;
                                update = true;
                            }
                            //LEFT
                            if (x > 0 && mark[x - 1][y] == 0) {
                                mark[x - 1][y] = i + 1;
                                update = true;
                            }
                        }
                    }
                }
                i++;
            }
        }
    }

    public void displaymark(Entity target, Graphics g){
        for (int x = 0; x < nbmark-1; x++) {
            for (int y = 0; y < nbmark - 1; y++) {
                float xt = x * markDensity + markX;
                float yt = y * markDensity + markY;
                g.setColor(Color.red);
                g.fillOval(xt-2, yt-2, 4, 4);
                g.setColor(Color.black);

                g.drawString(String.valueOf(mark[x][y]), xt, yt);
            }
        }
    }

    private void createTrade(List<TradeSection> tradeSections) {
        List<Trade> tradesWood = new ArrayList<>();
        tradesWood.add(new Trade(new ArrayList<>(){{
            add(new Wood(1));}},
                new Stick(4)));
        TradeSection tradeWoodSection = new TradeSection(tradesWood, new Wood(1), "Wood");      //Bois

        List<Trade> tradesWeapon = new ArrayList<>();
        tradesWeapon.add(new Trade(new ArrayList<>(){{
            add(new Stick(2));
            add(new Stone(2));}},
                new Sword(Material.Stone)));
        TradeSection tradeWeaponSection = new TradeSection(tradesWeapon, new StoneThrower(), "Weapon");   //Armes

        List<Trade> tradesTool = new ArrayList<>();
        tradesTool.add(new Trade(new ArrayList<>(){{
            add(new Stick(2));
            add(new Stone(3));}},
                new Axe(Material.Stone)));
        tradesTool.add(new Trade(new ArrayList<>(){{
            add(new Stick(2));
            add(new Stone(3));}},     //Automatiser les materiaux
                new Pickaxe(Material.Stone)));
        tradesTool.add(new Trade(new ArrayList<>(){{
            add(new Stick(2));
            add(new Stone(3));
            add(new Apple(3));}},     //Automatiser les materiaux
                new Pickaxe(Material.Stone)));
        TradeSection tradeToolSection = new TradeSection(tradesTool, new Pickaxe(Material.Copper), "Tool");   //Outils

        tradeSections.add(tradeWoodSection);        //Ajout des sections
        tradeSections.add(tradeWeaponSection);
        tradeSections.add(tradeToolSection);

        for (int i = 0; i < 4; i++) {
            tradeSections.add(tradeWoodSection);
            tradeSections.add(tradeWeaponSection);
            tradeSections.add(tradeToolSection);
        }
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        Input input = gc.getInput();
        count++;

        keyboard1.use(gc);          //Utilisation du clavier

        if(input.isKeyPressed(Input.KEY_I)) System.out.println("Nombre d'entité : "+ Entity.nbEntities);//info

        Hero heroSelected = heroes.get(heroSelection);
        if(!editingMode) {
            switchHero();

            heroSelected.sideBarActive = true;   //inventaire du joueur actif utilisé
            heroSelected.useInventory(keyboard1, gc);

            heroSelected.updateLifeState();
            boolean moving = heroSelected.move(keyboard1, obstacles);       //Mouvements du joueur
            if (moving) markUpdated = false;
            heroSelected.shot(keyboard1, bullets);       //Combat a distance
            heroSelected.hit(keyboard1, heroes, animals);         //Corps a corps           //Bug
            heroSelected.mine(keyboard1, obstacles);     //Minage
            heroSelected.collectItem(loots);            //Recup item
            heroSelected.dropItem(keyboard1, loots);           //DropItem
            heroSelected.eat(keyboard1);                    //mange
            heroSelected.craft(keyboard1, tradeSections, gc);      //Craft
            heroSelected.build(keyboard1);      //Build

            //if(inventoryButton.isActive(gc)) heroes.get(heroSelection).inventoryActive = !heroes.get(heroSelection).inventoryActive; //Version bouton

            refreshChunkPosition(heroes.get(heroSelection));
            updateAnimal(animals);
            updateBullet(bullets);
            updateItem(loots);

            removeDeadEntity(animals);
            removeDeadHero(heroes, loots);
            removeDeadEntity(bullets);

            removeDestroyObstacle(obstacles);
            removeLoot(loots);
        }

        if(keyboard1.isEditing()) {
            editingMode = !editingMode;
            editor.switchButtonOff();
        }
        if(editingMode) {
            editor.update(gc);
            editor.build(obstacles, gc);
            editor.createHouse(houses, obstacles, gc);
            editor.saveMap(obstacles, gc);
            editor.loadMap(obstacles, gc);
            editor.clearEntities(animals, gc);
            editor.generateMap(this, gc);           //OPTIMISE ????
        }

        if(!markUpdated && count%(int)(FPS/5)==0) {            //Délai pour limiter lag
            markUpdated = true;
            /*Thread threadUpdateMap = new Thread() {           //https://www.jmdoudoux.fr/java/dej/chap-threads.htm
                public void run() {
                    initMarkMapping(obstacles, heroSelected);
                }
            };*/
            if(threadUpdateMap != null) threadUpdateMap.interrupt();         //Bonne pratique ?
            threadUpdateMap = new Thread(() -> initMarkMapping(obstacles, heroSelected)); //Probleme thread concurant

            threadUpdateMap.start();
        }

        dynamicGeneration();            //Generation dynamique !        //Ajouter une thread ?  //A mettre dans le refreshChunk

        if(input.isKeyPressed(Input.KEY_K)){
            //File file1 = new File("Map.xml");
            File file2 = new File("Entities.xml");

            saveEntities(file2);
        }
    }

    private void switchHero() {
        if(keyboard1.isSwitchPerso()){
            heroes.get(heroSelection).sideBarActive = false;
            heroes.get(heroSelection).inventoryActive = false;  //Switch de héro
            heroes.get(heroSelection).craftingActive = false;                                      //AJouter des fcts pour tout ca
            heroSelection++;
            if(heroSelection>=heroes.size()) heroSelection = 0;
            initMarkMapping(obstacles, heroes.get(heroSelection));
        }
    }

    public void updateAnimal(List<Animal> animals) {
        for (Animal animal : animals) {
            if (!animal.isDead()){
                float x = animal.x;
                float y = animal.y;
                float size = animal.size;
                if (x - size < width + (chunkX + 1) * width && x + size > 0 + (chunkX - 1) * width && y - size < height + (chunkY + 1) * height && y + size > (chunkY - 1) * height)
                    if(animal instanceof Intelligent) ((Intelligent) animal).move(heroes.get(heroSelection) ,mark, markX, markY, markDensity, keyboard1);
                    else animal.move(obstacles);         //Les animaux ne peuvent bouger que si ils se situent dans le champs de visions du hero actif
            }                                       //Alternative ?
        }
    }

    public void dynamicGeneration(){        //G2RER LES DEBORDEMENTS
        if(!chunkStateGeneration[chunkX][chunkY]) generateChunk(chunkX, chunkY);
        if(chunkX+1<chunkStateGeneration.length && !chunkStateGeneration[chunkX+1][chunkY]) generateChunk(chunkX+1, chunkY);
        if(chunkY+1<chunkStateGeneration[0].length && !chunkStateGeneration[chunkX][chunkY+1]) generateChunk(chunkX, chunkY+1);
        if(chunkX-1>=0 && !chunkStateGeneration[chunkX-1][chunkY]) generateChunk(chunkX-1, chunkY);
        if(chunkY-1>=0 && !chunkStateGeneration[chunkX][chunkY-1]) generateChunk(chunkX, chunkY-1);
    }

    public void generateChunk(int chunkX, int chunkY){
        chunkStateGeneration[chunkX][chunkY] = true;

        dynamicAnimalGenerator(animals, chunkX, chunkY);
        dynamicVegetationGenerator(obstacles, chunkX, chunkY);
    }

    public void dynamicAnimalGenerator(List<Animal> animals, int chunkX, int chunkY){
        int nb = 100;       //Nombres de tirage définis donc le nombre d'animaux max dans un chunk ....

        RandomNumber rand = new RandomNumber();

        for(int i = 0; i<nb; i++){
            float spawnRate = rand.generate(0, 1);

            if(Pig.spawnRate>=spawnRate) {
                float x = rand.generate(chunkX * width, (chunkX+1) * width);
                float y = rand.generate(chunkY * height, (chunkY+1) * height);

                animals.add(new Pig(x, y));
            }
            spawnRate = rand.generate(0, 1);

            if(Cow.spawnRate>=spawnRate) {
                float x = rand.generate(chunkX * width, (chunkX+1) * width);
                float y = rand.generate(chunkY * height, (chunkY+1) * height);

                animals.add(new Cow(x, y));
            }
            spawnRate = rand.generate(0, 1);

            if(Snake.spawnRate>=spawnRate) {
                float x = rand.generate(chunkX * width, (chunkX+1) * width);
                float y = rand.generate(chunkY * height, (chunkY+1) * height);

                animals.add(new Snake(x, y));
            }
        }
    }

    public void dynamicVegetationGenerator(List<Obstacle> obstacles,  int chunkX, int chunkY){
        int nb = 100;       //Nombres de tirage définis donc le nombre d'animaux max dans un chunk ....

        RandomNumber rand = new RandomNumber();

        for(int i = 0; i<nb; i++){
            float spawnRate = rand.generate(0, 1);

            if(Tree.spawnRate>=spawnRate) {
                float x = rand.generate(chunkX * width, (chunkX + 1) * width);
                float y = rand.generate(chunkY * height, (chunkY + 1) * height);
                //generation !!
                obstacles.add(new Tree(x, y));
            }
            spawnRate = rand.generate(0, 1);

            if(Rock.spawnRate>=spawnRate) {
                float x = rand.generate(chunkX * width, (chunkX + 1) * width);
                float y = rand.generate(chunkY * height, (chunkY + 1) * height);

                obstacles.add(new Rock(x, y));
            }
        }
    }

    public void clearGeneration(){
        obstacles.clear();
        animals.clear();

        for (int x = 0; x<chunkStateGeneration.length; x++)
            for (int y = 0; y<chunkStateGeneration[0].length; y++){
                chunkStateGeneration[x][y] = false;
            }
    }

    /*public void updateEntity(List<? extends Entity> entities) {
        for (Entity entity : entities) {
            if (!entity.isDead()){
                float x = entity.x;
                float y = entity.y;
                float sizeItem = entity.sizeItem;
                if (x - sizeItem < width + chunkX * width && x + sizeItem > 0 + chunkX * width && y - sizeItem < height + chunkY * height && y + sizeItem > chunkY * height)
            }                                       //Alternative ?
        }
    }*/

    private void updateBullet(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if(!bullet.isDead()) {
                float x = bullet.x;
                float y = bullet.y;
                float size = bullet.sizeItem;
                if (x - size < width + (chunkX + 1) * width && x + size > 0 + (chunkX - 1) * width && y - size < height + (chunkY + 1) * height && y + size > (chunkY - 1) * height) {
                    bullet.move(obstacles, heroes, animals);         //A DEBATTRE !! ==> AUTORISER les chunk adjacent
                }
            }
        }
    }

    public void updateItem(List<Item> items){
        for (Item item : items) {
            item.refreshPV();               //Tous ?
        }
    }

    public void refreshChunkPosition(Hero hero){
        chunkX = (int)(hero.x/width);
        chunkY = (int)(hero.y/height);                  //Centre le jeu sur un hero choisi
    }

    public void removeDeadEntity(List<? extends Entity> entities){
        for (int j = 0; j < entities.size(); j++) {
            if(entities.get(j).isDead()){
                if(entities.get(j) instanceof Lootable) {
                    ((Lootable) entities.get(j)).generateLoot(loots);
                }
                entities.remove(j);
            }
        }
    }

    public void removeDeadHero(List<Hero> heroes, List<Item> loots){
        for (int j = 0; j < heroes.size(); j++) {
            if(heroes.get(j).isDead()){
                heroes.get(j).dropAllItem(loots);
                heroes.remove(j);
            }
        }
    }

    public void removeDestroyObstacle(List<? extends Obstacle> obstacles){
        for (int j = 0; j < obstacles.size(); j++) {
            if (obstacles.get(j).isDestroy()) {
                if(obstacles.get(j) instanceof Lootable) {
                    ((Lootable) obstacles.get(j)).generateLoot(loots);
                }
                obstacles.remove(j);
            }
        }
    }

    public void removeLoot(List<? extends Item> loots){
        for (int j = 0; j < loots.size(); j++) {
            if (loots.get(j).isCollected || !loots.get(j).isAlive()) {
                loots.remove(j);
            }
        }
    }

    public void saveEntities(File file){
        Document dom;
        Element e = null;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try{
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element obstaclesElement = dom.createElement("obstacles");
            //Element obstaclesElement = dom.createElement("obstaclesElement");

            // create data elements and place them under root
            for (Animal animal : animals) {               //Bonne pratique ? ==> pas tres modulaire
                if (animal instanceof Pig) e = dom.createElement("pig");
                else if (animal instanceof Snake) e = dom.createElement("snake");
                else if (animal instanceof Cow) e = dom.createElement("cow");
                    e.setAttribute("x", String.valueOf(animal.x));              //wall
                    e.setAttribute("y", String.valueOf(animal.y));
                    e.setAttribute("name", String.valueOf((animal.getName())));
                    e.setAttribute("pv", String.valueOf((animal.getPv())));
                    obstaclesElement.appendChild(e);
            }

            /*for (He animal : animals) {               //Bonne pratique ? ==> pas tres modulaire
                if (obstacle instanceof Structure){
                    e = dom.createElement("wall");
                    e.setAttribute("x", String.valueOf(obstacle.x));              //wall
                    e.setAttribute("y", String.valueOf(obstacle.y));
                    e.setAttribute("dx", String.valueOf(((Wall) obstacle).dx));
                    e.setAttribute("dy", String.valueOf(((Wall) obstacle).dy));
                    e.setAttribute("durability", String.valueOf(obstacle.getDurability()));
                    obstaclesElement.appendChild(e);
                }
            }*/

            dom.appendChild(obstaclesElement);

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
            //tr.setOutputProperty("{http://xml.Apache.org/xslt}indent-amount", "4");

            // send DOM to file
            tr.transform(new DOMSource(dom),
                    new StreamResult(new FileOutputStream(file)));



            for (int i = 0; i < 5; i++) {
                e = dom.createElement("tree");
                e.setAttribute("x", "50");              //Arbres
                e.setAttribute("y", "100");
                e.setAttribute("durability", "70");
                //e.appendChild(dom.createTextNode(role1));
                obstaclesElement.appendChild(e);
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        Input input = gc.getInput();

        g.setBackground(new Color(150,180,150));

        Hero heroSelected = heroes.get(heroSelection);

        g.pushTransform();//////////////////////////////////////////////////
        g.translate(-chunkX*width,-chunkY*height);

        displayObstacle(obstacles, g);                                      //Tout ce qui est dépendant des chunks

        if(!hideEntities) {
            displayLoot(loots, g);

            displayEntity(animals, g);
            displayEntity(bullets, g);

            displayEntity(heroes, g);
        }

        if(input.isKeyDown(Input.KEY_H))
            displaymark(heroSelected, g); //=> lag

        g.popTransform();////////////////////////////////////////////////////

        if(!editingMode) {
            heroSelected.displayInventory(gc, g);                               //A afficher à l'écran uniquement
            heroSelected.displayPV(gc, g);
            heroSelected.displayEnergy(gc, g);          //ENlever les gc a terme (a debattre pour les fleches)
            heroSelected.displayHunger(gc, g);
            heroSelected.displayCrafting(g);

            displayCoord(heroes.get(heroSelection), g);    //Info
        }

        //titleTrueFont.drawString(100, 100, "teseeeeet", Color.gray);
        if(editingMode){
            editor.displayMessage("Mode editeur actif !", titleTrueFont);
            editor.displayCoord(g);
            editor.displayButton(gc, g);

            editor.displayConstruction(obstacles, gc, g);          //Structure en cours
        }

        //TradeSection tradeSection = new TradeSection(null, new StoneThrower(1));
        //tradeSection.display(width/2-200, height/2-200, 400, 250, g);
    }

    public void displayObstacle(List<? extends Obstacle> obstacles, Graphics g){
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof Vegetation) {
                Vegetation vegetation = (Vegetation) obstacle;

                float x = vegetation.getX();
                float y = vegetation.getY();
                float size = vegetation.getSizeGlobal();
                if (x - size < width + chunkX * width && x + size > 0 + chunkX * width && y - size < height + chunkY * height && y + size > chunkY * height)
                    vegetation.display(g);      //AFIN DEVITER BEAUCOUP DE LAG
            } else if (obstacle instanceof Structure) { //Differenciation des structures
                Structure structure = (Structure) obstacle;

                float x = structure.getX();
                float y = structure.getY();
                float dx = structure.getDx();
                float dy = structure.getDy();
                if (x < width + chunkX * width && x + dx > 0 + chunkX * width && y < height + chunkY * height && y + dy > chunkY * height)
                    structure.display(g);
            }
        }
    }

    public void displayEntity(List<? extends Entity> entities, Graphics g){
        for (Entity entity : entities) {
            float x = entity.x;
            float y = entity.y;           //Affichage du héro
            float size = entity.size;
            if (x - size < width + chunkX * width && x + size > 0 + chunkX * width && y - size < height + chunkY * height && y + size > chunkY * height)
                entity.display(g);
        }
    }

    public void displayLoot(List<? extends Item> loots, Graphics g){
        for (Item item : loots) {
            float x = item.x;
            float y = item.y;           //Affichage du héro
            float size = Item.sizeItem;
            if (x - size < width + chunkX * width && x + size > 0 + chunkX * width && y - size < height + chunkY * height && y + size > chunkY * height)
                item.displayLoot(g);
        }

    }

    public void displayCoord(Hero hero, Graphics g){//dans hero
        g.setColor(Color.white);
        String text = "";
        text = "Coord : x : "+(int)(hero.x) +" | y : "+(int)(hero.y)+"\nChunk : x : "+chunkX+" | y : " +chunkY;
        int E = 20;
        g.drawString(text, E, 2*E);
    }
}
