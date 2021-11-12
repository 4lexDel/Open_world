package com.company;

import com.company.Entities.Animals.Animal;
import com.company.Entities.Items.Flag;
import com.company.InvCraft.Inventory;
import com.company.Obstacles.Obstacle;
import com.company.Obstacles.Structures.Floor;
import com.company.Obstacles.Structures.Structure;
import com.company.Obstacles.Structures.Wall;
import com.company.Obstacles.Vegetations.Rock;
import com.company.Obstacles.Vegetations.Tree;
import com.company.Obstacles.Vegetations.Vegetation;
import com.company.ToolBox.Button;
import org.newdawn.slick.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Editor {
    //int nbButton = 1;
    private Button saveData;
    private Button loadData;
    private Button buildButton;
    private Button deleteButton;
    private Button clearEntity;
    private Button hideEntity;
    private Button generateMap;

    private boolean deleteMode = false;
    private boolean buildMode = false;

    Builder builder;
    Inventory buildInventory;

    public Editor(){
        float dx = 150;
        float dy = 50;

        float x = 20;

        float y = 100;

        float E = 1.5f;

        saveData = new Button("Save map", x, y, dx, dy, Button.PUSH_MODE);
        loadData = new Button("Load map", x, y+E*dy, dx, dy, Button.PUSH_MODE);
        buildButton = new Button("Build", x, y+2*E*dy, dx, dy, Button.SWITCH_MODE);
        deleteButton = new Button("Delete", x, y+3*E*dy, dx, dy, Button.SWITCH_MODE);
        clearEntity = new Button("Clear entities", x, y+4*E*dy, dx, dy, Button.PUSH_MODE);
        hideEntity = new Button("Hide entities", x, y+5*E*dy, dx, dy, Button.SWITCH_MODE);
        generateMap = new Button("Generate map", x, y+6*E*dy, dx, dy, Button.PUSH_MODE);

        builder = new Builder();

        buildInventory = new Inventory();
        buildInventory.give(new Flag("Wall", 1, Color.orange));
        buildInventory.give(new Flag("Floor", 2, Color.white));        //Ajouter un numéro associé aux flag
        buildInventory.give(new Flag("House", 10, Color.red));      //num flag => option de building
    }                                                                      //Mieux gérer les options de bulding

    public void displayMessage(String message, TrueTypeFont trueTypeFont){
        float x =  Game.width/2-Game.titleTrueFont.getWidth(message)/2;
        float y =  Game.titleTrueFont.getHeight(message)/4;

        trueTypeFont.drawString(x, y, message, Color.red);
    }

    public void displayButton(GameContainer gc, Graphics g){
        saveData.display(gc, g);
        loadData.display(gc, g);
        clearEntity.display(gc, g);
        buildButton.display(gc, g);
        deleteButton.display(gc, g);
        hideEntity.display(gc, g);
        generateMap.display(gc, g);

        if(buildMode) buildInventory.displaySideBar(g);
    }

    public void displayCoord(Graphics g){
        g.setColor(Color.white);
        String text = "";
        text = "X : "+Game.chunkX +" | Y : "+Game.chunkY;
        int E = 20;
        g.drawString(text, E, 2*E);
    }

    public void update(GameContainer gc){
        move(gc);
        useButton(gc);
    }

    public void createHouse(List<House> houses, List<Obstacle> obstacles, GameContainer gc){
        int id = 0;
        if(buildInventory.getItemActive() != null) id = ((Flag)buildInventory.getItemActive()).getId();

        if(id==10) {                          //A MODIFIER !!!!!!!!!!!!
            House house = builder.createHouse(obstacles, gc);

            if (house != null) {
                houses.add(house);
                File file = selectFolder("Création maison");

                saveHouse(file, house);
            }
        }
    }

    private void saveHouse(File folder, House house) {
        if (folder != null) {
            String fileName = "houses.xml";

            String folderPath = folder.getAbsolutePath();

            Document dom;
            Element e = null;

            // instance of a DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            try {
                // use factory to get an instance of document builder
                DocumentBuilder db = dbf.newDocumentBuilder();
                // create instance of DOM
                dom = db.newDocument();

                // create the root element
                Element obstaclesElement = dom.createElement("house");
                //Element obstaclesElement = dom.createElement("obstaclesElement");

                // create data elements and place them under root
                for (Structure structure : house.getStructures()) {               //Bonne pratique ? ==> pas tres modulaire
                    if (structure instanceof Wall) e = dom.createElement("wall");
                    else if (structure instanceof Floor) e = dom.createElement("floor");

                    e.setAttribute("x", String.valueOf(structure.getX()));
                    e.setAttribute("y", String.valueOf(structure.getY()));
                    e.setAttribute("dx", String.valueOf(structure.getDx()));
                    e.setAttribute("dy", String.valueOf(structure.getDy()));
                    e.setAttribute("durability", String.valueOf(structure.getDurability()));
                    obstaclesElement.appendChild(e);
                }

                dom.appendChild(obstaclesElement);

                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(folderPath+"/"+fileName)));

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void build(List<Obstacle> obstacles, GameContainer gc){
        if(deleteButton.isActive(gc)){
            if(deleteButton.isButtonPressed()) buildButton.setButtonPressed(false);
        }

        if(buildButton.isActive(gc)){
            if(buildButton.isButtonPressed()) deleteButton.setButtonPressed(false);
        }

        deleteMode = deleteButton.isButtonPressed();
        buildMode = buildButton.isButtonPressed();

        if(buildMode){
            int id = 0;
            if(buildInventory.getItemActive() != null) id = ((Flag)buildInventory.getItemActive()).getId();
            if(id!=10) builder.build(obstacles, id, gc);//generalisé le flag ?
            if(buildMode) {
                if(buildInventory.selectSlot(gc)) builder.reset();
            }
        }
        else builder.reset();           //NUL !

        if(deleteMode){
            builder.delete(obstacles, gc);
        }
    }

    public void displayConstruction(List<Obstacle> obstacles, GameContainer gc , Graphics g){
        builder.displayConstruction(obstacles, gc , g);
        builder.displayCursor(gc, g);
    }

    private void move(GameContainer gc){
        Input input = gc.getInput();

        if(input.isKeyPressed(Input.KEY_UP) && Game.chunkY > 0) Game.chunkY--;
        if(input.isKeyPressed(Input.KEY_DOWN)) Game.chunkY++;
        if(input.isKeyPressed(Input.KEY_LEFT) && Game.chunkX > 0) Game.chunkX--;
        if(input.isKeyPressed(Input.KEY_RIGHT)) Game.chunkX++;
    }

    private void useButton(GameContainer gc){           //Global
        hideEntity.isActive(gc);
        Game.hideEntities = hideEntity.isButtonPressed();
    }

    public void switchButtonOff(){
        buildButton.setButtonPressed(false);
        deleteButton.setButtonPressed(false);
        hideEntity.setButtonPressed(false);
        Game.hideEntities = false;
    }

    public void saveMap(List<Obstacle> obstacles, GameContainer gc){
        if (saveData.isActive(gc)) {
            File folder = selectFolder("Save map");

            saveObstacle(obstacles, folder);
        }
    }

    public File selectFolder(String title){
        File file = null;

        JFileChooser fileChooser = new JFileChooser();      //Instance de fileChooser
        fileChooser.setDialogTitle(title);                 //Titre

        fileChooser.setCurrentDirectory(new File("save/"));             //Current directory
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showDialog(null, title);       //CREATION DE LA FENETRE

        if (userSelection == JFileChooser.APPROVE_OPTION) {         //On a bien appuyé sur OK
            file = fileChooser.getSelectedFile();
        }

        return file;
    }

    private void saveObstacle(List<Obstacle> obstacles, File folder) {
        if (folder != null) {
            String fileName = "obstacles.xml";

            String folderPath = folder.getAbsolutePath();

            Document dom;
            Element e = null;

            // instance of a DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            try {
                // use factory to get an instance of document builder
                DocumentBuilder db = dbf.newDocumentBuilder();
                // create instance of DOM
                dom = db.newDocument();

                // create the root element
                Element obstaclesElement = dom.createElement("obstacles");
                //Element obstaclesElement = dom.createElement("obstaclesElement");

                // create data elements and place them under root
                for (Obstacle obstacle : obstacles) {               //Bonne pratique ? ==> pas tres modulaire
                    if (obstacle instanceof Structure) {
                        if (obstacle instanceof Wall) e = dom.createElement("wall");
                        else if (obstacle instanceof Floor) e = dom.createElement("floor");

                        e.setAttribute("x", String.valueOf(obstacle.getX()));
                        e.setAttribute("y", String.valueOf(obstacle.getY()));
                        e.setAttribute("dx", String.valueOf(((Structure) obstacle).getDx()));
                        e.setAttribute("dy", String.valueOf(((Structure) obstacle).getDy()));
                        e.setAttribute("durability", String.valueOf(obstacle.getDurability()));
                        obstaclesElement.appendChild(e);

                    }
                    if (obstacle instanceof Vegetation) {
                        if (obstacle instanceof Tree) e = dom.createElement("tree");
                        else if (obstacle instanceof Rock) e = dom.createElement("rock");

                        e.setAttribute("x", String.valueOf(obstacle.getX()));
                        e.setAttribute("y", String.valueOf(obstacle.getY()));
                        e.setAttribute("durability", String.valueOf(obstacle.getDurability()));
                        e.setAttribute("size", String.valueOf(((Vegetation) obstacle).getSizeGlobal()));
                        //e.appendChild(dom.createTextNode(role1));
                        obstaclesElement.appendChild(e);
                    }
                }

                dom.appendChild(obstaclesElement);

                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                //tr.setOutputProperty("{http://xml.Apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(folderPath+"/"+fileName)));

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void loadMap(List<Obstacle> obstacles, GameContainer gc){
        if (loadData.isActive(gc)) {
            File file = selectFolder("Load map");
            ////////
            //LOAD//
            ////////
            if(file != null) {
                obstacles.clear();
                loadObstacle(obstacles, file);
            }
        }
    }

    private void loadObstacle(List<Obstacle> obstacles, File folder) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(folder.getAbsolutePath()+"/obstacles.xml");
            doc.getDocumentElement().normalize();

            // Recherche tous les elements
            //NodeList nodeList = doc.getElementsByTagName("obstacles");
            NodeList nodeList = doc.getDocumentElement().getChildNodes();       //Acces a tout les elements
            //NodeList nodeList = doc.getChildNodes();

            //System.out.println(doc.getDocumentElement().getTagName());

            // Pour chaque élément XML de la liste
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    // Récupère l'élément
                    Element element = (Element) node;

                    Obstacle newObstacle = null;

                    float x = Float.parseFloat(element.getAttribute("x"));                 //Element commun
                    float y = Float.parseFloat(element.getAttribute("y"));

                    int durability = Integer.parseInt(element.getAttribute("durability"));

                    switch(element.getTagName()){
                        case "wall" :
                            float dx = Float.parseFloat(element.getAttribute("dx"));
                            float dy = Float.parseFloat(element.getAttribute("dy"));

                            newObstacle = new Wall(x, y, dx, dy);

                            break;

                        case "floor" :
                            dx = Float.parseFloat(element.getAttribute("dx"));
                            dy = Float.parseFloat(element.getAttribute("dy"));

                            newObstacle = new Floor(x, y, dx, dy);

                            break;

                        case "tree" :
                            float sizeTree = Float.parseFloat(element.getAttribute("size"));        //Repetition

                            newObstacle = new Tree(x, y, sizeTree);

                            break;

                        case "rock" :
                            float sizeRock = Float.parseFloat(element.getAttribute("size"));    //Repetition

                            newObstacle = new Rock(x, y, sizeRock);

                            break;
                    }

                    obstacles.add(newObstacle);

                    //System.out.println("x : "+element.getAttribute("x"));
                    //System.out.println("durability : "+element.getAttribute("durability"));
                    System.out.println("*-------" + element.getTagName() + "-------*");
                    int nbAttr = element.getAttributes().getLength();

                    for (int j = 0; j < nbAttr; j++) {
                        System.out.println(element.getAttributes().item(j));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void generateMap(Game game, GameContainer gc){
        if(generateMap.isActive(gc)) game.clearGeneration();
    }

    void clearEntities(List<Animal> animals, GameContainer gc){
        if(clearEntity.isActive(gc)) animals.clear();
    }
}




    /*public void saveMap(List<Obstacle> obstacles, GameContainer gc) {
        if (saveData.isActive(gc)) {
            File file = null;

            JFileChooser fileChooser = new JFileChooser();      //Instance de fileChooser
            fileChooser.setDialogTitle("Save map");                 //Titre
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(           //Filtre
                    "Fichier .xml", "xml");
            fileChooser.setFileFilter(filter);

            fileChooser.setCurrentDirectory(new File("."));             //Current directory
            int userSelection = fileChooser.showSaveDialog(null);       //CREATION DE LA FENETRE

            if (userSelection == JFileChooser.APPROVE_OPTION) {         //On a bien appuyé sur OK
                file = fileChooser.getSelectedFile();*/

                /*if (!file.getName().toLowerCase().endsWith(".xml")) {
                    file = new File(file.getParentFile(), file.getName() + ".xml");
                }

                System.out.println("Save as file: " + file.getAbsolutePath());*/
            /*}

            saveStructure(file);
        }
    }*/
