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
    
    //getters

    public int getHealth() {
        return this.health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return this.attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }

}
