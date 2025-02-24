package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;

import java.util.LinkedList;
import java.util.List;

// TODO вставить куданибудь мб
public class Effects {
    private final List<Effect> effects;

    public Effects(List<Effect> effects) {
        this.effects = new LinkedList<>(effects);
    }

    public static String getRegex(boolean nameGroup, boolean onlyApplyable) {
        return RegexConstructor.group(
                nameGroup ? Effects.class.getSimpleName() : null,
                RegexConstructor.groupAND(null,
                        "(?:",
                        Effect.getRegex(false, false, onlyApplyable ? ApplyableEffect.class : Effect.class),
                        RegexConstructor.REGEX_NEW_LINE,
                        ")+"
                )
        );
    }

}
