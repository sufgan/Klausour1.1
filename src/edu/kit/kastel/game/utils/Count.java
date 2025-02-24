package edu.kit.kastel.game.utils;

public enum Count implements RegexProvider {
    VALUE,
    RANDOM;

    public int getValue(int[] data) {
        return switch (this) {
            case VALUE -> data[0];
            case RANDOM -> RandomGenerator.getRandomNumber(data[0], data[1], "idk");
        };
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return switch (this) {
            case VALUE -> ValueType.VALUE.toRegex(false);
            case RANDOM -> "random\\s+%s".formatted(ValueType.MINMAX.toRegex(false));
        };
    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? Count.class.getSimpleName() : null,
                Count.values()
        );
    }

}
