package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.stats.BaseState;
import edu.kit.kastel.game.stats.ProtectionType;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.ValueType;

public final class StateScaleEffect extends ApplyableEffect {
    private static final String MASSAGE_PROTECTED_FORMAT = "%s is protected and is unaffected!";
    private static final String POSITIVE_SHIFT_MESSAGE_END = "rises!";
    private static final String NEGATIVE_SHIFT_MESSAGE_END = "decreases...";
    private static final String MESSAGE_FORMAT = "%s's %s %s";

    private final BaseState state;
    private final int scaleShift;

    public StateScaleEffect(int effectHitRate, boolean self, BaseState state, int scaleShift) {
        super(effectHitRate, self);
        this.state = state;
        this.scaleShift = scaleShift;
    }

    @Override
    public void apply(Monster user, Monster target) {
        (this.isSelf() ? user : target).shiftStateScale(state, scaleShift);

        System.out.printf(MESSAGE_FORMAT,
                (this.isSelf() ? user : target).getName(),
                state,
                scaleShift < 0 ? NEGATIVE_SHIFT_MESSAGE_END : POSITIVE_SHIFT_MESSAGE_END);
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        if (!this.isSelf() && target.getProtectionType() == ProtectionType.STATS) { // check protection
            System.out.printf(MASSAGE_PROTECTED_FORMAT + "%n",
                    target.getName()
            );
            return false;
        }
        return super.canBeApplied(user, target); // check hit
    }

    public static Effect createEffect(String regex) {
        return null;
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroups) {
        return RegexConstructor.groupAND(
                nameGroup ? StateScaleEffect.class.getSimpleName() : null,
                "inflictStatChange",
                RegexConstructor.REGEX_SPACE,
                Target.getRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                BaseState.getRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                ValueType.CHANGE.toRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                ValueType.RATE.toRegex(nameSubGroups)
        );
    }

}
