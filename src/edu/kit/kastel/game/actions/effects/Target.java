package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

public enum Target implements RegexProvider {
    TARGET,
    USER;

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? Target.class.getSimpleName() : null,
                Target.values()
        );
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return this.name().toLowerCase();
    }

}
