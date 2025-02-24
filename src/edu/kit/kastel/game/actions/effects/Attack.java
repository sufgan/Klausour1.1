package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexProvider;
import edu.kit.kastel.game.utils.ValueType;
import edu.kit.kastel.game.utils.Utility;
import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.elements.Element;
import edu.kit.kastel.game.utils.RegexConstructor;

public enum Attack implements RegexProvider {
    ABS,
    REL,
    BASE;

    public int getEffectiveDamage(Monster user, Monster target, Element actionElement, double value) {
        return switch (this) {
            case ABS -> (int) value;
            case REL -> (int) Math.round(target.getHealth().getMaxHealth() * value);
            case BASE -> Utility.findBasicDamage(user, target, actionElement, value);
        };
    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? Attack.class.getSimpleName() : null,
                Attack.values()
        );
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(
                null,
                name().toLowerCase(),
                RegexConstructor.REGEX_SPACE,
                switch (this) {
                    case ABS, BASE -> ValueType.VALUE.toRegex(false);
                    case REL -> ValueType.PERCENTAGE.toRegex(false);
                }
        );
    }
}
