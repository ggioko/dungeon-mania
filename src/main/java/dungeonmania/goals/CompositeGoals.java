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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean add(Goal child) {
        children.add(child);
        return true;
    }

    
}
