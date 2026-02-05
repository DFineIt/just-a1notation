package cloud.fineit.just.a1notation;

import cloud.fineit.just.SheetName;

import java.util.Optional;

/**
 * A1NotationBuilder constructs {@link A1Notation} scoped to a specific sheet.
 *
 * <p>When a sheet name contains spaces or a single quote, the builder quotes it with single quotes
 * and escapes inner quotes with a backslash to keep the resulting notation valid.
 */
public final class A1NotationBuilder {

    private final Optional<SheetName> sheet;

    /**
     * Creates a builder bound to the given sheet name.
     *
     * <p>Use {@link A1Notation#withSheet(String)} to obtain an instance.
     *
     * @param sheetName sheet name without surrounding quotes
     */
    A1NotationBuilder(String sheetName) {
        this.sheet = Optional.ofNullable(sheetName).map(SheetName::new);
    }

    /**
     * Creates a builder with no sheet name.
     */
    A1NotationBuilder() {
        this.sheet = Optional.empty();
    }

    /**
     * Creates a whole-row reference like {@code 5:5}.
     *
     * @param row positive row number
     * @return A1 notation instance
     * @throws IllegalArgumentException if the row index is invalid
     */
    public A1Notation forRow(int row) {
        return forRow(new A1Row(row));
    }

    /**
     * Creates a whole-row reference like {@code 5:5}.
     *
     * @param row row index
     * @return A1 notation instance
     */
    public A1Notation forRow(A1Row row) {
        return new A1RangeRef(sheet, row, row);
    }

    /**
     * Creates a whole-rows reference like {@code 1:10}.
     *
     * @param from positive start row, inclusive
     * @param to positive end row, inclusive
     * @return A1 notation instance
     * @throws IllegalArgumentException if bounds are invalid
     */
    public A1Notation forRows(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("Invalid row index bounds");
        }
        return forRows(new A1Row(from), new A1Row(to));
    }

    /**
     * Creates a whole-rows reference like {@code 1:10}.
     *
     * @param from starting row, inclusive
     * @param to ending row, inclusive
     * @return A1 notation instance
     * @throws IllegalArgumentException if bounds are invalid
     */
    public A1Notation forRows(A1Row from, A1Row to) {
        return new A1RangeRef(sheet, from, to);
    }

    /**
     * Creates a whole-column reference like {@code A:A}.
     *
     * @param col column index, case-insensitive
     * @return A1 notation instance
     * @throws IllegalArgumentException if the column index is invalid
     */
    public A1Notation forColumn(String col) {
        return forColumn(new A1Column(col));
    }

    /**
     * Creates a whole-column reference like {@code A:A}.
     *
     * @param col column index
     * @return A1 notation instance
     */
    public A1Notation forColumn(A1Column col) {
        return new A1RangeRef(sheet, col, col);
    }

    /**
     * Creates a whole-columns reference like {@code A:Z}.
     *
     * @param from starting column index, case-insensitive, inclusive
     * @param to ending column index, case-insensitive, inclusive
     * @return A1 notation instance
     * @throws IllegalArgumentException if the column indexes are invalid
     */
    public A1Notation forColumns(String from, String to) {
        return forColumns(new A1Column(from), new A1Column(to));
    }

    /**
     * Creates a whole-columns reference like {@code A:Z}.
     *
     * @param from starting column index, inclusive
     * @param to ending column index, inclusive
     * @return A1 notation instance
     */
    public A1Notation forColumns(A1Column from, A1Column to) {
        return new A1RangeRef(sheet, from, to);
    }

    /**
     * Creates a single cell reference like {@code B2}.
     *
     * @param colLetters column index, case-insensitive
     * @param row positive row number
     * @return A1 notation instance
     * @throws IllegalArgumentException if the input is invalid
     */
    public A1Notation forCell(String colLetters, int row) {
        return forCell(new A1Column(colLetters), new A1Row(row));
    }

    /**
     * Creates a single cell reference like {@code B2}.
     *
     * @param col column index
     * @param row row index
     * @return A1 notation instance
     */
    public A1Notation forCell(A1Column col, A1Row row) {
        return new A1CellRef(sheet, col, row);
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
    public A1Notation range(String fromCol, int fromRow, String toCol, int toRow) {
        return range(
                new A1Column(fromCol), new A1Row(fromRow),
                new A1Column(toCol), new A1Row(toRow)
        );
    }

    /**
     * Creates a rectangular range like {@code A1:C10}.
     *
     * @param fromCol starting column index
     * @param fromRow starting row
     * @param toCol ending column index
     * @param toRow ending row
     * @return A1 notation instance
     */
    public A1Notation range(A1Column fromCol, A1Row fromRow, A1Column toCol, A1Row toRow) {
        return new A1RangeRef(sheet, fromCol, fromRow, toCol, toRow);
    }

    /**
     * Creates a sheet-only reference like {@code 'My Sheet'}.
     *
     * @return A1 notation instance
     */
    public A1Notation sheetOnly() {
        return new A1SheetRef(sheet.orElseThrow(() -> new IllegalStateException("Sheet name is not set")));
    }
}
