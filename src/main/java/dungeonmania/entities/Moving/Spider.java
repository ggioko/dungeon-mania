package dungeonmania.entities.Moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Spider extends MovingEntity {
    
    boolean spawned;
    int pathnum;
    static int ids = 0;
    List<Direction> directions = new ArrayList<Direction>(
        Arrays.asList(Direction.LEFT, Direction.DOWN, Direction.DOWN, Direction.RIGHT, Direction.RIGHT, Direction.UP, Direction.UP, Direction.LEFT)
    );
    

    public Spider(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1; 
        this.spawned = true;
        this.pathnum = 0;
        //TODO Auto-generated constructor stub
    }

    @Override
    public void move(Position pos, List<Entity> walls) {
        if (this.spawned) {
            this.setPosition(this.getPosition().translateBy(Direction.UP));
            this.spawned = false;
            return;
        }
        //move in circle
        this.setPosition(this.getPosition().translateBy(directions.get(pathnum)));
        this.pathnum++;
        if (this.pathnum == 8) {
            this.pathnum = 0;
        }
    }

    static public Dungeon spawn(Dungeon current) {
        JSONObject obj = new JSONObject();
        obj.put("x", Math.random()*10);
        obj.put("y", Math.random()*10);
        obj.put("type", "spider");
        Spider spider = new Spider(obj);
        spider.setId("spider_" + ((Integer)ids).toString());
        List<Entity> list = current.getEntities();
        list.add(spider);
        current.setEntities(list);
        ids++;
        return current;
    }

}
