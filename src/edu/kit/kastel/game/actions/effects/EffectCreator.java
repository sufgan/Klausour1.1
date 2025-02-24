package edu.kit.kastel.game.actions.effects;

public class EffectCreator {
    public static Effect create(String effectName, String effectRegex) {
        return switch (effectName) {
            default -> null;
        };
    }

    private static Effect createDamageEffect(String effectRegex) {

        return null;
    }
}
