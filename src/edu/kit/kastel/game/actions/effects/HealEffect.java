package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.elements.Element;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.ValueType;

public final class HealEffect extends HealthShiftEffect {
    private static final String MESSAGE_FORMAT = "%s gains back %d health!%n";

    public HealEffect(int effectHitRate, boolean self, Element actionElement, Attack attack, double value) {
        super(effectHitRate, self, actionElement, attack, value);
    }

    @Override
    protected String getMessageFormat() {
        return MESSAGE_FORMAT;
    }

    public static Effect createEffect(String regex) {
        return null;
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroups) {
        return RegexConstructor.groupAND(
                nameGroup ? HealEffect.class.getSimpleName() : null,
                "heal",
                RegexConstructor.REGEX_SPACE,
                Target.getRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                Attack.getRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                ValueType.RATE.toRegex(nameSubGroups)
        );
    }

}

