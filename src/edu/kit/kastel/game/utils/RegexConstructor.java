package edu.kit.kastel.game.utils;

public final class RegexConstructor {
    public static final String REGEX_MULTI_SPACE = "\\s+";
    public static final String REGEX_SPACE = "\\s";
//    public static final String EMPTY_SEPARATOR = "";
    public static final String REGEX_OR = "|";
    public static final String REGEX_MULTI_NEW_LINE = "\\n+\\s*";
    public static final String REGEX_NEW_LINE = "\\n\\s*";

    private RegexConstructor() {

    }

//    public static <E extends Enum<E>> String construct(Class<E> enumClass, boolean nameGroups) {
//        List<String> processedValues = new LinkedList<>();
//        for (E val : enumClass.getEnumConstants()) {
//            processedValues.add(val instanceof RegexProvider ? ((RegexProvider) val).toRegex(false) : val.toString());
//        }
//        return "(?%s%s)".formatted(
//                nameGroups ? "<%s>".formatted(enumClass.getSimpleName()) : ":",
//                String.join("|", processedValues)
//        );
//    }

    // TODO rewrite few
    public static String groupAND(String groupName, String... regexElements) {
        if (groupName == null) {
            return String.join("", regexElements);
        }
        return group(groupName, String.join("", regexElements));
    }

    public static String groupOR(String groupName, String... regexElements) {
        return group(groupName, String.join(REGEX_OR, regexElements));
    }

    public static <T extends RegexProvider> String groupOR(String groupName, T... regexElements) {
        String[] processed = new String[regexElements.length];
        for (int i = 0; i < regexElements.length; i++) {
            processed[i] = regexElements[i].toRegex(false);
        }
        return groupOR(groupName, processed);
    }

    public static String group(String groupName, String regex) {
        return "(?%s%s)".formatted(
                groupName != null ? "<%s>".formatted(groupName) : ":",
                regex
        );
    }

        // TODO: add function to get result of regex based on class name

}
