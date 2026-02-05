package cloud.fineit.just.a1notation;

/**
 * Positive row index in A1 notation.
 */
public final class A1Row {

    private final int index;

    /**
     * Creates a row from its 1-based index.
     *
     * @param index positive row number
     * @throws IllegalArgumentException if index is not positive
     */
    public A1Row(int index) {
        if (index <= 0) {
            throw new IllegalArgumentException("Row index must be positive");
        }
        this.index = index;
    }

    /**
     * Returns the 1-based row index.
     *
     * @return 1-based index
     */
    public int value() {
        return index;
    }

    /**
     * Returns the 0-based row index for array access.
     *
     * @return 0-based index
     */
    public int arrayIndex() {
        return index - 1;
    }

    @Override
    public String toString() {
        return Integer.toString(index);
    }
}
