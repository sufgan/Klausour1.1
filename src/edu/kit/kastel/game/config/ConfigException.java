package edu.kit.kastel.game.config;

public class ConfigException extends RuntimeException {
    private static final String prefix = "Error: ";

    public ConfigException(String message) {
        super(prefix + message);
    }
}
