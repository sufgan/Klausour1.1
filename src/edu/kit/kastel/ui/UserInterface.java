package edu.kit.kastel.ui;

import java.util.Arrays;
import java.util.Scanner;

public class UserInterface {
    private static final String COMMAND_SEPARATOR = " ";
    private static final String ERROR_UNKNOWN_COMMAND_FORMAT = "Unknown command: %s";
    private boolean isRunning;

    public UserInterface() {

    }

    /**
     * Starts the interaction with the user. This method will block while interacting.
     * The interaction will continue as long as the provided source has more lines to read,
     * or until it is stopped. The provided input source is closed after the interaction is finished.
     *
     * @see Scanner#hasNextLine()
     * @see #stop()
     */
    public void handleUserInput() {
        this.isRunning = true;
//        try () {
//            while (this.isRunning && scanner.hasNextLine()) {
//                handleLine(scanner.nextLine());
//            }
//        }
    }

    private void handleLine(String line) {
        String[] split = line.split(COMMAND_SEPARATOR, -1);
        String command = split[0];
        String[] arguments = Arrays.copyOfRange(split, 1, split.length);

//        if (!findAndHandleCommand(this.viewKeywords, this, command, arguments)
//                && !findAndHandleCommand(this.gameKeywords, this.game, command, arguments)) {
//            this.errorStream.println(ERROR_UNKNOWN_COMMAND_FORMAT.formatted(command));
//        }
    }


    /**
     * Stops this instance from reading further input from the source.
     */
    public void stop() {
        this.isRunning = false;
    }

}
