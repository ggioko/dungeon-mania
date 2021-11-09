package dungeonmania.entities.Moving;

import dungeonmania.util.Position;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Direction;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Mercenary extends MovingEntity{
    private boolean bribed;
    private boolean inBattle;

    static int ids;

    public Mercenary(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1;
        this.bribed = false;
        this.inBattle = true;
    }
    public static Dungeon spawn(Dungeon currentDungeon, Position entry) {
        Mercenary ass = new Mercenary(new JSONObject("{x:"+ entry.getX() + ",y:"+entry.getY()+",type:mercenary}"));
        ass.setId("mercenary_" + ((Integer)ids).toString());
        List<Entity> ents = currentDungeon.getEntities();
        ents.add(ass);
        currentDungeon.setEntities(ents);
        ids++;
        return currentDungeon;
    }

    @Override
    public void move(Position pos, List<Entity> walls, int width, int height) {
        List<Position> grid = new ArrayList<Position>();
        for (int i = width; i>0-width; i--) {
            for (int c = height;c>0-height; c--) {
                grid.add(new Position(i, c));
            }
        }
        Map<Position, Integer> dist = new HashMap<Position, Integer>();
        Map<Position, Position> prev = new HashMap<Position, Position>();
        Queue<Position> que = new LinkedList<Position>();
        List<Entity> temp = new ArrayList<Entity>();
        for (Entity e : walls) {
            if (!(this.equals(e)) && !(e instanceof Player)) {
                temp.add(e);
            }
        }
        walls = temp;
        for (Position p : grid) {
            boolean wall = false;
            for (Entity e : walls) {
                if (p.equals(e.getPosition())){
                    wall = true;
                }
            }
            if (!wall) {
                que.add(p);
            }
            dist.put(p,999);
            prev.put(p, null);
        }
        dist.put(pos, 0);
        Position u = this.getPosition();
        while (!que.isEmpty()) {
            for (Position v : u.getAdjacentPositionsWalls(walls)){ 
                if (dist.get(v) != null) {
                    if (dist.get(u) + 1 < dist.get(v)) {
                        dist.put(v,dist.get(u)+1);
                        prev.put(v, u);
                    }
                }
            }
            int distance = 999;
            Position holder = u;
            for (Position p : que) {
                if (dist.get(p) < distance) {
                    u = p;
                    distance = dist.get(p);
                }
            }
            que.remove(u);
            if (u.equals(holder)) {
                break;
            }
        }
        if (prev.get(this.getPosition()) != null) {
            this.setPosition(prev.get(this.getPosition()));
        }
        
    }/*
    @Override
    public void move(Position pos, List<Entity> walls) {
        //move mercenary towards position pathfinding algorithm
        int xdiff = pos.getX() - this.getPosition().getX();
        int ydiff = pos.getY() - this.getPosition().getY();

        boolean moved = false;
        if (Math.abs(xdiff) > Math.abs(ydiff)) {
            //x++
            if (pos.getX() - this.getPosition().getX() < 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.LEFT))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.LEFT));
                    moved = true;
                }
            } 
            if (moved == false && pos.getX() - this.getPosition().getX() > 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.RIGHT))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.RIGHT));
                    moved = true;
                }
            }
        } 
        if (moved == false) {
            //x++
            if (pos.getY() - this.getPosition().getY() < 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.UP))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.UP));
                    moved = true;
                }
            } 
            if (moved == false && pos.getY() - this.getPosition().getY() > 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.DOWN))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.DOWN));
                    moved = true;
                }
            }
            if (moved == false) {
                if (pos.getX() - this.getPosition().getX() < 0 ) {
                    boolean move = true;
                    for (Entity w : walls) {
                        if (w.getPosition().equals(this.getPosition().translateBy(Direction.LEFT))) {
                            move = false;
                        }
                    }
                    if (move) {
                        this.setPosition(this.getPosition().translateBy(Direction.LEFT));
                        moved = true;
                    }
                } 
                if (moved == false && pos.getX() - this.getPosition().getX() > 0 ) {
                    boolean move = true;
                    for (Entity w : walls) {
                        if (w.getPosition().equals(this.getPosition().translateBy(Direction.RIGHT))) {
                            move = false;
                        }
                    }
                    if (move) {
                        this.setPosition(this.getPosition().translateBy(Direction.RIGHT));
                        moved = true;
                    }
                }
            }
        } 
    }
    */

    /**
     * Move mercenary away depending on if invincibility potion effects are on or off
     */
    @Override
    public void moveAway(Position pos, List<Entity> walls) {
        //move mercenary away algorithm
        int xdiff = pos.getX() - this.getPosition().getX();
        int ydiff = pos.getY() - this.getPosition().getY();

        boolean moved = false;
        if (Math.abs(xdiff) > Math.abs(ydiff)) {
            //x++
            if (pos.getX() - this.getPosition().getX() > 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.LEFT))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.LEFT));
                    moved = true;
                }
            } 
            if (moved == false && pos.getX() - this.getPosition().getX() < 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.RIGHT))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.RIGHT));
                    moved = true;
                }
            }
        } 
        if (moved == false) {
            //x++
            if (pos.getY() - this.getPosition().getY() > 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.UP))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.UP));
                    moved = true;
                }
            } 
            if (moved == false && pos.getY() - this.getPosition().getY() < 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.DOWN))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.DOWN));
                    moved = true;
                }
            }
            if (moved == false) {
                if (pos.getX() - this.getPosition().getX() > 0 ) {
                    boolean move = true;
                    for (Entity w : walls) {
                        if (w.getPosition().equals(this.getPosition().translateBy(Direction.LEFT))) {
                            move = false;
                        }
                    }
                    if (move) {
                        this.setPosition(this.getPosition().translateBy(Direction.LEFT));
                        moved = true;
                    }
                } 
                if (moved == false && pos.getX() - this.getPosition().getX() < 0 ) {
                    boolean move = true;
                    for (Entity w : walls) {
                        if (w.getPosition().equals(this.getPosition().translateBy(Direction.RIGHT))) {
                            move = false;
                        }
                    }
                    if (move) {
                        this.setPosition(this.getPosition().translateBy(Direction.RIGHT));
                        moved = true;
                    }
                }
            }
        }
        
    }
    
    public boolean isInBribableRange (Position playerPosition) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x-1, y-1));
        adjacentPositions.add(new Position(x  , y-1));
        adjacentPositions.add(new Position(x+1, y-1));
        adjacentPositions.add(new Position(x+1, y));
        adjacentPositions.add(new Position(x+1, y+1));
        adjacentPositions.add(new Position(x  , y+1));
        adjacentPositions.add(new Position(x-1, y+1));
        adjacentPositions.add(new Position(x-1, y));

        adjacentPositions.add(new Position(x-2, y));
        adjacentPositions.add(new Position(x-2, y-2));
        adjacentPositions.add(new Position(x, y-2));
        adjacentPositions.add(new Position(x+2, y-2));
        adjacentPositions.add(new Position(x+2, y));
        adjacentPositions.add(new Position(x+2, y-2));
        adjacentPositions.add(new Position(x, y+2));
        adjacentPositions.add(new Position(x-2, y+2));

        adjacentPositions.add(new Position(x-1, y-2));
        adjacentPositions.add(new Position(x+1, y-2));
        adjacentPositions.add(new Position(x+2, y-1));
        adjacentPositions.add(new Position(x+2, y+1));
        adjacentPositions.add(new Position(x+1, y+2));
        adjacentPositions.add(new Position(x-1, y+2));
        adjacentPositions.add(new Position(x-2, y+1));
        adjacentPositions.add(new Position(x-2, y-1));

        for (Position i : adjacentPositions) {
            if (playerPosition.getX() == i.getX() && playerPosition.getY() == i.getY()) {
                return true;
            }
        }

        return false;
    }
    public boolean isInBattleRadius (Position playerPosition) {
        int x = playerPosition.getX() - this.getPosition().getX();
        int y = playerPosition.getY() - this.getPosition().getY();

        if (x < 0) {
            x = x*(-1);
        }
        if (y < 0) {
            y = y*(-1);
        }

        if (x <= 3 && y <= 3) {
            return true;
        }
        else return false;
    }
    
    public void setBribed (boolean bribed) {
        this.bribed = bribed;
    }

    public boolean isBribed () {
        return this.bribed;
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }
}
