package dungeonmania;

import org.json.JSONObject;

public class MovingEntity extends Entity {

    /* from entity
    String id;
    String type;
    Position position;
    boolean isInteractable;
    */

    int health;
    int attack;

    public MovingEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }
    
}
