package dungeonmania.entities.Moving;

import org.json.JSONObject;

import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Zombie extends MovingEntity {

    public Zombie(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    public void move(Position pos, List<Wall> walls) {
        boolean move = true;
        List<Direction> directions = new ArrayList<Direction>();
        directions.add(Direction.UP);
        directions.add(Direction.LEFT);
        directions.add(Direction.DOWN);
        directions.add(Direction.RIGHT);

        Random rand = new Random();

        pos = this.getPosition().translateBy(directions.get(rand.nextInt(directions.size())));
        for (Wall w : walls) {
            if (w.getPosition().equals(pos)) {
                move = false;
            }
        }
        if (move) {
            this.setPosition(pos);
        }
    }

}  
