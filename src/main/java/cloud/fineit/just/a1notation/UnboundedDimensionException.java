package cloud.fineit.just.a1notation;

/**
 * Signals that a dimension cannot be determined for an A1 reference.
 *
 * <p>Typical cases include sheet-only references and whole-row/whole-column
 * ranges where one of the dimensions is unbounded.
 */
public final class UnboundedDimensionException extends RuntimeException {

    public UnboundedDimensionException(String message) {
        super(message);
    }
}
