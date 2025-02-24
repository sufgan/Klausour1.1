package edu.kit.kastel.game.actions;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.actions.effects.*;
import edu.kit.kastel.game.elements.Element;
import edu.kit.kastel.game.utils.RegexConstructor;

import java.util.*;

public class Action {
    private static final Map<String, Action> actions = new HashMap<>();

    private static final String USING_ACTION_MESSAGE_FORMAT = "%s uses %s action!%n";
    private static final String PASSING_MESSAGE_FORMAT = "%s passes!%n";
    private static final String PASS_ACTION_NAME = "pass";
    private static final String ACTION_FAIL_MESSAGE = "The action failed...";

    private final String name;
    private final Element element;
    private final List<Effect> effects;
    private final Queue<ApplyableEffect> effectsQueue;

    private Action(String name, Element element, List<Effect> effects) {
        this.name = name;
        this.element = element;
        this.effects = effects;
        this.effectsQueue = new LinkedList<>();
    }

    public static void create(String name, Element element, List<Effect> effects) {
        if (actions.containsKey(name)) {
            System.err.println("Action " + name + " already exists!");
        } else {
            Action action = new Action(name, element, effects);
            actions.put(name, action);
        }
    }

    public void apply(Monster user, Monster target) {
        System.out.print(switch (name) {
            case PASS_ACTION_NAME -> PASSING_MESSAGE_FORMAT.formatted(user.getName());
            default -> USING_ACTION_MESSAGE_FORMAT.formatted(user.getName(), name);
        });

        // impact of conditions
        switch (user.getCondition()) {
            case SLEEP -> effectsQueue.clear();
            case BURN -> effectsQueue.add(new BurnDamageEffect());
        }

        // applying effects
        if (!effectsQueue.isEmpty()) {
            boolean first = true;
            for (ApplyableEffect effect = effectsQueue.poll(); !effectsQueue.isEmpty(); effect = effectsQueue.poll()) {
                if (effect.canBeApplied(user, target)) {
                    effect.apply(user, target);
                } else if (first) {
                    System.out.println(ACTION_FAIL_MESSAGE);
                    return;
                }
                first = false;
            }
        }
    }

    public void createEffectsQueue() {
        effectsQueue.clear();
        for (Effect effect : effects) {
            effect.addToQueue(effectsQueue);
        }
    }

    public static Action findAction(String name) {
        return actions.get(name);
    }

    public String getName() {
        return name;
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroup) {
        return RegexConstructor.groupAND(
                nameGroup ? Action.class.getSimpleName() : null,
                "action",
                RegexConstructor.REGEX_SPACE,
                "(?%s\\w+)".formatted(nameSubGroup ? "<name>" : ":"),
                RegexConstructor.REGEX_SPACE,
                Element.getRegex(nameSubGroup),
                RegexConstructor.REGEX_NEW_LINE,
                Effects.getRegex(nameSubGroup, false),
                "end action",
                RegexConstructor.REGEX_MULTI_NEW_LINE
        );
    }

}
