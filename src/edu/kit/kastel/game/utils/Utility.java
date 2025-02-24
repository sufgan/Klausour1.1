package edu.kit.kastel.game.utils;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.elements.Element;
import edu.kit.kastel.game.stats.BaseState;

public final class Utility {
    private static final String CRITICAl_HIT_MESSAGE = "Critical hit!%n";
    private static final String CRITICAl_HIT_DEBUG_MESSAGE = "critical hit";
    private static final String RANDOM_FACTOR_DEBUG_MESSAGE = "random factor";

    private Utility() {

    }

    public static int findBasicDamage(Monster user, Monster target, Element actionElement, double value) {
        double elementFactor = actionElement.getEffeciency(target.getElement()).getDamageScale();
        double statusFactor = BaseState.ATK.getScaledFor(user) / BaseState.DEF.getScaledFor(target);
        double criticalHitProbability = Math.pow(10, -BaseState.SPD.getScaledFor(target) / BaseState.SPD.getScaledFor(user)) * 100;
        int criticalHitFactor;
        if (RandomGenerator.probabilityGood(criticalHitProbability, CRITICAl_HIT_DEBUG_MESSAGE)) {
            System.out.println(CRITICAl_HIT_MESSAGE);
            criticalHitFactor = 2;
        } else {
            criticalHitFactor = 1;
        }
        double sameElementFactor = user.getElement() == actionElement ? 1.5 : 1;
        double randomFactor = RandomGenerator.getRandomFactor(0.85, 1, RANDOM_FACTOR_DEBUG_MESSAGE);
        double normalFactor = 1 / 3.;
        return (int) Math.ceil(value * elementFactor * statusFactor * criticalHitFactor * sameElementFactor * randomFactor * normalFactor);
    }

    public static int limitValue(int val, int low, int top) {
        return Math.max(low, Math.min(val, top));
    }
}
