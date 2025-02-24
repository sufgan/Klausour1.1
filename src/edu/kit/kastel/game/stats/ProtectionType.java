package edu.kit.kastel.game.stats;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

public enum ProtectionType implements RegexProvider {
    HEALTH,
    STATS;

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? ProtectionType.class.getSimpleName() : null,
                ProtectionType.values()
        );
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return this.name().toLowerCase();
    }

}
