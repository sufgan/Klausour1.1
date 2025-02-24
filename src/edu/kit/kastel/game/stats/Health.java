package edu.kit.kastel.game.stats;

import edu.kit.kastel.game.utils.Utility;

public class Health {
    private final int maxHealth;
    private int currentHealth;

    public Health(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void setHealth(int health) {
        health = Utility.limitValue(0, maxHealth, health);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public String toString() {
        return ""; // TODO
    }
}
