package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.entities.*;
import dungeonmania.entities.Moving.MovingEntity;
import dungeonmania.entities.Static.Spawner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DungeonManiaController {
    Dungeon currentDungeon;
    public DungeonManiaController() {
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
        return null;
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        //should be the same as new game
        return null;
    }

    public List<String> allGames() {
        return new ArrayList<>();
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        //gets the item that is used
        currentDungeon.getItem(itemUsed);
        //enemy pathing
        currentDungeon.pathing(movementDirection);
        currentDungeon = enemyInteraction(currentDungeon);
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

        return currentDungeon.createResponse();
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public Dungeon enemyInteraction(Dungeon current) {
        for (Entity e : current.entities) {
            //for all moving entities aka enemies
            if (e instanceof MovingEntity) {
                MovingEntity enemy = (MovingEntity)e;
                //if the entity is on the same ssquare as character
                if (e.getPosition().equals(current.player.getPosition())) {
                    boolean battleOver = false;
                    while (!battleOver) {
                        //change health values
                        current.player.setHealth(current.player.getHealth() - ((enemy.getHealth() * enemy.getAttack()) / 10));
                        enemy.setHealth(((enemy.getHealth() - current.player.getHealth() * current.player.getAttack()) / 5));
                        
                        if (current.player.getHealth() <= 0) {
                            //game over
                            return null;
                        } else if (enemy.getHealth() <= 0) {
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