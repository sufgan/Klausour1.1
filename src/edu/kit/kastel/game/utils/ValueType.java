package edu.kit.kastel.game.utils;

import javax.xml.namespace.QName;

public enum ValueType implements RegexProvider {
    VALUE,
    MINMAX,
    MIN,
    MAX,
    HEALTH,
//    STATS,
    ATK,
    DEF,
    SPD,
    RATE,
    PERCENTAGE,
    CHANGE;

    // TODO
    @Override
    public String toRegex(boolean nameGroup) {
        String regex = switch (this) {
            case VALUE, MIN, MAX -> RegexConstructor.groupOR( // [0, inf)
                    null,
                    "0",
                    "[1-9]\\d*"
            ) ;
            case HEALTH, ATK, DEF, SPD -> "[1-9]\\d*"; // [1, inf)
//            case STATS -> RegexConstructor.groupAND(
//                    null,
//                    ATK.toRegex(false),
//                    RegexConstructor.REGEX_SPACE,
//                    DEF.toRegex(false),
//                    RegexConstructor.REGEX_SPACE,
//                    SPD.toRegex(false)
//            );
            case RATE, PERCENTAGE -> RegexConstructor.groupOR( // [0, 100]
                    null,
                    "0", "100", "[1-9]\\d?"
            );
            case CHANGE -> "[+-]\\d+"; // TODO: check working props
            case MINMAX -> RegexConstructor.groupAND(
                    null,
                    MIN.toRegex(false),
                    RegexConstructor.REGEX_SPACE,
                    MAX.toRegex(false)
            );
        };
        if (nameGroup) {
            return RegexConstructor.group(this.name(), regex);
        }
        return regex;
    }

}
