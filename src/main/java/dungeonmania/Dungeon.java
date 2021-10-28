package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.Player;
import dungeonmania.entities.Moving.*;
import dungeonmania.items.Item;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

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
                
            } else {
                this.entities.add(new Entity((JSONObject)entity));
            }
        }
        this.inventory = new ArrayList<Item>();
        this.buildables = new ArrayList<String>();
        this.goals = "";
        this.complete = false;


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

    public void removeItem(String type) {
        for (Iterator<Item> item = inventory.iterator(); item.hasNext();) {
            Item value = item.next();
            if (value.getType().equals(type)) item.remove();
        }
    }

    public void removeEntity(String stringId) {
        for (Iterator<Entity> entity = entities.iterator(); entity.hasNext();) {
            Entity value = entity.next();
            if (value.getId().equals(stringId)) entity.remove();
        }
    }

    public Entity getEntity(String stringId) {
        for (Entity entity : this.entities) {
            if (entity.getId().equals(stringId)) {
                return entity;
            }
        }
        return null;
    }

    public Player getPlayer() {
        return this.player;
    }
    
    public List<Entity> getEntities() {
        return this.entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
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
