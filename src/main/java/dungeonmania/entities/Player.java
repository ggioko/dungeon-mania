package dungeonmania.entities;

import org.json.JSONObject;

public class Player extends Entity {
    
    int health;
    int attack;

    public Player(JSONObject entity) {
        super(entity);
        this.health = 10;
        this.attack = 1;
    }
    
    

}
