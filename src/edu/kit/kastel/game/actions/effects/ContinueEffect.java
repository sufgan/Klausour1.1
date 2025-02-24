package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.ValueType;

public final class ContinueEffect extends ApplyableEffect {
    public ContinueEffect(int effectHitRate) {
        super(effectHitRate, true);
    }

    @Override
    public void apply(Monster user, Monster target) {

    }

    public static Effect createEffect(String regex) {
        return null;
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroups) {
        return RegexConstructor.groupAND(
                nameGroup ? ContinueEffect.class.getSimpleName() : null,
                "continue",
                RegexConstructor.REGEX_SPACE,
                ValueType.RATE.toRegex(nameSubGroups)
        );
    }

}
