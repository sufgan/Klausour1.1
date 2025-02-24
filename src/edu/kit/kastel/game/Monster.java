package edu.kit.kastel.game;

import edu.kit.kastel.game.config.Config;
import edu.kit.kastel.game.elements.Element;
import edu.kit.kastel.game.stats.BaseState;
import edu.kit.kastel.game.stats.Condition;
import edu.kit.kastel.game.stats.Health;
import edu.kit.kastel.game.stats.ProtectionType;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.Utility;
import edu.kit.kastel.game.utils.ValueType;

import java.util.*;

public class Monster {
    private static final Map<String, Monster> monsters = new HashMap<>();

    private final String name;
    private final Element element;
    private final Set<String> actions; // with max length 4 or 5 with pass
    private final Map<BaseState, Integer> stateScales;

    private final Health health;
    private Condition condition;
    private Protection protection;

//    public Monster(String regex) {
//        regex.
//    }

    private Monster(String name, Element element, int maxHealth, Set<String> actions) {
        this.name = name;
        this.element = element;
        this.health = new Health(maxHealth);
        this.actions = new HashSet<>(actions);
        this.stateScales = new HashMap<>();
        monsters.put(name, this);
        refreshState();
    }

    public static void create(String name, Element element, int maxHealth, String[] actions) {
        if (monsters.containsKey(name)) {
            System.err.println("Action " + name + " already exists!");
        } else {
            Monster monster = new Monster(name, element, maxHealth, Set.of(actions));
            monsters.put(name, monster);
        }
    }

    public void refreshState() {
        stateScales.clear();
        health.setHealth(Integer.MAX_VALUE); // will be later rounded
    }

    public boolean hasAction(String actionName) {
        return actionName.equals("pass") || actions.contains(actionName);
    }

    public void shiftStateScale(BaseState state, int shift) {
        stateScales.put(state, Utility.limitValue(getStateScale(state) + shift, -5, 5));
    }

    public void shiftHealth(int shift) {
        health.setHealth(health.getCurrentHealth() + shift);
    }

    public boolean isDefeated() {
        return health.getCurrentHealth() == 0;
    }

    public String getName() {
        return name;
    }

    public Element getElement() {
        return element;
    }

    public int getStateScale(BaseState state) {
        return stateScales.getOrDefault(state, 0);
    }

    public Health getHealth() {
        return health;
    }

    public void updateCondition() {
        if (Condition.isEnded()) {
            System.out.printf(this.condition.getMessage(Condition.FINISHING_MESSAGE), name);
            this.condition = null;
        } else {
            System.out.printf(this.condition.getMessage(Condition.EXISTING_MESSAGE), name);
        }
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setProtection(ProtectionType type, int duration) {
        this.protection = new Protection(type, duration);
    }

    public ProtectionType getProtectionType() {
        return protection != null ? protection.getType() : null;
    }

    public static Monster findMonster(String name) {
        return monsters.get(name);
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroup) {
        return RegexConstructor.groupAND(
                nameGroup ? Monster.class.getSimpleName() : null,
                "monster",
                RegexConstructor.REGEX_SPACE,
                "(?%s\\w+)".formatted(nameSubGroup ? "<name>" : ":"), // name
                RegexConstructor.REGEX_SPACE,
                Element.getRegex(nameSubGroup),
                RegexConstructor.REGEX_SPACE,
                ValueType.HEALTH.toRegex(nameSubGroup),
                RegexConstructor.REGEX_SPACE,
                ValueType.ATK.toRegex(nameSubGroup),
                RegexConstructor.REGEX_SPACE,
                ValueType.DEF.toRegex(nameSubGroup),
                RegexConstructor.REGEX_SPACE,
                ValueType.SPD.toRegex(nameSubGroup),
                RegexConstructor.REGEX_SPACE,
                "(?%s\\w+(?:%s\\w+){0,3})".formatted( // action names
                        nameSubGroup ? "<actions>" : ":",
                        RegexConstructor.REGEX_SPACE
                ),
                RegexConstructor.REGEX_MULTI_NEW_LINE
        );
    }

    @Override
    public String toString() {
        return String.format("%s: Element %s, HP %d, ATK %d, DEF %d, SPD %d",
                name,
                element,
                health.getCurrentHealth(),
                BaseState.ATK.getFor(this),
                BaseState.DEF.getFor(this),
                BaseState.SPD.getFor(this));
    }


    private static class Protection {
        private final ProtectionType type;
        private int duration;

        public Protection(ProtectionType type, int duration) {
            this.type = type;
            this.duration = duration;
        }

        public void decreaseDuration() {
            duration--;
        }

        public ProtectionType getType() {
            return type;
        }

        public boolean isFinished() {
            return duration == 0;
        }

    }

}


