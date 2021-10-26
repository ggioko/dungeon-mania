package dungeonmania;

import org.json.JSONObject;

public class Mercenary extends MovingEntity {

    public Mercenary(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1;
    }
    
}   
