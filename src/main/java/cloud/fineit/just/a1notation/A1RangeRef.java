package cloud.fineit.just.a1notation;

import cloud.fineit.just.SheetName;

import java.util.Optional;

import static cloud.fineit.just.a1notation.References.extractColumn;
import static cloud.fineit.just.a1notation.References.extractRow;

/**
 * RangeRef represents a range reference in A1 notation, including rectangular ranges and
 * whole row or whole column references.
 */
final class A1RangeRef implements A1Notation {

    private final Optional<SheetName> sheetName;

    private final Optional<A1Column> left;
    private final Optional<A1Column> right;
    private final Optional<A1Row> top;
    private final Optional<A1Row> bottom;


    A1RangeRef(A1Column left, A1Column right) {
        this.sheetName = Optional.empty();
        this.left = Optional.of(left);
        this.right = Optional.of(right);
        this.top = Optional.empty();
        this.bottom = Optional.empty();
    }

    A1RangeRef(A1Row top, A1Row bottom) {
        this.sheetName = Optional.empty();
        this.left = Optional.empty();
        this.right = Optional.empty();
        this.top = Optional.of(top);
        this.bottom = Optional.of(bottom);
    }

    A1RangeRef(A1Column left, A1Row top, A1Column right, A1Row bottom) {
        this.sheetName = Optional.empty();
        this.left = Optional.of(left);
        this.top = Optional.of(top);
        this.right = Optional.of(right);
        this.bottom = Optional.of(bottom);
    }

    A1RangeRef(Optional<SheetName> sheetName, A1Column left, A1Column right) {
        this.sheetName = sheetName;
        this.left = Optional.of(left);
        this.right = Optional.of(right);
        this.top = Optional.empty();
        this.bottom = Optional.empty();
    }

    A1RangeRef(Optional<SheetName> sheetName, A1Row top, A1Row bottom) {
        this.sheetName = sheetName;
        this.left = Optional.empty();
        this.right = Optional.empty();
        this.top = Optional.of(top);
        this.bottom = Optional.of(bottom);
    }

    A1RangeRef(Optional<SheetName> sheetName, A1Column left, A1Row top, A1Column right, A1Row bottom) {
        this.sheetName = sheetName;
        this.left = Optional.of(left);
        this.top = Optional.of(top);
        this.right = Optional.of(right);
        this.bottom = Optional.of(bottom);
    }

    A1RangeRef(SheetName sheetName, A1Column left, A1Column right) {
        this.sheetName = Optional.of(sheetName);
        this.left = Optional.of(left);
        this.right = Optional.of(right);
        this.top = Optional.empty();
        this.bottom = Optional.empty();
    }

    A1RangeRef(SheetName sheetName, A1Row top, A1Row bottom) {
        this.sheetName = Optional.of(sheetName);
        this.left = Optional.empty();
        this.right = Optional.empty();
        this.top = Optional.of(top);
        this.bottom = Optional.of(bottom);
    }

    A1RangeRef(SheetName sheetName, A1Column left, A1Row top, A1Column right, A1Row bottom) {
        this.sheetName = Optional.of(sheetName);
        this.left = Optional.of(left);
        this.top = Optional.of(top);
        this.right = Optional.of(right);
        this.bottom = Optional.of(bottom);
    }

    A1RangeRef(String original) {
        this.sheetName = SheetName.fromNotation(original);

        String ref = SheetName.refPart(original);
        String[] parts = ref.split(":", 2);
        String rangeStart = parts[0];
        String rangeEnd = parts[1];

        A1Column left = null;
        A1Column right = null;
        A1Row top = null;
        A1Row bottom = null;

        if (References.isColumnOnly(rangeStart) && References.isColumnOnly(rangeEnd)) {
            left = new A1Column(rangeStart);
            right = new A1Column(rangeEnd);
        } else if (References.isRowOnly(rangeStart) && References.isRowOnly(rangeEnd)) {
            top = new A1Row(Integer.parseInt(rangeStart));
            bottom = new A1Row(Integer.parseInt(rangeEnd));
        } else {
            left = extractColumn(rangeStart);
            right = extractColumn(rangeEnd);
            top = extractRow(rangeStart);
            bottom = extractRow(rangeEnd);
        }

        this.left = Optional.ofNullable(left);
        this.right = Optional.ofNullable(right);
        this.top = Optional.ofNullable(top);
        this.bottom = Optional.ofNullable(bottom);
    }

    /**
     * Returns {@code true} if the reference targets only whole columns.
     */
    public boolean columnsOnly() {
        return left.isPresent() && right.isPresent() && top.isEmpty() && bottom.isEmpty();
    }

    /**
     * Returns {@code true} if the reference targets only whole rows.
     */
    public boolean rowsOnly() {
        return top.isPresent() && bottom.isPresent() && left.isEmpty() && right.isEmpty();
    }

    @Override
    public String toString() {
        String prefix = sheetName.map(Stringifiers::sheetPrefix).orElse("");
        return prefix + toShortString();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent") // checked by columns/rows Only checks.
    @Override
    public String toShortString() {
        if (columnsOnly()) {
            return left.map(Object::toString).orElseThrow()
                + ":"
                + right.map(Object::toString).orElseThrow();
        }

        if (rowsOnly()) {
            return top.map(Object::toString).orElseThrow()
                + ":"
                + bottom.map(Object::toString).orElseThrow();
        }

        return new A1CellRef(left.get(), top.get()).toShortString()
            + ":"
            + new A1CellRef(right.get(), bottom.get()).toShortString();
    }

    @Override
    public int width() {
        if (rowsOnly()) {
            throw new UnboundedDimensionException("Width cannot be determined for whole-row range: " + this);
        }
        int leftIdx = left.orElseThrow().arrayIndex();
        int rightIdx = right.orElseThrow().arrayIndex();
        return Math.abs(rightIdx - leftIdx) + 1;
    }

    @Override
    public int height() {
        if (columnsOnly()) {
            throw new UnboundedDimensionException("Height cannot be determined for whole-column range: " + this);
        }
        int topIdx = top.orElseThrow().arrayIndex();
        int bottomIdx = bottom.orElseThrow().arrayIndex();
        return Math.abs(bottomIdx - topIdx) + 1;
    }
}
