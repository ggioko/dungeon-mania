package dungeonmania.entities.collectable;

import dungeonmania.util.Position;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.Dungeon;

import org.json.JSONObject;
import java.util.List;
import dungeonmania.util.Direction;
import java.lang.Math;
import java.util.ArrayList;

public class Bomb extends CollectableEntity {
    private boolean placed;

    public Bomb(JSONObject entity) {
        super(entity);
        this.placed = false;
        //TODO Auto-generated constructor stub
    }
    
    public Dungeon placeBomb(Dungeon current) {
        JSONObject obj = new JSONObject();
        obj.put("x", current.getPlayer().getPosition().getX());
        obj.put("y", current.getPlayer().getPosition().getY());
        obj.put("type", "bomb");
        Bomb bomb = new Bomb(obj);
        bomb.setId(this.getId());
        bomb.setPlaced(true);
        List<Entity> list = current.getEntities();
        list.add(bomb);
        current.setEntities(list);
        return current;
    }

    public boolean isActivated(Dungeon current) { 
        for (Entity entity1 : current.getEntities()) {
            if (entity1 instanceof FloorSwitch) {
                for (Entity entity2 : current.getEntities()) {
                    if (entity2 instanceof Boulder) {
                        if (entity1.getPosition().equals(entity2.getPosition()) && this.isInRange(entity1.getPosition(), null)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public Dungeon explode(Dungeon current) {
        for (Entity entity : current.getEntities()) {
            if (this.isInRange(entity.getPosition(), "explosion")) {
                current.removeEntity(entity.getId());
            }
        }
        current.removeEntity(this.getId());
        return current;
    }

    public boolean isInRange(Position pos, String mode) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x  , y-1));
        adjacentPositions.add(new Position(x+1, y));
        adjacentPositions.add(new Position(x  , y+1));
        adjacentPositions.add(new Position(x-1, y));

        if (mode.equals("explosion")) {
            adjacentPositions.add(new Position(x-1, y-1));
            adjacentPositions.add(new Position(x+1, y-1));
            adjacentPositions.add(new Position(x+1, y+1));
            adjacentPositions.add(new Position(x-1, y+1));
        }

        for (Position i : adjacentPositions) {
            if (i.getX() == pos.getX() && i.getY() == pos.getY()) return true;
        }

        return false;
    }

    public boolean isPlaced() {
        return this.placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
