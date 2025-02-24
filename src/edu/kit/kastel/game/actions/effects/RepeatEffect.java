package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.Count;
import edu.kit.kastel.game.utils.RegexConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class RepeatEffect extends Effect {
    private final List<ApplyableEffect> effects;
    private final int value;

    public RepeatEffect(int value, List<ApplyableEffect> effects) {
        this.value = value;
        this.effects = new LinkedList<>(effects);
    }

    @Override
    public void addToQueue(Queue<ApplyableEffect> queue) {
        for (int i = 0; i < value; i++) {
            queue.addAll(effects);
        }
    }

    public static Effect createEffect(String regex) {
        return null;
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroups) {
        return RegexConstructor.groupAND(
                nameGroup ? RepeatEffect.class.getSimpleName() : null,
                "repeat",
                RegexConstructor.REGEX_SPACE,
                Count.getRegex(nameSubGroups),
                RegexConstructor.REGEX_NEW_LINE,
                Effects.getRegex(nameSubGroups, true),
                RegexConstructor.REGEX_SPACE, // may be unnecessary
                "end repeat"
        );
    }

}
