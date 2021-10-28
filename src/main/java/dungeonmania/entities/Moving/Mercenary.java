package dungeonmania.entities.Moving;

import dungeonmania.util.Position;

import org.json.JSONObject;

public class Mercenary extends MovingEntity{

    public Mercenary(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }
    
    public boolean isInBribableRange (Position playerPosition) {
        int x = this.getPosition().getX() - playerPosition.getX();
        int y = this.getPosition().getY() - playerPosition.getY();
        return x + y <= 2;
    }
}
