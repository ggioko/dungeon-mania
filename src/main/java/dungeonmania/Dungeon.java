package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Static.Door;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.collectable.Key;
import dungeonmania.entities.collectable.OneRing;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.Static.Portal;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.HealthPotion;
import dungeonmania.entities.collectable.InvincibilityPotion;
import dungeonmania.entities.collectable.InvisibilityPotion;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.Treasure;
import dungeonmania.entities.collectable.Wood;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Buildable;
import dungeonmania.entities.collectable.buildable.Shield;
import dungeonmania.goals.CompositeGoals;
import dungeonmania.goals.Goal;
import dungeonmania.goals.GoalLeaf;
import dungeonmania.entities.Player;
import dungeonmania.entities.Moving.*;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.spi.CurrencyNameProvider;

import javax.sound.sampled.Port;

import org.json.JSONArray;
import org.json.JSONObject;


public class Dungeon {
    String dungeonId;
    String dungeonName;
    List<Entity> entities;
    List<Entity> inventory;
    List<String> buildables;
    Goal goalTree;
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
            Object e = EntityFactory.getEntity((JSONObject)entity, gameMode, doorcreated, entities);

            if (e instanceof Player) {
                this.player = (Player)e;
            }

            this.entities.add((Entity)e);
        }
        this.inventory = new ArrayList<Entity>();
        this.buildables = new ArrayList<String>();

        if (entities.has("goal-condition")) {
            this.goalTree = new CompositeGoals(entities.getJSONObject("goal-condition").getString("goal"), false);
            if (entities.getJSONObject("goal-condition").has("subgoals")) {
                this.goalTree.add(setGoals(entities.getJSONObject("goal-condition")));        
            }
            this.goals = getGoals();
        } else {
            this.goals = null;
        }
    }

    //getters
    private Goal setGoals(JSONObject goals) {
        CompositeGoals r = new CompositeGoals(goals.getString("goal"), false);
        if (goals.equals(null)) {
            return null;
        }

        if (goals.has("subgoals")) {
            for (Object o : goals.getJSONArray("subgoals")) {
                if (((JSONObject)o).has("subgoals")) {
                    r.add(setGoals((JSONObject)o));
                } else {
                    Goal newGoal = new GoalLeaf(((JSONObject)o).getString("goal"), false);
                    r.add(newGoal);
                }
            }
        }
        return r;
    }

    private String getGoals() {
        return goalTree.goalsString();
    }


    public Entity getItem(String type) {
        for (Entity i : this.inventory) {
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

    public void setItems(List<Entity> items) {
        this.inventory = items;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Entity> getItems() {
        return this.inventory;
    }

    public Entity getItemUsed(String stringId) {
        for (Entity item : this.inventory) {
            if (item.getId().equals(stringId)) {
                return item;
            }
        }
        return null;
    }

    public void pathing(Direction direction) {
        //make a list of walls
        List<Entity> walls = getWalls();

        for (Entity e : this.entities) {
            if (e instanceof Player) {
                e.move(this.player.getPosition().translateBy(direction), walls);
            } if (e instanceof Mercenary) {
                walls.add(this.player);
                walls.add(e);
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
        for (Entity i : this.inventory) {
            itemList.add(new ItemResponse(i.getId(), i.getType()));
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
                    inventory.add(anEntity);
                    it.remove();
                }
            }
        }
    }

    public boolean hasKey() {
        for (Entity i : inventory) {
            if (i.getType().equals("key_1") || i.getType().equals("key_2")) {
                return true;
            }
        }
        return false;
     }

    public void removeItemFromInventory(String type) {
        for (Iterator<Entity> it = inventory.iterator(); it.hasNext();) {
            Entity anItem = it.next();
            if (anItem.getType().equals(type)) {
                it.remove();
                break;
            }
        } 
    }

    public void removeItem(String type) {
        for (Iterator<Entity> item = inventory.iterator(); item.hasNext();) {
            Entity value = item.next();
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
            int num = (int)Math.floor(Math.random()*(10-1+1)+1);
            if (num == 2) {
                inventory.add(new Armour("armour_drop", "armour"));
            }
        }

        if (enemy instanceof MovingEntity) {
            int num = (int)Math.floor(Math.random()*(50-1+1)+1);
            if (num == 2) {
                inventory.add(new OneRing("one_ring_drop", "one_ring"));
            }
        }
    }

    public void MercenaryBattleMovement(Dungeon current) {
        List<Entity> walls = getWalls();
        for (Entity entity: this.entities) {
            if (entity instanceof Mercenary) {
                Mercenary mercenary = (Mercenary) entity;
                if (mercenary.isInBattleRadius(current.getPlayer().getPosition()) && current.getPlayer().isBattling()) {
                    walls.add(this.player);
                    walls.add(entity);
                    mercenary.move(this.player.getPosition(), walls);
                }
            }
        }
    }

    public Dungeon battle(Dungeon current) {
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
                    this.getPlayer().setBattling(true);
                    while (!battleOver) {
                        //change health values
                        double playerHP = current.player.getHealth();
                        double enemyHP = enemy.getHealth();
                        double playerAD = current.player.getAttack();
                        double enemyAD = enemy.getAttack();
                        
                        //Armour cuts enemy damage to half
                        if (this.getItem("armour") != null) {
                            enemyAD = enemyAD/2;
                            Armour.durability -= 1;
                            Armour.isBroken(current.inventory);
                            // decrease armour durability by 1 // TODO
                        }

                        if (this.getItem("sword") != null) {
                            enemy.setHealth(enemyHP - 1);
                            Sword.durability -= 1;
                            Sword.isBroken(current.inventory);
                            // decrease sword durability by 1 // TODO
                        }
                        
                        //Shield cuts enemy damage to half
                        //If player has shield and armour, 75% of damage is negated.
                        if (current.getItem("shield") != null) {
                            Shield shield = (Shield) current.getItem("shield");
                            enemyAD = shield.effect(enemyAD, current.inventory);
                        }
                       
                        //Bow allows player to attack twice
                        if (current.getItem("bow") != null) {
                            Bow bow = (Bow) current.getItem("bow");
                            bow.effect(enemy, enemyHP, playerHP, playerAD, this.inventory);
                        }
                        
                        //Player and Enemy damage each other
                        current.player.setHealth(playerHP - ((enemyHP * enemyAD) / 10));
                        enemy.setHealth(enemyHP - ((playerHP * playerAD) / 5));

                        //Has an ally Mercenary
                        if (this.getPlayer().haveAlly()) {
                            enemy.setHealth(enemyHP - ((playerHP * playerAD) / 5));
                        }

                        if (this.player.isInvincibilityPotionEffect() == true) {
                            battleOver = true;
                        }
                        
                        if (playerHP <= 0) {
                            //one ring
                            if (this.getItem("one_ring") != null) {
                                current.getPlayer().setHealth(10);
                                this.removeItem("one_ring");
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

    public List<Entity> getWalls() {
        List<Entity> walls = new ArrayList<Entity>();       
        for (Entity e : this.entities) {
            if (e instanceof Mercenary){
                Mercenary m = (Mercenary) e;
                if (m.isBribed()) {
                    walls.add(m);
                }
            }
            else if (e instanceof Wall || e instanceof Door || e instanceof MovingEntity || e instanceof Spawner) {
                if (e instanceof Wall || e instanceof MovingEntity || e instanceof Spawner) {
                    walls.add(e);
                } else {
                    if (!(((Door)e).getType().equals("door_unlocked"))) {
                        walls.add(e);
                    }
                }
            }
        }
        return walls;
    }
}
