package dev.erpix.tiruka.command.console.model.argument;

/**
 * Represents an argument type for parsing {@link String} values.
 */
public class StringArgumentType implements ArgumentType<String> {

    @Override
    public String parse(String input) throws ParsingException {
        if (input == null) throw new ParsingException();
        return input;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

}
