package cloud.fineit.just.a1notation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helpers related to A1 references.
 *
 * <p>Includes split helpers (column/row extraction) and kind recognizers
 * such as cell, range, whole-column, and whole-row checks.
 */
final class References {

    private static final Pattern FIRST_DIGIT = Pattern.compile("\\d");
    private static final Pattern CELL = Pattern.compile("[A-Z]+[1-9][0-9]*");
    private static final Pattern RANGE = Pattern.compile("[A-Z]+[1-9][0-9]*:[A-Z]+[1-9][0-9]*");
    private static final Pattern WHOLE_COL_RANGE = Pattern.compile("[A-Z]+:[A-Z]+");
    private static final Pattern WHOLE_ROW_RANGE = Pattern.compile("[1-9][0-9]*:[1-9][0-9]*");
    private static final Pattern QUOTED_SHEET = Pattern.compile("'(?:[^'\\\\]|\\\\')+'");
    private static final Pattern COLUMN_ONLY = Pattern.compile("[A-Za-z]+");
    private static final Pattern ROW_ONLY = Pattern.compile("[1-9][0-9]*");
    private static final Pattern UNQUOTED_SHEET = Pattern.compile("[A-Za-z_][A-Za-z0-9_ ]*");

    private References() {
    }

    /**
     * Returns the column of a cell reference.
     *
     * <p>The boundary is detected at the first digit in the reference.
     *
     * @param ref a1 reference without sheet part
     * @return column value
     * @throws IllegalArgumentException if the reference does not contain any digits
     */
    static A1Column extractColumn(String ref) {
        int boundary = firstDigitIndex(ref);
        String letters = ref.substring(0, boundary);
        return new A1Column(letters);
    }

    /**
     * Returns the row of a cell reference.
     *
     * <p>The boundary is detected at the first digit in the reference.
     *
     * @param ref a1 reference without sheet part
     * @return row value
     * @throws IllegalArgumentException if the reference does not contain any digits
     */
    static A1Row extractRow(String ref) {
        int boundary = firstDigitIndex(ref);
        String digits = ref.substring(boundary);
        return new A1Row(Integer.parseInt(digits));
    }

    /**
     * Finds the index of the first digit within a reference.
     *
     * <p>This index separates the column letters from the row digits.
     *
     * @param ref a1 reference without sheet part
     * @return index of the first digit
     * @throws IllegalArgumentException if no digits found in the reference
     */
    private static int firstDigitIndex(String ref) {
        Matcher matcher = FIRST_DIGIT.matcher(ref);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid A1 cell reference: " + ref);
        }
        return matcher.start();
    }

    /** Returns true when string matches a single A1 cell reference like A1 or BC42. */
    static boolean isCell(String str) {
        return CELL.matcher(str).matches();
    }

    /** Returns true when string matches a rectangular A1 range like A1:C10. */
    static boolean isRange(String str) {
        return RANGE.matcher(str).matches();
    }

    /** Returns true when string matches a whole-column range like A:C. */
    static boolean isWholeColumnRange(String str) {
        return WHOLE_COL_RANGE.matcher(str).matches();
    }

    /** Returns true when string matches a whole-row range like 1:10. */
    static boolean isWholeRowRange(String str) {
        return WHOLE_ROW_RANGE.matcher(str).matches();
    }

    /** Returns true for quoted sheet names like 'My Sheet' or 'Jon\'s_Data'. */
    static boolean isQuotedSheet(String str) {
        return QUOTED_SHEET.matcher(str).matches();
    }

    /** Returns true when string is a sheet-only name (no ref part, may be quoted). */
    static boolean isSheetOnly(String str) {
        if (isQuotedSheet(str)) {
            return true;
        }
        if (isCell(str)) {
            return false;
        }
        return UNQUOTED_SHEET.matcher(str).matches();
    }

    /** Returns true when the token is a column-only token like A or BC. */
    static boolean isColumnOnly(String str) {
        return COLUMN_ONLY.matcher(str).matches();
    }

    /** Returns true when the token is a row-only token like 1 or 123. */
    static boolean isRowOnly(String str) {
        return ROW_ONLY.matcher(str).matches();
    }
}
