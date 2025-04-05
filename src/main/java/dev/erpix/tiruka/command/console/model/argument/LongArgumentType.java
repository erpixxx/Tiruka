package dev.erpix.tiruka.command.console.model.argument;

/**
 * Represents an argument type for parsing {@link Long} values.
 */
public class LongArgumentType implements ArgumentType<Long> {

    @Override
    public Long parse(String input) throws ParsingException {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new ParsingException();
        }
    }

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

}
