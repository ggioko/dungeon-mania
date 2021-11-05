package dungeonmania.goals;

import java.util.ArrayList;

public class CompositeGoals implements Goal {

    private String name;
    private boolean complete;

    ArrayList<Goal>  children = new ArrayList<Goal>();

    public CompositeGoals(String name, boolean complete) {
        this.name = name;
        this.complete = complete;
    }

    @Override
    public String goalsString() {
        String answer = null;
        if (children.size() == 1) {
            return children.get(0).goalsString();
        } else if (children.get(0) instanceof GoalLeaf && children.get(1) instanceof GoalLeaf) {
            answer = "(:" + children.get(0).goalsString() + " " + this.getName() + " :" + children.get(1).goalsString() + ")";

        } else {
            if (children.get(1) instanceof CompositeGoals && children.get(0) instanceof CompositeGoals) {
                answer = "(" + children.get(0).goalsString() + " " + this.getName() + " " + children.get(1).goalsString() + ")";
            }else if (children.get(1) instanceof CompositeGoals) {
                answer = "(:" + children.get(0).goalsString() + " " + this.getName() + " " + children.get(1).goalsString() + ")";
                System.out.println(answer);
            } else if (children.get(0) instanceof CompositeGoals) {
                answer = "(" + children.get(0).goalsString() + " " + this.getName() + " :" + children.get(1).goalsString() + ")";
            }
        }
        return answer;
    }

    private String getName() {
        return name;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean add(Goal child) {
        children.add(child);
        return true;
    }

    public void checkComplete() {
        if (this.name.equals("AND")) {
            if (children.get(0).isComplete() && children.get(1).isComplete()) {
                this.complete = true;
            } else {
                this.complete = false;
            }
        } else if (this.name.equals("OR")) {
            if (children.size() == 1) {
                if (children.get(0).isComplete()) {
                    this.complete = true;
                } else {
                    this.complete = false;
                }
            } else {
                if (children.get(0).isComplete() || children.get(1).isComplete()) {
                    this.complete = true;
                } else {
                    this.complete = false;
                }
            }
        }
    }

    public void checkGoalState() {
        
    }
    
}
