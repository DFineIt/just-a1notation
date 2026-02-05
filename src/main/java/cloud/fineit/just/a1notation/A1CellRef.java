package cloud.fineit.just.a1notation;

import cloud.fineit.just.SheetName;

import java.util.Optional;

import static cloud.fineit.just.a1notation.Stringifiers.cellStr;

/**
 * CellRef represents a single cell reference in A1 notation.
 */
final class A1CellRef implements A1Notation {

    private final Optional<SheetName> sheetName;
    private final A1Column column;
    private final A1Row row;

    A1CellRef(A1Column column, A1Row row) {
        this.column = column;
        this.row = row;
        this.sheetName = Optional.empty();
    }

    A1CellRef(Optional<SheetName> sheetName, A1Column column, A1Row row) {
        this.column = column;
        this.row = row;
        this.sheetName = sheetName;
    }

    A1CellRef(SheetName sheetName, A1Column column, A1Row row) {
        this.column = column;
        this.row = row;
        this.sheetName = Optional.of(sheetName);
    }

    A1CellRef(String original) {
        this.sheetName = SheetName.fromNotation(original);

        String ref = SheetName.refPart(original);
        this.column = References.extractColumn(ref);
        this.row = References.extractRow(ref);
    }

    @Override
    public String toString() {
        return sheetName.map(Stringifiers::sheetPrefix).orElse("")
                + cellStr(column, row);
    }

    @Override
    public String toShortString() {
        return cellStr(column, row);
    }

    @Override
    public int width() {
        return 1;
    }

    @Override
    public int height() {
        return 1;
    }
}
