package dungeonmania.entities.Static;

import org.json.JSONObject;

public class Wall extends StaticEntity {

    public Wall(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor 
        this.setInteractable(false);
    }
    
}
