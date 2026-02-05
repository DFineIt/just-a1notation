package cloud.fineit.just.a1notation;

import cloud.fineit.just.SheetName;

import static cloud.fineit.just.a1notation.Stringifiers.sheetNameStr;

/**
 * SheetRef represents a sheet-only A1 reference.
 */
final class A1SheetRef implements A1Notation {

    private final SheetName sheetName;


    A1SheetRef(SheetName sheetName) {
        this.sheetName = sheetName;
    }

    A1SheetRef(String original) {
        this.sheetName = SheetName.parse(original);
    }

    @Override
    public String toString() {
        return sheetNameStr(sheetName);
    }

    @Override
    public String toShortString() {
        return "";
    }

    @Override
    public int width() {
        throw new UnboundedDimensionException("Width cannot be determined for sheet-only reference: " + this);
    }

    @Override
    public int height() {
        throw new UnboundedDimensionException("Height cannot be determined for sheet-only reference: " + this);
    }
}
