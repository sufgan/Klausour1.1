package edu.kit.kastel.game;

import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.stats.BaseState;
import edu.kit.kastel.ui.Reader;

import java.util.*;

public class Competition implements Comparator<Monster> {
    private static final String MONSTER_DONT_HAVE_ACTION_MESSAGE_FORMAT = "%s doesn't have this action%n";

    private final List<Monster> monsters;

    public Competition(List<Monster> monsters) {
        // TODO: create names like #1, #2, ... for repeating monsters
        this.monsters = new LinkedList<>(monsters);
        start();
    }

    public void start() {
        while (!isEnd()) {
            SortedMap<Monster, Action> actions = selectActions();
            for (Monster monster : actions.keySet()) {
                if (!monster.isDefeated()) {
                    Action action = actions.get(monster);
                    action.createEffectsQueue();
                    monster.updateCondition();
                    action.apply(monster, null); // TODO: replace target on something
                }
            }
        }
    }
    //

    private boolean isEnd() {
        int count = 0;
        for (Monster monster : monsters) {
            if (!monster.isDefeated() && count++ > 1) {
                return false;
            }
        }
        return true;
    }

    private SortedMap<Monster, Action> selectActions() {
        SortedMap<Monster, Action> actions = new TreeMap<>(this);
        for (Monster monster : monsters) {
            if (!monster.isDefeated()) {
                actions.put(monster, chooseActionForMonster(monster));
            }
        }
        return actions;
    }

    private Action chooseActionForMonster(Monster monster) {
        String actionName = Reader.readActionName("Choose monster action");
        if (monster.hasAction(actionName)) {
            return Action.findAction(actionName);
        }
        System.err.printf(MONSTER_DONT_HAVE_ACTION_MESSAGE_FORMAT, monster.getName());
        return chooseActionForMonster(monster);
    }

    @Override
    public int compare(Monster o1, Monster o2) {
        return Double.compare(BaseState.SPD.getScaledFor(o1), BaseState.SPD.getScaledFor(o2));
    }

}
