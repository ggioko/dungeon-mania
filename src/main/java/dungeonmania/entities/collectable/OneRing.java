package dungeonmania.entities.collectable;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

public class OneRing extends Entity{
    public OneRing(JSONObject entity) {
        super(entity);
    }

    public OneRing(String id, String type) {
        super(id, type);
    }
}
