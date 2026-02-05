package cloud.fineit.just;

import java.util.Optional;

/**
 * SheetName holds a sheet identifier parsed from unquoted or quoted A1 notation.
 *
 * <p>For quoted input, outer quotes are removed and inner quotes escaped with a backslash
 * are unescaped.
 */
public final class SheetName {

    private final String value;

    /**
     * Creates a sheet name from a plain value (without surrounding quotes).
     *
     * @param value sheet name
     * @throws IllegalArgumentException if value is null or empty
     */
    public SheetName(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Sheet name must not be empty");
        }
        this.value = value;
    }

    /**
     * Parses a possibly quoted sheet part to a {@code SheetName}.
     *
     * @param sheetPart entire sheet part, quoted or plain
     * @return parsed sheet name
     */
    public static SheetName parse(String sheetPart) {
        if (sheetPart == null || sheetPart.isEmpty()) {
            throw new IllegalArgumentException("Sheet name must not be empty");
        }
        if (sheetPart.length() >= 2 && sheetPart.charAt(0) == '\'' &&
                sheetPart.charAt(sheetPart.length() - 1) == '\'') {
            String inner = sheetPart.substring(1, sheetPart.length() - 1);
            // Unescape backslash-escaped single quotes
            inner = inner.replace("\\'", "'");
            return new SheetName(inner);
        }
        return new SheetName(sheetPart);
    }

    /**
     * Extracts an optional {@code SheetName} from a full A1 string.
     *
     * <p>If the input contains a {@code '!'} separator, the part before it is parsed as a sheet
     * name. If no separator is present, the result is {@code Optional.empty()}.
     *
     * @param notation A1 notation string
     * @return sheet name if any, {@code Optional.empty} otherwise.
     */
    public static Optional<SheetName> fromNotation(String notation) {
        int bang = notation.indexOf('!');
        if (bang >= 0) {
            String sheetPart = notation.substring(0, bang);
            return Optional.of(parse(sheetPart));
        }
        return Optional.empty();
    }

    /**
     * Returns the part of an A1 string after the optional sheet separator.
     *
     * <p>If the input contains {@code '!'}, the substring following it is returned.
     * If no separator is present, the original string is returned unchanged.
     *
     * @param notation full A1 string
     * @return notation part without the sheet prefix
     */
    public static String refPart(String notation) {
        int bang = notation.indexOf('!');
        return bang >= 0 ? notation.substring(bang + 1) : notation;
    }

    /**
     * Returns the plain sheet name (without quotes).
     *
     * @return sheet name value
     */
    public String value() {
        return value;
    }
}
