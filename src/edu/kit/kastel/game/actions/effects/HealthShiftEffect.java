package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.elements.Element;
import edu.kit.kastel.game.stats.ProtectionType;

public abstract class HealthShiftEffect extends ApplyableEffect {
    private static final String MESSAGE_DEFEAT_FORMAT = "%s faints!%n";
    private static final String MASSAGE_PROTECTED_FORMAT = "%s is protected and takes no damage!%n";
    private final Element actionElement;
    private final Attack attack;
    private final double value;

    public HealthShiftEffect(int effectHitRate, boolean self, Element actionElement, Attack attack, double value) {
        super(effectHitRate, self);
        this.actionElement = actionElement;
        this.attack = attack;
        this.value = value;
    }

    @Override
    public void apply(Monster user, Monster target) {
        int shiftValue = attack.getEffectiveDamage(user, target, actionElement, value);
        (this.isSelf() ? user : target).shiftHealth(shiftValue);

        System.out.printf((getMessageFormat()),
                (this.isSelf() ? user : target).getName(),
                shiftValue
        );

        if ((this.isSelf() ? user : target).isDefeated()) {
            System.out.printf(MESSAGE_DEFEAT_FORMAT,
                    (this.isSelf() ? user : target).getName()
            );
        }
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        if (!this.isSelf() && target.getProtectionType() == ProtectionType.HEALTH) { // check protection
            System.out.printf(MASSAGE_PROTECTED_FORMAT,
                    (this.isSelf() ? user : target).getName()
            );
            return false;
        }
        return super.canBeApplied(user, target); // check hit
    }

    protected abstract String getMessageFormat();

}