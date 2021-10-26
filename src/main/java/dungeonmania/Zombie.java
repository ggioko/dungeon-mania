package dungeonmania;

import org.json.JSONObject;

public class Zombie extends MovingEntity {

    public Zombie(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1;
    }
    
}   
