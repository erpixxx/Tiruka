package dev.erpix.tiruka.command.console.model.argument;

/**
 * Represents an argument type for parsing {@link Integer} values.
 */
public class IntegerArgumentType implements ArgumentType<Integer> {

    @Override
    public Integer parse(String input) throws ParsingException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ParsingException();
        }
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

}
