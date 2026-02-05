package cloud.fineit.just.a1notation;

/**
 * Column index written with A–Z letters in A1 notation.
 *
 * <p>Stores the normalized (uppercased) column letters.
 */
public final class A1Column {

    private final String letters;

    /**
     * Creates a column from its letters.
     *
     * @param letters column letters, case-insensitive
     * @throws IllegalArgumentException if letters are null or not alphabetic
     */
    public A1Column(String letters) {
        if (letters == null || !letters.matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Invalid column letters");
        }
        this.letters = letters.toUpperCase();
    }

    /**
     * Returns the normalized column letters.
     *
     * @return letters in uppercase
     */
    public String value() {
        return letters;
    }

    /**
     * Returns the 0-based column index for array access.
     *
     * @return 0-based index
     */
    public int arrayIndex() {
        return index(this) - 1;
    }

    /**
     * Returns the 1-based column index for the given A1 column.
     *
     * <p>Maps letters to numbers using base-26 with A=1, Z=26, AA=27 and so on.
     *
     * @param col column letters to convert
     * @return 1-based column index
     */
    static int index(A1Column col) {
        String columnLetters = col.value();
        int result = 0;
        for (int i = 0; i < columnLetters.length(); i++) {
            char character = columnLetters.charAt(i);
            int k = (character - 'A') + 1;
            result = result * 26 + k;
        }
        return result;
    }

    @Override
    public String toString() {
        return letters;
    }
}
