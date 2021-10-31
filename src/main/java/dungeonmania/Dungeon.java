package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Door;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.Static.Portal;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.Wood;
import dungeonmania.entities.Player;
import dungeonmania.entities.Moving.*;
import dungeonmania.items.Item;
import dungeonmania.items.buildable.Buildable;
import dungeonmania.items.buildable.Shield;
import dungeonmania.items.buildable.Bow;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import javax.sound.sampled.Port;

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
        this.gameMode = gameMode;
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
                    door.setType("door_2");
                } else {
                    door.setType("door_1");
                    doorcreated = true;
                }
                this.entities.add(door);
            } else if (((JSONObject)entity).getString("type").equals("key")) {
                this.entities.add(new Key((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("sword")) {
                this.entities.add(new Sword((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("armour")) {
                this.entities.add(new Armour((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("sword")) {
                this.entities.add(new Sword((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("health_potion")) {
                this.entities.add(new HealthPotion((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("wood")) {
                this.entities.add(new Wood((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("invincibility_potion")) {
                this.entities.add(new InvincibilityPotion((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("invisibility_potion")) {
                this.entities.add(new InvisibilityPotion((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("boulder")) {
                this.entities.add(new Boulder((JSONObject)entity));
            } else if (((JSONObject)entity).getString("type").equals("portal")) {
                Position coords = new Position(0,0);
                for (Object o : entities.getJSONArray("entities")){
                    try {
                        if (((JSONObject)o).getString("colour").equals(((JSONObject)entity).getString("colour"))) {
                            if (((((JSONObject)o).getInt("x") != (((JSONObject)entity).getInt("x"))) || (((JSONObject)o).getInt("y") != (((JSONObject)entity).getInt("y"))))) {
                                coords = new Position(((JSONObject)o).getInt("x"), ((JSONObject)o).getInt("y"));
                            }
                        }
                    } catch (Exception e) {
                        //TODO: handle exception
                    }
                }
                Portal portal = new Portal((JSONObject)entity, coords);
                portal.setType("portal_" + ((JSONObject)entity).getString("colour"));
                this.entities.add(portal);
            } else if (((JSONObject)entity).getString("type").equals("switch")) {
                this.entities.add(new FloorSwitch((JSONObject)entity));
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

    public void setItems(List<Item> items) {
        this.inventory = items;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Item> getItems() {
        return this.inventory;
    }

    public void pathing(Direction direction) {
        //make a list of walls
        List<Entity> walls = new ArrayList<Entity>();
        for (Entity e : this.entities) {
            if (e instanceof Mercenary){
                Mercenary m = (Mercenary) e;
                if (m.isBribed()) {
                    walls.add(m);
                }
            }
            else if (e instanceof Wall || e instanceof Door || e instanceof MovingEntity) {
                if (e instanceof Wall || e instanceof MovingEntity) {
                    walls.add(e);
                } else {
                    if (!(((Door)e).getType().equals("door_unlocked"))) {
                        walls.add(e);
                    }
                }
            }
        }
        for (Entity e : this.entities) {
            if (e instanceof Player) {
                e.move(this.player.getPosition().translateBy(direction), walls);
            } if (e instanceof Mercenary) {
                walls.add(this.player);
                Mercenary m = (Mercenary) e;
                if (m.isInBribableRange(this.player.getPosition()) && this.player.isBattling()) {
                    e.move(this.player.getPosition(), walls);
                }
                e.move(this.player.getPosition(), walls);
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
        if (!inventory.isEmpty()) {
            if (Buildable.getBuildable("bow").isBuildable(inventory)) {
                if (!buildables.contains("bow")) {buildables.add("bow");}
            } else {buildables.remove("bow");}

            if (Buildable.getBuildable("shield").isBuildable(inventory)) {
                if (!buildables.contains("shield")) {buildables.add("shield");}
            } else {buildables.remove("shield");}
        }
            
        return new DungeonResponse(this.dungeonId, this.dungeonName, entityList, itemList, this.buildables, this.goals);
    }

    public void itemPickup() {
        for (Iterator<Entity> it = entities.iterator(); it.hasNext();) {
            Entity anEntity = it.next();
            EntityResponse entityResponse = anEntity.createResponse();
            if (entityResponse.getPosition().equals(player.getPosition()) && anEntity.isCollectable()) {
                if ((entityResponse.getType().equals("key_1") || entityResponse.getType().equals("key_2")) && hasKey()) {
                } else {
                    inventory.add(new Item(entityResponse.getId(), entityResponse.getType()));
                    it.remove();
                }
            }
        }
    }

    public boolean hasKey() {
        for (Item i : inventory) {
            if (i.getType().equals("key_1") || i.getType().equals("key_2")) {
                return true;
            }
        }
        return false;
     }

    public void removeItemFromInventory(String type) {
        for (Iterator<Item> it = inventory.iterator(); it.hasNext();) {
            Item anItem = it.next();
            if (anItem.getType().equals(type)) {
                it.remove();
                break;
            }
        } 
    }

    public void removeItem(String type) {
        for (Iterator<Item> item = inventory.iterator(); item.hasNext();) {
            Item value = item.next();
            if (value.getType().equals(type)) item.remove();
        }
    }


    public void createBuildable(String type) {
        Buildable buildable = Buildable.getBuildable(type);
        if (buildables.contains(buildable.getType())) {
            Map<String, Integer> recipe = buildable.materialNeeded(inventory);
            for (Map.Entry<String, Integer> e : recipe.entrySet()) {
                for (int i = 0; i < e.getValue(); i++) {
                    removeItemFromInventory(e.getKey());
                }
            }
            inventory.add(buildable);
        }
    }
    
    public void enemyDeath(MovingEntity enemy) {
        //remove enemy from entities and give player loot
        this.entities.remove(enemy);
        if (enemy instanceof Mercenary) {
            
        }
    }

    public Buildable getBuildableFromInventory(String type) {
        for (Item i : inventory) {
            if (i.getType().equals("shield")) {
                return (Shield) i;
            } else if (i.getType().equals("bow")) {
                return (Bow) i;
            } 
        }
        return null;
    }



}
