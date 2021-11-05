package dungeonmania.entities.Static;
import dungeonmania.entities.Entity;

import org.json.JSONObject;

public class Wall extends Entity {

    public Wall(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor 
        this.setInteractable(false);
    }
    
}
