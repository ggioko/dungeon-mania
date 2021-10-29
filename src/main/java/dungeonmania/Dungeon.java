package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Door;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.Player;
import dungeonmania.entities.Moving.*;
import dungeonmania.items.Item;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class Dungeon {
    String dungeonId;
    String dungeonName;
    List<Entity> entities;
    List<Item> inventory;
    List<String> buildables;
    String goals;
    List<AnimationQueue> animations;
    Player player;
    String gameMode;
    boolean complete;
    String goaltype;
    List<String> goalsToComplete;
    List<String> goalsCompleted;
    boolean nogoals;


    public Dungeon(String dungeonName, JSONObject entities, String gameMode) {
        this.dungeonName = dungeonName;
        this.dungeonId = dungeonName;
        this.entities = new ArrayList<Entity>();
        boolean keycreated = false;
        boolean doorcreated = false;
        for (Object entity : entities.getJSONArray("entities")) {
            
            if (((JSONObject)entity).getString("type").equals("player")) {
                this.player = new Player((JSONObject)entity);
                this.entities.add(this.player);
            } else if (((JSONObject)entity).getString("type").equals("mercenary")) {
                this.entities.add(new Mercenary((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("spider")) {
                this.entities.add(new Spider((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("zombie_toast")) {
                this.entities.add(new Zombie((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("wall")) {
                this.entities.add(new Wall((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("zombie_toast_spawner")) {
                if (gameMode == "hard") {
                    this.entities.add(new Spawner((JSONObject)entity, 15));
                } else {
                    this.entities.add(new Spawner((JSONObject)entity, 20));
                }
                
            } else if (((JSONObject)entity).getString("type").equals("treasure")) {
                this.entities.add(new Treasure((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("door")) {
                Door door = new Door((JSONObject)entity);
                if (doorcreated) {
                    door.setType("key_2");
                } else {
                    door.setType("key_1");
                    doorcreated = true;
                }
                this.entities.add(door);
            } else if (((JSONObject)entity).getString("type").equals("key")) {
                Key key = new Key((JSONObject)entity);
                if (keycreated) {
                    key.setType("key_2");
                } else {
                    key.setType("key_1");
                    keycreated = true;
                }
                this.entities.add(key);
            } else {
                this.entities.add(new Entity((JSONObject)entity));
            }
        }
        this.inventory = new ArrayList<Item>();
        this.buildables = new ArrayList<String>();
        try {
            this.goals = ":" + entities.getJSONObject("goal-condition").getString("goal");
            if (this.goals.equals(":AND") || this.goals.equals(":OR")) {
                this.goals = "";
                for (Object o : entities.getJSONObject("goal-condition").getJSONArray("subgoals")) {
                    this.goals += ":" + ((JSONObject)o).getString("goal") + " ";
                    this.goals += entities.getJSONObject("goal-condition").getString("goal") + " ";
                }
                this.goals = this.goals.substring(0,this.goals.length()-5);
                this.goaltype = entities.getJSONObject("goal-condition").getString("goal");
            }
            this.complete = false;
            this.goalsToComplete = Arrays.asList(this.goals.replace(":","").replace(" ", "").split(this.goaltype));
            this.goalsCompleted = new ArrayList<>();
        } catch (Exception e) {
            //TODO: handle exception
            this.nogoals = true;
        }
        
    }

    //getters

    public Item getItem(String type) {
        for (Item i : this.inventory) {
            if (i.getType().equals(type)) {
                return i;
            }
        }
        return null;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void setItems(List<Item> items) {
        this.inventory = items;
    }

    public List<Item> getItems() {
        return this.inventory;
    }

    public void pathing(Direction direction) {
        //make a list of walls
        List<Wall> walls = new ArrayList<Wall>();
        for (Entity e : this.entities) {
            if (e instanceof Wall) {
                walls.add((Wall)e);
            }
        }
        for (Entity e : this.entities) {
            if (e instanceof Player) {
                e.move(this.player.getPosition().translateBy(direction), walls);
            } else {
                e.move(this.player.getPosition(), walls);
            }
        }
    }

    public DungeonResponse createResponse() {
        List<EntityResponse> entityList = new ArrayList<EntityResponse>();
        for (Entity e : this.entities) {
            entityList.add(e.createResponse());
        }
        List<ItemResponse> itemList = new ArrayList<ItemResponse>();
        for (Item i : this.inventory) {
            itemList.add(i.creatResponse());
        }
        return new DungeonResponse(this.dungeonId, this.dungeonName, entityList, itemList, this.buildables, this.goals);
    }

    public void enemyDeath(MovingEntity enemy) {
        //remove enemy from entities and give player loot
        this.entities.remove(enemy);
    }

}
