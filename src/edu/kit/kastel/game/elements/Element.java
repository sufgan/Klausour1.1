package edu.kit.kastel.game.elements;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

public enum Element implements RegexProvider {
    NORMAL(null,    null),
    WATER ("EARTH", "FIRE"),
    FIRE  ("WATER", "EARTH"), // и начал народ огня войну
    EARTH ("FIRE",  "WATER");

    private final String dominant;
    private final String yielding;

    Element(String dominant, String yielding) {
        this.dominant = dominant;
        this.yielding = yielding;
    }

    public ElementEfficiency getEffeciency(Element element) {
        if (element == valueOf(dominant)) {
            System.out.println("It is very effective!");
            return ElementEfficiency.POWERLESS;
        } else if (element == valueOf(yielding)) {
            System.out.println("It is not very effective...");
            return ElementEfficiency.POWERFUL;
        } else {
            return ElementEfficiency.NORMAL;
        }
    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? Element.class.getSimpleName() : null,
                Element.values()
        );
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return name();
    }
}