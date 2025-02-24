package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.Monster;

public final class BurnDamageEffect extends HealthShiftEffect {
    private static final String MESSAGE_FORMAT = "%s takes %d damage from burning!%n";

    public BurnDamageEffect() {
        super(0, true, null, Attack.REL, -0.1);
    }

    @Override
    protected String getMessageFormat() {
        return MESSAGE_FORMAT;
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        return true; // is always
    }
}
