package edu.kit.kastel.game.config;

import edu.kit.kastel.game.Monster;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.actions.effects.Effect;
import edu.kit.kastel.game.actions.effects.Effects;
import edu.kit.kastel.game.elements.Element;
import edu.kit.kastel.game.stats.BaseState;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.ValueType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
    private final String config;

    public static void main(String[] args) {
        System.out.println(Action.getRegex(false, true));
//        var conf = new Config("./config");
//        conf.parse();

    }

    public Config(String path) {
        config = readConfig(path);
    }

    private String readConfig(String path) {
        Path configPath = Path.of(path);
        if (!Files.exists(configPath)) {
            throw new ConfigException("config file does not exist: " + path);
        }
        try {
            String config = Files.readString(Path.of(path));
            if (!Pattern.matches(getRegex(), config)) {
                throw new ConfigException("wrong config pattern");
            }
            return config;
        } catch (IOException e) {
            throw new ConfigException("excepted exception"); // TODO
        }
    }

    public void parse() {
        parseActions();
        parseMonsters();
    }

    private void parseActions() {
        Matcher matcher = Pattern.compile(Action.getRegex(false, true)).matcher(config);
        while (matcher.find()) {
            Action.create(
                    matcher.group("name"),
                    Element.valueOf(matcher.group(Element.class.getSimpleName())),
                    parseEffects(matcher.group(Effects.class.getSimpleName()))
            );
        }
    }

    private List<Effect> parseEffects(String effects) {
        List<Effect> effectList = new LinkedList<>();
        for (String effect : effects.split(RegexConstructor.REGEX_NEW_LINE)) {

        }
//        Matcher matcher = Pattern.compile(Action.getRegex(false, true)).matcher(config);

        return effectList;
    }

    private void parseMonsters() {
        Matcher matcher = Pattern.compile(Monster.getRegex(false, true)).matcher(config);
        while (matcher.find()) {
            String name = matcher.group("name");
            Monster.create(
                    name,
                    Element.valueOf(matcher.group(Element.class.getSimpleName())),
                    Integer.parseInt(matcher.group(ValueType.HEALTH.name())),
                    matcher.group("actions").split(" ")
            );
            BaseState.addBaseState(name, BaseState.ATK, Integer.parseInt(matcher.group(ValueType.ATK.name())));
            BaseState.addBaseState(name, BaseState.DEF, Integer.parseInt(matcher.group(ValueType.DEF.name())));
            BaseState.addBaseState(name, BaseState.SPD, Integer.parseInt(matcher.group(ValueType.SPD.name())));
        }
    }

    public static String getRegex() {
        return RegexConstructor.groupAND(null,
                RegexConstructor.group(null, Action.getRegex(false, false)),
                "*",
                RegexConstructor.group(null, Monster.getRegex(false, false)),
                "*"
        );
    }

}
