package dev.erpix.tiruka.console;

import dev.erpix.tiruka.command.CommandRegistrar;
import dev.erpix.tiruka.command.console.CommandResult;
import dev.erpix.tiruka.command.console.model.ArgumentCommandNode;
import dev.erpix.tiruka.command.console.model.CommandNode;
import dev.erpix.tiruka.command.console.model.KeywordCommandNode;
import dev.erpix.tiruka.command.console.model.argument.ParsingException;
import dev.erpix.tiruka.command.console.model.builder.CommandContextBuilder;

import java.util.*;

/**
 * Processes console commands and executes them if they are valid.
 */
public class ConsoleCommandProcessor {

    /**
     * <p>Map of all available commands containing their names and aliases</p>
     * <p>To get all available commands without aliases, use {@link CommandRegistrar#getConsoleCommands()}</p>
     */
    private final Map<String, CommandNode> commands = new LinkedHashMap<>();

    /**
     * <p>Processes the input string and executes the command if it is valid.</p>
     *
     * @param input The input string to process.
     * @return The result of the command processing.
     */
    public CommandResult process(String input) {
        CommandResult result = new CommandResult();

        CommandContextBuilder builder = new CommandContextBuilder();
        input = input.trim();

        CommandNode currentNode = null;
        int lastSpace = 0;
        char expectedChar = ' ';
        for (int i = 0; i <= input.length(); i++) {
            if (i == input.length() || input.charAt(i) == expectedChar) {
                String token = input.substring(lastSpace, i).trim();
                lastSpace = i + 1;

                if (token.isEmpty()) continue;

                result.addToPath(token);

                // Root node
                if (currentNode == null) {
                    currentNode = commands.get(token);
                    if (currentNode == null) {
                        result.setUnknownCommand(true);
                        return result;
                    } // Handle unknown command
                }
                else {
                    CommandNode child = currentNode.getChild(token);
                    // Child is a keyword
                    if (child != null) {
                        currentNode = child;
                    }
                    // Child is an argument
                    else {
                        boolean argumentParsed = false;
                        for (ArgumentCommandNode<?> arg : currentNode.getArguments()) {
                            try {
                                Object parsed = arg.parse(token);
                                if (parsed != null) {
                                    builder.addArgument(arg.getName(), parsed);
                                    currentNode = arg;
                                    argumentParsed = true;
                                    break;
                                }
                            } catch (ParsingException e) {
                                result.addError("Invalid argument: " + token);
                                return result;
                            }
                        }

                        if (!argumentParsed && !result.hasErrors()) {
                            result.addError("Invalid argument: " + token);
                            result.addError("Expected: " + getExpectedOptions(currentNode));
                            return result;
                        }
                    }
                }
            }
        }

        if (currentNode != null) {
            if (currentNode.isExecutable()) {
                currentNode.execute(builder.build());
                return result;
            }
            else {
                result.addError("Incomplete command");
                result.addError(String.join(" ", result.getCommandPath()));
                result.addError("Expected: " + getExpectedOptions(currentNode));
                return result;
            }
        }

        return result;
    }

    private String getExpectedOptions(CommandNode node) {
        List<String> options = new ArrayList<>();
        node.getKeywords().forEach(c -> options.add(c.getName()));
        node.getArguments().forEach(a -> options.add("<" + a.getName() + ": " + a.getType().getType().getSimpleName() + ">"));
        return options.isEmpty() ? "no available options" : String.join(", ", options);
    }

    /**
     * Registers a command to the processor.
     *
     * @param command The command to register.
     */
    public void register(CommandNode command) {
        commands.put(command.getName(), command);
        ((KeywordCommandNode) command).getAliases().forEach(alias -> commands.put(alias, command));
    }

    /**
     * Gets all registered commands.
     *
     * @return A collection of all registered commands.
     */
    public Collection<CommandNode> getCommands() {
        return commands.values();
    }

}
