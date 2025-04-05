package dev.erpix.tiruka.config;

/**
 * A utility class that provides a static method to transform property names typically found in YAML
 * files into camelCase format, which is commonly used in Java field names.
 */
public final class YamlPropertyToFieldConverter {

    /**
     * Converts an YAML kebab-case property to Java camelCase field.
     *
     * @param input the YAML property to be converted.
     * @return the camelCase equivalent of the input string, or null if input is null.
     */
    public static String convert(String input) {
        if (input == null || input.isEmpty()) return input;

        String[] parts = input.split("-");
        StringBuilder builder = new StringBuilder(parts[0]);

        for (int i = 1; i < parts.length; i++) {
            // Handle cases where there might be consecutive hyphens resulting in empty parts
            if (parts[i].isEmpty()) continue;

            // Convert the first character to uppercase and append the rest of the part
            builder.append(Character.toUpperCase(parts[i].charAt(0)));
            builder.append(parts[i].substring(1));
        }

        return builder.toString();
    }

}
