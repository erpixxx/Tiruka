package dev.erpix.tiruka.command.console;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final List<String> commandPath = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();
    private boolean unknownCommand = false;

    public void addToPath(String command) {
        commandPath.add(command);
    }

    public List<String> getCommandPath() {
        return commandPath;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean isUnknownCommand() {
        return unknownCommand;
    }

    public void setUnknownCommand(boolean unknownCommand) {
        this.unknownCommand = unknownCommand;
    }

}
