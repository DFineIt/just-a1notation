package cloud.fineit.just.a1notation;

import cloud.fineit.just.AddressNotation;
import cloud.fineit.just.NotationType;

/**
 * A1Notation defines the spreadsheet A1 notation used to reference cells and ranges by
 * column letters and row numbers, optionally prefixed with a sheet name.
 *
 * <p>In A1 notation, a reference combines the sheet name (optional) and the starting and ending
 * cell coordinates. Column indices are written as letters (A, B, ..., Z, AA, AB, ...), and row
 * indices are written as numbers (1, 2, ...). This style is the most common and is typically used
 * for absolute references to cells or rectangular ranges.
 *
 * <p>Examples:
 *
 * <pre>{@code
 * Sheet1!A1:B2
 * Sheet1!A:A
 * Sheet1!1:2
 * Sheet1!A5:A
 * A1:B2
 * Sheet1
 * 'Jon\'s_Data'!A1:D5
 * 'My Custom Sheet'!A:A
 * 'My Custom Sheet'
 * }</pre>
 *
 * <p>Note: single quotes are required for sheet names that contain spaces or special characters.
 *
 * <p>Tip: use distinct names for objects to avoid ambiguity. For example, {@code A1} (without
 * quotes) refers to cell A1 in the first visible sheet, while {@code 'A1'} refers to a sheet named
 * A1. Similarly, {@code Sheet1} refers to a sheet named Sheet1 unless a named range called
 * {@code Sheet1} exists, in which case {@code Sheet1} refers to the named range and
 * {@code 'Sheet1'} refers to the sheet.
 *
 * <p>Contrast: R1C1 notation uses row and column numbers (e.g., {@code R1C1:R2C2}) and allows
 * relative references (e.g., {@code R[3]C[1]}), which differs from the A1 letter-and-number style.
 */
public interface A1Notation extends AddressNotation {

    @Override
    default NotationType type(){
        return NotationType.A1;
    }

    /**
     * Parses a string and returns an A1Notation of the appropriate kind.
     *
     * @param a1Notation the input string
     * @return A1Notation instance
     * @throws IllegalArgumentException if the input cannot be parsed
     */
    static A1Notation of(String a1Notation) {
        if (a1Notation == null || a1Notation.isEmpty()) {
            throw new IllegalArgumentException("A1 notation must not be empty");
        }


        int bang = a1Notation.indexOf('!');
        String refPart = a1Notation;
        if (bang >= 0) {
            refPart = a1Notation.substring(bang + 1);
        }

        if (bang < 0 && References.isSheetOnly(a1Notation)) {
            return new A1SheetRef(a1Notation);
        }

        if (References.isCell(refPart)) {
            return new A1CellRef(a1Notation);
        }

        if (References.isRange(refPart)
            || References.isWholeColumnRange(refPart)
            || References.isWholeRowRange(refPart)) {
            return new A1RangeRef(a1Notation);
        }

        throw new IllegalArgumentException("Unsupported A1 notation: " + a1Notation);
    }

    /**
     * Returns a builder preconfigured with the given sheet name.
     *
     * <p>Helps to create sheet-qualified references.
     *
     * @param sheetName sheet name without surrounding quotes
     * @return builder instance
     * @throws IllegalArgumentException if the name is null or empty
     */
    static A1NotationBuilder withSheet(String sheetName) {
        if (sheetName == null || sheetName.isEmpty()) {
            throw new IllegalArgumentException("Sheet name must not be empty");
        }
        return new A1NotationBuilder(sheetName);
    }

    /**
     * Creates a whole-row reference like {@code 5:5}.
     *
     * @param row positive row number
     * @return A1 notation instance
     * @throws IllegalArgumentException if the row index is invalid
     */
    static A1Notation row(int row) {
        return new A1NotationBuilder().forRow(row);
    }

    /**
     * Creates a whole-rows reference like {@code 1:10}.
     *
     * @param from positive start row, inclusive
     * @param to positive end row, inclusive
     * @return A1 notation instance
     * @throws IllegalArgumentException if bounds are invalid
     */
    static A1Notation rows(int from, int to) {
        return new A1NotationBuilder().forRows(from, to);
    }

    /**
     * Creates a whole-column reference like {@code A:A}.
     *
     * @param col column index, case-insensitive
     * @return A1 notation instance
     * @throws IllegalArgumentException if the column index is invalid
     */
    static A1Notation column(String col) {
        return new A1NotationBuilder().forColumn(col);
    }

    /**
     * Creates a whole-columns reference like {@code A:Z}.
     *
     * @param from starting column index, case-insensitive, inclusive
     * @param to ending column index, case-insensitive, inclusive
     * @return A1 notation instance
     * @throws IllegalArgumentException if the column indexes are invalid
     */
    static A1Notation columns(String from, String to) {
        return new A1NotationBuilder().forColumns(from, to);
    }

    /**
     * Creates a single cell reference like {@code B2}.
     *
     * @param colLetters column index, case-insensitive
     * @param row positive row number
     * @return A1 notation instance
     * @throws IllegalArgumentException if the input is invalid
     */
    static A1Notation cell(String colLetters, int row) {
        return new A1NotationBuilder().forCell(colLetters, row);
    }

    /**
     * Creates a rectangular range like {@code A1:C10}.
     *
     * @param fromCol starting column index, case-insensitive
     * @param fromRow starting row, positive
     * @param toCol ending column index, case-insensitive
     * @param toRow ending row, positive
     * @return A1 notation instance
     * @throws IllegalArgumentException if any argument is invalid
     */
    static A1Notation range(String fromCol, int fromRow, String toCol, int toRow) {
        return new A1NotationBuilder().range(fromCol, fromRow, toCol, toRow);
    }

    /**
     * Returns A1Notation without a sheet name.
     */
    String toShortString();

    /**
     * Returns the number of columns spanned by this reference.
     *
     * <p>Returns {@code 1} for a single cell and {@code (right - left + 1)} for a rectangular
     * range. Throws an exception when the width cannot be determined (for example, for whole-row
     * references or a sheet-only reference).
     *
     * @return positive number of columns spanned
     * @throws UnboundedDimensionException when the width cannot be determined
     */
    int width();

    /**
     * Returns the number of rows spanned by this reference.
     *
     * <p>Returns {@code 1} for a single cell and {@code (bottom - top + 1)} for a rectangular
     * range. Throws an exception when the height cannot be determined (for example, for whole-
     * column references or a sheet-only reference).
     *
     * @return positive number of rows spanned
     * @throws UnboundedDimensionException when the height cannot be determined
     */
    int height();
}
