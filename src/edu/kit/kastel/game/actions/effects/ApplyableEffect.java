package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.game.stats.BaseState;

import java.util.Queue;

public abstract class ApplyableEffect extends Effect {
    private static final String DEBUG_MESSAGE = "apply action effect";

    private final double effectHitRate;
    private final Target target;

    public ApplyableEffect(int effectHitRate, Target target) {
        this.effectHitRate = effectHitRate / 100.0;
        this.target = target;
    }

    public abstract void apply(Monster user, Monster target);

    public boolean canBeApplied(Monster user, Monster target) {
        if (user.isDefeated() || (!isOnUser() && target.isDefeated())) {
            return false;
        }

        double userPRC = BaseState.PRC.getScaledFor(user);
        double targetAGL = isOnUser() ? 1 : BaseState.AGL.getScaledFor(target);
        double conditionQuotient = userPRC / targetAGL;

        return RandomGenerator.probabilityGood(effectHitRate * conditionQuotient, DEBUG_MESSAGE);
    }

    @Override
    public void addToQueue(Queue<ApplyableEffect> queue) {
        queue.add(this);
    }

    public boolean isOnUser() {
        return target == Target.USER;
    }

}
