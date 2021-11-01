package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class Sword extends CollectableEntity {
    public static int durability = (int)Math.floor(Math.random()*(5-2+1)+2);

    public Sword(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }
    

}
