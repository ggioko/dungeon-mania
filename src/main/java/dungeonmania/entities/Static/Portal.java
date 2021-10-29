package dungeonmania.entities.Static;

import org.json.JSONObject;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {

    Position coords;

    public Portal(JSONObject entity, Position coords) {
        super(entity);
        this.coords = coords;
        //TODO Auto-generated constructor stub
    }
    
    public Position getCoords() {
        return this.coords;
    }

}
