package edu.kit.kastel;


import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.ui.Reader;
import edu.kit.kastel.ui.UserInterface;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * The class offering the entry point for the application.
 *
 * @author i
 */
public final class Application {
    private static final String ERROR_MESSAGE_COMMAND_LINE_ARGUMENTS = "Wrong arguments count, 1 or 2 line arguments expected.";
    private static final String ERROR_MESSAGE_WRONG_FIRST_ARGUMENT = "Wrong first argument, file path expected.";
    private static final String ERROR_MESSAGE_WRONG_SECOND_ARGUMENT = "Wrong second argument, number or 'debug' expected.";
    private static final String ERROR_MESSAGE_WRONG_PATH = "File does not exist.";

    private Application() {
        // utility class
    }

    /**
     * The entry point for the application. No command line arguments are expected.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.err.println(ERROR_MESSAGE_COMMAND_LINE_ARGUMENTS);
            return;
        }

        if (handleArguments(args)) {
            UserInterface userInterface = new UserInterface();
            userInterface.handleUserInput();
        }

        Reader.close();
    }

    private static boolean handleArguments(String[] args) {
        if (Pattern.matches("^.+\\..+$", args[0])) {
            // do something with config
            Path path = Paths.get(args[0]);
            if (Files.notExists(path)) {
                System.err.println(ERROR_MESSAGE_WRONG_PATH);
                return false;
            }
//            try {
////                Config.parseFile(path);
//            } catch (IOException e) {
//                //                throw new RuntimeException(e);
//            }
        } else {
            System.err.println(ERROR_MESSAGE_WRONG_FIRST_ARGUMENT);
            return false;
        }

        if (args.length == 2) {
            if (Pattern.matches("\\d+", args[1])) {
                RandomGenerator.setSeed(Integer.parseInt(args[1]));
            } else if (Pattern.matches("^debug$", args[1])) {
                RandomGenerator.activateDebugMod();
            } else {
                System.err.println(ERROR_MESSAGE_WRONG_SECOND_ARGUMENT);
                return false;
            }
        }

        return true;
    }
}

