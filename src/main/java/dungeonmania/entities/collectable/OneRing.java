package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class OneRing extends CollectableEntity{
    public OneRing(JSONObject entity) {
        super(entity);
    }

    public OneRing(String id, String type) {
        super(id, type);
    }
}
