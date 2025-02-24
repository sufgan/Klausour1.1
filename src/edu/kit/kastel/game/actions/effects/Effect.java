package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Effect {
    private static final List<Class<? extends Effect>> regexEffects = List.of(
            RepeatEffect.class, // has to be on first place
            DamageEffect.class,
            ConditionEffect.class,
            StateScaleEffect.class,
            ProtectionEffect.class,
            HealEffect.class,
            ContinueEffect.class
    );

    // TODO make class to create effects and make it non-static
    public static Effect createEffect(String effectName, String effectRegex) {
        return switch (effectName) {
            case "RepeatEffect" -> RepeatEffect.createEffect(effectRegex);
            case "ConditionEffect" -> ConditionEffect.parseEffect(effectRegex);
            case "DamageEffect" -> DamageEffect.createEffect(effectRegex);
            case "StateScaleEffect" -> StateScaleEffect.createEffect(effectRegex);
            case "ProtectionEffect" -> ProtectionEffect.createEffect(effectRegex);
            case "HealEffect" -> HealEffect.createEffect(effectRegex);
            case "ContinueEffect" -> ContinueEffect.createEffect(effectRegex);
            default -> null;
        };
    }

    public abstract void addToQueue(Queue<ApplyableEffect> queue);

    public static String getRegex(boolean nameGroup, boolean nameSubGroups, Class<? extends Effect> superClass) {
        List<String> effectRegexes = new LinkedList<>();
        for (Class<? extends Effect> regexClass : regexEffects) {
            if ((superClass.isAssignableFrom(regexClass))) {
                effectRegexes.add(getRegexForEffect(regexClass.getSimpleName(), nameSubGroups, false));
            }
        }
        return RegexConstructor.groupOR(
                nameGroup ? Effect.class.getSimpleName() : null,
                effectRegexes.toArray(String[]::new)
        );
    }

    private static String getRegexForEffect(String effectName, boolean nameGroup, boolean nameSubGroups) {
        return switch (effectName) {
            case "RepeatEffect" -> RepeatEffect.getRegex(nameGroup, nameSubGroups);
            case "ConditionEffect" -> ConditionEffect.getRegex(nameGroup, nameSubGroups);
            case "DamageEffect" -> DamageEffect.getRegex(nameGroup, nameSubGroups);
            case "StateScaleEffect" -> StateScaleEffect.getRegex(nameGroup, nameSubGroups);
            case "ProtectionEffect" -> ProtectionEffect.getRegex(nameGroup, nameSubGroups);
            case "HealEffect" -> HealEffect.getRegex(nameGroup, nameSubGroups);
            case "ContinueEffect" -> ContinueEffect.getRegex(nameGroup, nameSubGroups);
            default -> "";
        };
    }


        public static void main(String[] args) {
        System.out.println(Effect.getRegex(false, true, Effect.class));
    }

}
