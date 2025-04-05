package dev.erpix.tiruka.command.console.model.argument;

/**
 * Represents an argument type for parsing {@link Boolean} values.
 */
public class BooleanArgumentType implements ArgumentType<Boolean> {

    @Override
    public Boolean parse(String input) throws ParsingException {
        try {
            return Boolean.getBoolean(input);
        } catch (Exception e) {
            throw new ParsingException();
        }
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

}
