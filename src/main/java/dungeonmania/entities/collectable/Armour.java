package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class Armour extends CollectableEntity {

    public Armour(JSONObject entity) {
        super(entity);
        int durability = (int)Math.floor(Math.random()*(15-4+1)+4);
        this.setDurability(durability);
        //TODO Auto-generated constructor stub
    }

    
}
