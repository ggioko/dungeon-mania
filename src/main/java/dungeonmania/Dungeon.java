package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;

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
    boolean complete;


    public Dungeon(String dungeonName, JSONObject entities) {
        this.dungeonName = dungeonName;
        this.dungeonId = dungeonName;
        this.entities = new ArrayList<Entity>();
        for (Object entity : entities.getJSONArray("entities")) {
            
            if (((JSONObject)entity).getString("type").equals("player")) {
                this.player = new Player((JSONObject)entity);
                this.entities.add(this.player);
            } else {
                this.entities.add(new Entity((JSONObject)entity));
            }
        }
        this.inventory = new ArrayList<Item>();
        this.buildables = new ArrayList<String>();
        this.goals = "";
        this.complete = false;


    }

    public Item getItem(String type) {
        for (Item i : this.inventory) {
            if (i.type.equals(type)) {
                return i;
            }
        }
        return null;
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

}
