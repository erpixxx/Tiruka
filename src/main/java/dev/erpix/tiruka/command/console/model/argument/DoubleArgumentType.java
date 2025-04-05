package dev.erpix.tiruka.command.console.model.argument;

/**
 * Represents an argument type for parsing {@link Double} values.
 */
public class DoubleArgumentType implements ArgumentType<Double> {

    @Override
    public Double parse(String input) throws ParsingException {
        try {
            return Double.parseDouble(input);
        } catch (NullPointerException | NumberFormatException e) {
            throw new ParsingException();
        }
    }

    @Override
    public Class<Double> getType() {
        return Double.class;
    }

}
