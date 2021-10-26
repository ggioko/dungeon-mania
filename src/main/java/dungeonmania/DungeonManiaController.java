package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

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
        Dungeon newDungeon = new Dungeon(dungeonName, obj);
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
        currentDungeon.player.position = currentDungeon.player.position.translateBy(movementDirection);
        //gets the item that is used
        currentDungeon.getItem(itemUsed);
        currentDungeon = enemyInteraction(currentDungeon);
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
                if (e.position.equals(current.player.position)) {
                    //change health values
                    current.player.health -= ((enemy.health * enemy.attack) / 10);
                    enemy.health -= ((current.player.health * current.player.attack) / 5);
                    
                    if (current.player.health <= 0) {
                        //game over
                        return null;
                    }
                    if (enemy.health <= 0) {
                        //enemy is dead
                        current.enemyDeath(enemy);
                        return current;
                    }
                }
            }
        }
        return current;
    }
}