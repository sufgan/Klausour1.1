package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.stats.Condition;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.ValueType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ConditionEffect extends ApplyableEffect {
    private final Condition condition;

    public ConditionEffect(int effectHitRate, Target target, Condition status) {
        super(effectHitRate, target);
        this.condition = status;
    }

    @Override
    public void apply(Monster user, Monster target) {
        (this.isOnUser() ? user : target).setCondition(condition);
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        if ((this.isOnUser() ? user : target).getCondition() == null && super.canBeApplied(user, target)) {
            System.out.printf(condition.getMessage(Condition.CREATING_MESSAGE),
                    (isOnUser() ? user : target).getName()
            );
            return true;
        }
        return false;
    }

    public static Effect parseEffect(String regex) {
        Matcher matcher = Pattern.compile(getRegex(false, true)).matcher(regex);
        matcher.find();
        Target target = Target.valueOf(matcher.group(Target.class.getSimpleName()).toUpperCase());
        Condition condition = Condition.valueOf(matcher.group(Condition.class.getSimpleName()));
        int effectHitRate = Integer.parseInt(matcher.group(ValueType.RATE.name()));
        return new ConditionEffect(effectHitRate, target, condition);
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroups) {
        return RegexConstructor.groupAND(
                nameGroup ? ConditionEffect.class.getSimpleName() : null,
                "inflictStatusCondition",
                RegexConstructor.REGEX_SPACE,
                Target.getRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                Condition.getRegex(nameSubGroups),
                RegexConstructor.REGEX_SPACE,
                ValueType.RATE.toRegex(nameSubGroups)
        );
    }

    public static void main(String[] args) {
        System.out.println(parseEffect("inflictStatusCondition target BURN 90"));
//        System.out.println(getRegex(false, true));
    }

}
