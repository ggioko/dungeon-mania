package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.Sword;
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


    public Dungeon(String dungeonName, JSONObject entities, String gameMode) {
        this.dungeonName = dungeonName;
        this.dungeonId = dungeonName;
        this.entities = new ArrayList<Entity>();
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
            } else if (((JSONObject)entity).getString("type").equals("sword")) {
                this.entities.add(new Sword((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("Armour")) {
                this.entities.add(new Armour((JSONObject)entity));
            } else {
                this.entities.add(new Entity((JSONObject)entity));
            }
        }
        this.inventory = new ArrayList<Item>();
        this.buildables = new ArrayList<String>();

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

    public void pathing(Direction direction) {
        //make a list of walls
        List<Entity> walls = new ArrayList<Entity>();
        for (Entity e : this.entities) {
            if (e instanceof Wall) {
                walls.add(e);
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
        if (enemy instanceof Mercenary) {
            
        }
    }

}
