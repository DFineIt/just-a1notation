package cloud.fineit.just.a1notation;

import cloud.fineit.just.SheetName;

/**
 * Helpers to build A1 notation strings from parsed parts.
 *
 * <p>Utilities cover sheet prefixes, cells, and ranges.
 */
final class Stringifiers {

    private Stringifiers() {}

    /**
     * Returns the sheet name formatted according to A1 quoting rules.
     *
     * @param sheetName sheet name value
     * @return formatted sheet name
     */
    static String sheetNameStr(SheetName sheetName) {
        String name = sheetName.value();
        if (!isQuotingRequired(name)) {
            return name;
        }
        return "'" + name.replace("'", "\\'") + "'";
    }

    /**
     * Returns the sheet prefix including the trailing {@code !}.
     *
     * @param sheet sheet name
     * @return formatted sheet name ending with {@code !}
     */
    static String sheetPrefix(SheetName sheet) {
        return sheetNameStr(sheet) + "!";
    }

    /**
     * Returns a cell reference as A1 notation string.
     *
     * @param column the column of the cell
     * @param row the row of the cell
     * @return cell string like {@code A1}
     */
    static String cellStr(A1Column column, A1Row row) {
        return column.toString() + row.toString();
    }

    private static boolean isQuotingRequired(String name) {
        return name == null || !name.matches("[A-Za-z_][A-Za-z0-9_]*");
    }
}
