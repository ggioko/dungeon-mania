package dungeonmania;

import org.json.JSONObject;

public class Spider extends MovingEntity {

    public Spider(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1;
    }
    
}   
