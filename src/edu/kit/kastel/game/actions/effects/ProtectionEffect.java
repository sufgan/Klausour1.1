package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.stats.ProtectionType;
import edu.kit.kastel.game.utils.Count;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.ValueType;

public final class ProtectionEffect extends ApplyableEffect {
    private final ProtectionType protectionType;
    private final int duration;

    public ProtectionEffect(int effectHitRate, ProtectionType protectionType, int duration) {
        super(effectHitRate, Target.USER);
        this.protectionType = protectionType;
        this.duration = duration;
    }

    // TODO: изменить на защиту только самого себя
    @Override
    public void apply(Monster user, Monster target) {
        (isSelf() ? user : target).setProtection(protectionType, duration);

        System.out.printf("%s is now protected against %s!%n",
                (isSelf() ? user : target).getName(),
                protectionType == ProtectionType.HEALTH ? "damage" : "status changes");
    }

    public static Effect createEffect(String regex) {
        return null;
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroups) {
        return RegexConstructor.groupAND(
                nameGroup ? ProtectionEffect.class.getSimpleName() : null,
                "protectStat",
                RegexConstructor.REGEX_SPACE,
                ProtectionType.getRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                Count.getRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                ValueType.RATE.toRegex(nameSubGroups)
        );
    }

}
