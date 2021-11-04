package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.items.buildable.Buildable;
import dungeonmania.items.Item;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.entities.*;
import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.items.*;
import dungeonmania.entities.Moving.MovingEntity;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.Moving.Spider;
import dungeonmania.entities.Static.Door;
import dungeonmania.entities.Static.Portal;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Treasure;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            entities.put(new JSONObject("{x:" + e.getPosition().getX() + ",y:" + e.getPosition().getY() + ",type:" + e.getType() + "}"));
        }
        saveGame.put("entities", entities);
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
        return null;
    }

    public List<String> allGames() {
        return new ArrayList<>();
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
        dungeonNames.add("interact");
        dungeonNames.add("portals");
        dungeonNames.add("potions");
        dungeonNames.add("maze");
        dungeonNames.add("characterTest");
        return dungeonNames;
    }
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        if (itemUsed != null) {
            Item item = currentDungeon.getItemUsed(itemUsed);
            if (currentDungeon.getItemUsed(itemUsed) == null) {
                throw new InvalidActionException("No item in inventory");
            }
            else if (item.getType().equals("bomb") || item.getType().equals("health_potion") || item.getType().equals("invisibility_potion") 
                    || item.getType().equals("invinsibility_potion")) {
            }
            else throw new IllegalArgumentException("itemUsed is not a valid item");
        }

        //gets the item that is used 
        if (ticknum >= 10) {
            currentDungeon = Spider.spawn(currentDungeon);
            this.ticknum = 0;
        }
        this.ticknum++;

        currentDungeon.getItem(itemUsed);
        
        
        // ENEMY PATHING
        currentDungeon.pathing(movementDirection);
        
        if (!currentDungeon.gameMode.equals("Peaceful")) {
            // making sure that enemy interactions dont happen when on the peaceful game mode
            currentDungeon = enemyInteraction(currentDungeon, itemUsed);
        }
        //mercenary moves again if battling
        currentDungeon.MercenaryBattleMovement(currentDungeon);
        currentDungeon.getPlayer().setBattling(false);
        //spawn zombies
        List<Spawner> spawners = new ArrayList<>();
        for (Entity e : currentDungeon.entities) {
            if (e instanceof Spawner) {
                spawners.add((Spawner)e);
            }
        }
        for (Spawner s : spawners) {
            s.spawn(currentDungeon);
        }
        
        
        // SIMPLE AND COMPLEX GOALS
        boolean treasureComplete = true;
        boolean enemiesComplete = true;
        boolean teleported = false;
        for (Entity e : currentDungeon.entities) {
            if (e instanceof Treasure) {
                treasureComplete = false;
            }
            if (e instanceof MovingEntity || e instanceof Spawner) {
                enemiesComplete = false;
            }
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
        if (!currentDungeon.nogoals) {
            //add treasure to completed goals if it is completed
            if (treasureComplete) {
                currentDungeon.goalsCompleted.add("treasure");
            }
            //add enemies to completed if it is completed
            if (enemiesComplete) {
                currentDungeon.goalsCompleted.add("enemies");
            }
            if (currentDungeon.goaltype.equals("AND")) {
                if (currentDungeon.goalsCompleted.containsAll(currentDungeon.goalsToComplete)) {
                    //game won
                    currentDungeon.complete = true;
                    currentDungeon.goals = "";
                }
            } else if (currentDungeon.goaltype.equals("OR")) {
                for (String s : currentDungeon.goalsCompleted) {
                    if (currentDungeon.goalsToComplete.contains(s)) {
                        //game won
                        currentDungeon.complete = true;
                        currentDungeon.goals = "";
                    }
                }
            } else {
                if (currentDungeon.goalsCompleted.contains(currentDungeon.goals.replace(":", "").replace(" ", ""))) {
                    // Game won
                    currentDungeon.complete = true;
                    currentDungeon.goals = "";
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
        return currentDungeon.createResponse();
    }
    
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        System.out.println(entityId);
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
                    for (Item item : currentDungeon.inventory) {
                        if (item.getType().equals("sword") || item.getType().equals("bow")) {      
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

    public Dungeon enemyInteraction(Dungeon current, String itemUsed) {
        for (Entity e : current.entities) {
            //for all moving entities aka enemies
            if (e instanceof MovingEntity) {
                if (e instanceof Mercenary) {
                    Mercenary mercenary = (Mercenary) e;
                    if (mercenary.isBribed()) {
                        continue;
                    }
                }
                MovingEntity enemy = (MovingEntity) e;
                //if the entity is on the same ssquare as character
                if (e.getPosition().equals(current.player.getPosition())) {
                    boolean battleOver = false;
                    currentDungeon.getPlayer().setBattling(true);
                    while (!battleOver) {
                        //change health values
                        double playerHP = current.player.getHealth();
                        double enemyHP = enemy.getHealth();
                        double playerAD = current.player.getAttack();
                        double enemyAD = enemy.getAttack();
                        
                        //Armour cuts enemy damage to half
                        if (currentDungeon.getItem("armour") != null) {
                            enemyAD = enemyAD/2;
                            Armour.durability -= 1;
                            Armour.isBroken(current.inventory);
                            // decrease armour durability by 1 // TODO
                        }

                        if (currentDungeon.getItem("sword") != null) {
                            enemy.setHealth(enemyHP - 1);
                            Sword.durability -= 1;
                            Sword.isBroken(current.inventory);
                            // decrease sword durability by 1 // TODO
                        }
                        
                        //Shield cuts enemy damage to half
                        //If player has shield and armour, 75% of damage is negated.
                        if (current.getItem("shield") != null) {
                            enemyAD = current.getShield().effect(enemyAD, current.inventory);
                        }
                       
                        //Bow allows player to attack twice
                        if (current.getItem("bow") != null) { 
                            current.getBow().effect(enemy, enemyHP, playerHP, playerAD, currentDungeon.inventory);
                        }
                        
                        //Player and Enemy damage each other
                        current.player.setHealth(playerHP - ((enemyHP * enemyAD) / 10));
                        enemy.setHealth(enemyHP - ((playerHP * playerAD) / 5));

                        //Has an ally Mercenary
                        if (currentDungeon.getPlayer().haveAlly()) {
                            enemy.setHealth(enemyHP - ((playerHP * playerAD) / 5));
                        }

                        
                        

                        if (currentDungeon.player.isInvincibilityPotionEffect() == true) {
                            battleOver = true;
                        }
                        

                        if (playerHP <= 0) {
                            //one ring
                            if (currentDungeon.getItem("one_ring") != null) {
                                current.getPlayer().setHealth(10);
                                currentDungeon.removeItem("one_ring");
                            }
                            //game over
                            else {
                                return null;
                            }
                        } else if (enemyHP <= 0) {
                            //enemy is dead
                            current.enemyDeath(enemy);
                            battleOver = true;
                        }
                    

                    }
                    return current;
                }
            }
            
        }
        return current;
    }
}