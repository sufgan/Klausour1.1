package edu.kit.kastel.ui;

import edu.kit.kastel.game.actions.Action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Reader {
    private static final InputStream DEFAULT_INPUT_STREAM = System.in;
    private static final Scanner scanner = new Scanner(DEFAULT_INPUT_STREAM);

    public static boolean readBoolean(String message) {
        System.out.println(message);
        scanner.nextBoolean();
        String answer = scanner.nextLine();
        if (answer.length() == 1) {
            switch (answer.charAt(0)) {
                case 'y': return true;
                case 'n': return false;
            }
        }
        System.out.println("Error, enter y or n.");
        return readBoolean(message);
    }

    public static double readDouble(String message) {
        System.out.println(message);
        String answer = scanner.nextLine();
        if (Pattern.matches("\\d+\\.\\d+", answer)) {
            return Double.parseDouble(answer);
        }
        System.out.println("Error, wrong value.");
        return readDouble(message);
    }

    public static String readActionName(String message) {
        System.out.println(message);
        String actionName = scanner.nextLine();
        if (Action.findAction(actionName) != null) {
            return actionName;
        }
        System.err.println("Wrong action name.");
        return readActionName(message);
    }

    public static int readInteger(String message) {
        System.out.println(message);
        String answer = scanner.nextLine();
        if (Pattern.matches("\\d+", answer)) {
            return Integer.parseInt(answer);
        }
        System.err.println("Error, wrong value.");
        return readInteger(message);
    }

    public static void close() {
        scanner.close();
    }

}
