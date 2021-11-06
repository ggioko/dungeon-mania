package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.CompositeGoals;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import spark.utils.IOUtils;
import dungeonmania.entities.*;
import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.Moving.Spider;
import dungeonmania.entities.Static.Door;
import dungeonmania.entities.Static.Portal;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.Bomb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DungeonManiaController {
    Dungeon currentDungeon;
    int ticknum;
    int invincibilityTicks;
    int invisibilityTicks;
    private final List<String> buildables = Arrays.asList("bow", "shield");
    public DungeonManiaController() {
        this.ticknum = 0;
        this.invincibilityTicks = 0;
        this.invisibilityTicks = 0;
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        //create list of entity response based on json from dungeon
        JSONObject obj;
        
        // If gamemode doesnt exist
        if (!gameMode.equalsIgnoreCase("standard") && !gameMode.equalsIgnoreCase("peaceful") && !gameMode.equalsIgnoreCase("hard")) {
            throw new IllegalArgumentException();
        }

        // If dungeon name doesnt exist
        ArrayList<String> dungeonNames = new ArrayList<String>();
        dungeonNames = setDungeonNames(dungeonName);

        if (!checkIfDungeonExists(dungeonName, dungeonNames)) {
            throw new IllegalArgumentException();
        }
        
        try {
            obj = new JSONObject(FileLoader.loadResourceFile("/dungeons" + "/" + dungeonName + ".json"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        Dungeon newDungeon = new Dungeon(dungeonName, obj, gameMode);
        currentDungeon = newDungeon;
        return newDungeon.createResponse();
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        //turn a dungeon class into a .json file and save it
        JSONArray entities = new JSONArray();
        JSONObject saveGame = new JSONObject();
        //width, height, entities
        for (Entity e : currentDungeon.entities) {
            //x, y, type
            if ((e instanceof Door) || (e instanceof Key)) {
                entities.put(new JSONObject("{x:" + e.getPosition().getX() + ",y:" + e.getPosition().getY() + ",type:" + e.getType() +  ",key:" + e.getKey() + "}"));
            } else {
                entities.put(new JSONObject("{x:" + e.getPosition().getX() + ",y:" + e.getPosition().getY() + ",type:" + e.getType() + "}"));
            }
        }
        saveGame.put("entities", entities);
        saveGame.put("gamemode", currentDungeon.gameMode);
        //turn into file
        FileWriter filewriter;
        try {
            File file = new File("src" + File.separator + "main" + File.separator + "java" + File.separator + "dungeonmania" + File.separator + "saves" + File.separator + name + ".json");
            file.setWritable(true);
            file.setReadable(true);
            file.createNewFile();
            filewriter = new FileWriter(file);
            file.getAbsolutePath();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return null;
        }

        try {
            filewriter.write(saveGame.toString());
            filewriter.close();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return null;
        }

        return currentDungeon.createResponse();

    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        //should be the same as new game
        //create list of entity response based on json from dungeon
        JSONObject obj;
        try {
            InputStream is = new FileInputStream("src" + File.separator + "main" + File.separator + "java" + File.separator + "dungeonmania" + File.separator + "saves" + File.separator + name + ".json");
            obj = new JSONObject(IOUtils.toString(is));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        String gameMode = obj.getString("gamemode");
        // If gamemode doesnt exist
        if (!gameMode.equalsIgnoreCase("standard") && !gameMode.equalsIgnoreCase("peaceful") && !gameMode.equalsIgnoreCase("hard")) {
            throw new IllegalArgumentException();
        }
        
        Dungeon newDungeon = new Dungeon(name, obj, gameMode);
        currentDungeon = newDungeon;
        return newDungeon.createResponse();
    }

    public List<String> allGames() {
        File folder = new File("src/main/java/dungeonmania/saves");
        File[] listOfFiles = folder.listFiles();
        List<String> result = new ArrayList<String>();
        for (File f : listOfFiles) {
            result.add(f.getName().replace(".json", ""));
        }
        return result;
    }

    public boolean checkIfDungeonExists(String dungeonName, ArrayList<String> dungeonNames) {
        boolean exists = false;
        for (String name : dungeonNames) {
            if (name.equals(dungeonName)) {
                exists = true;
            }
        }
        return exists;
    }

    public ArrayList<String> setDungeonNames(String dungeonName) {
        ArrayList<String> dungeonNames = new ArrayList<String>();
        dungeonNames.add("advanced-2");
        dungeonNames.add("boulders");
        dungeonNames.add("advanced");
        dungeonNames.add("crafting");
        dungeonNames.add("doors");
        dungeonNames.add("exist");
        dungeonNames.add("exit");
        dungeonNames.add("goals");
        dungeonNames.add("interact");
        dungeonNames.add("portals");
        dungeonNames.add("potions");
        dungeonNames.add("maze");
        dungeonNames.add("characterTest");
        dungeonNames.add("interactTest");
        dungeonNames.add("bombTest");
        return dungeonNames;
    }
    
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        if (itemUsed != null) {
            Entity item = currentDungeon.getItemUsed(itemUsed);
            if (currentDungeon.getItemUsed(itemUsed) == null) {
                throw new InvalidActionException("No item in inventory");
            }
            else if (item.getType().equals("bomb") || item.getType().equals("health_potion") || item.getType().equals("invincibility_potion") 
                    || item.getType().equals("invisibility_potion")) {
            }
            else throw new IllegalArgumentException("itemUsed is not a valid item");
        }

        //gets the item that is used 
        if (ticknum >= 25) {
            currentDungeon = Spider.spawn(currentDungeon);
            this.ticknum = 0;
        }
        this.ticknum++;

        // ENEMY PATHING
        currentDungeon.pathing(movementDirection);
        
        if (!currentDungeon.gameMode.equals("Peaceful")) {
            // making sure that enemy interactions dont happen when on the peaceful game mode
            currentDungeon.battle(currentDungeon);
            if (currentDungeon.battle(currentDungeon) == null) {
                currentDungeon = null;
            }
        }
        //mercenary moves again if battling
        currentDungeon.MercenaryBattleMovement(currentDungeon);
        currentDungeon.getPlayer().setBattling(false);

        List<Spawner> spawners = new ArrayList<>();
        for (Entity e : currentDungeon.entities) {
            //spawn zombies
            if (e instanceof Spawner) {
                spawners.add((Spawner)e);
            }
        }
        for (Spawner s : spawners) {
            s.spawn(currentDungeon);
        }
        
        // SIMPLE AND COMPLEX GOALS
        boolean teleported = false;
        for (Entity e : currentDungeon.entities) {
            //boulder movement and floor switch
            if (e instanceof Boulder) {
                currentDungeon.player = ((Boulder)e).move(movementDirection, currentDungeon.player, currentDungeon.entities);
            }
            if (e instanceof FloorSwitch) {
                ((FloorSwitch)e).trigger(currentDungeon.entities);
            }
            //doors
            if (e instanceof Door) {
                currentDungeon = ((Door)e).unlock(currentDungeon.entities, currentDungeon.inventory, currentDungeon, currentDungeon.player, movementDirection);
            }
            if (e instanceof Portal) {
                if (e.getPosition().equals(currentDungeon.player.getPosition()) && !teleported) {
                    currentDungeon.player.setPosition(((Portal)e).getCoords().translateBy(movementDirection));
                    teleported = true;
                }
            }
        }
        
        //BOMB LOGIC
        if (itemUsed != null) {
            if (currentDungeon.getItemUsed(itemUsed).getType().equals("bomb")) {
                Bomb bomb = (Bomb) currentDungeon.getItemUsed(itemUsed);
                bomb.placeBomb(currentDungeon);
            }
        }

        for (Entity e : currentDungeon.getEntities()) {
            // bomb explosion
            if (e instanceof Bomb) {
                Bomb b = (Bomb) e;
                if (b.isActivated(currentDungeon) && b.isPlaced()) {
                    b.explode(currentDungeon);
                }
            }
        }

        // POTION LOGIC
        // Invincibility potion
        if (invincibilityTicks >= 10) {
            currentDungeon.player.setInvincibilityPotionEffect(false);
            this.invincibilityTicks = 0;
        }
        if (currentDungeon.player.isInvincibilityPotionEffect()) {
            this.invincibilityTicks++;
        }
        currentDungeon = InvincibilityPotion.addEffects(currentDungeon, itemUsed, currentDungeon.player, currentDungeon.inventory);

        // Invisibility potion
        if (invisibilityTicks >= 10) {
            currentDungeon.player.setInvisibilityPotionEffect(false);
            this.invisibilityTicks = 0;
        }
        if (currentDungeon.player.isInvisibilityPotionEffect()) {
            this.invisibilityTicks++;
        }
        currentDungeon = InvisibilityPotion.addEffects(currentDungeon, itemUsed, currentDungeon.player, currentDungeon.inventory);

        // Health potion
        currentDungeon = HealthPotion.addEffects(currentDungeon, itemUsed, currentDungeon.player, currentDungeon.inventory);
        
        
        // ITEM PICKUP
        currentDungeon.itemPickup();
        if (currentDungeon.goalTree != null && !currentDungeon.goalTree.isComplete()) {
            currentDungeon.goalTree.checkGoalState(currentDungeon.entities, currentDungeon.player);
            ((CompositeGoals) currentDungeon.goalTree).checkComplete();
        }
        return currentDungeon.createResponse();
    }
    
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        if (currentDungeon.getEntity(entityId) == null) {
            throw new IllegalArgumentException("entityId is not a valid entity ID");
        }
        else if (currentDungeon.getEntity(entityId).getType().equals("mercenary")) {
            Mercenary mercenary = (Mercenary) currentDungeon.getEntity(entityId);

            if (mercenary.isInBribableRange(currentDungeon.getPlayer().getPosition())) {
                if (currentDungeon.getItem("treasure") == null) {
                    throw new InvalidActionException("No treasure in inventory");
                }
                else {
                    currentDungeon.removeItem("treasure");
                    mercenary.setBribed(true);
                    mercenary.setInteractable(false);
                    currentDungeon.getPlayer().setAlly(true);
                }
            }
            else {
                throw new InvalidActionException("Mercenary not in range");
            }
        }
        else if (currentDungeon.getEntity(entityId).getType().equals("zombie_toast_spawner")) {
            Spawner spawner = (Spawner) currentDungeon.getEntity(entityId);
            if (spawner.isInDestroyableRange(currentDungeon.getPlayer().getPosition())) {
                if (currentDungeon.getItem("sword") == null && currentDungeon.getItem("bow") == null) {
                    throw new InvalidActionException("No weapon in inventory");
                }
                else {
                    for (Entity i : currentDungeon.inventory) {
                        if (i.getType().equals("sword") || i.getType().equals("bow")) {   
                            CollectableEntity item = (CollectableEntity) i;
                            int newDurability = item.getDurability() - 1;
                            item.setDurability(newDurability);
                            currentDungeon.removeEntity(entityId);
                        }
                    }
                }
            } 
            else {
                throw new InvalidActionException("Spawner not in range");
            }
        }
        return currentDungeon.createResponse();
    }
    
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (!buildables.contains(buildable)) {
            throw new IllegalArgumentException();
        }

        if (!currentDungeon.buildables.contains(buildable)) {
            throw new InvalidActionException("Not Enough Materials");
        }
        currentDungeon.createBuildable(buildable);

        return currentDungeon.createResponse();

    }
}