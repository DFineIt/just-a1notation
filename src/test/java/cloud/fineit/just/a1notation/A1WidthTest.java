package cloud.fineit.just.a1notation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A1Notation width calculation")
class A1WidthTest {

    @Test
    @DisplayName("should return 1 when reference targets single cell")
    void singleCell() {
        A1Notation notation = A1Notation.of("C7");

        assertEquals(1, notation.width());
    }

    @Test
    @DisplayName("should return count of columns when reference targets rectangular range")
    void rectangularRange() {
        A1Notation notation = A1Notation.of("B3:E9");

        assertEquals(4, notation.width());
    }

    @Test
    @DisplayName("should return count of columns when reference has reversed endpoints")
    void reversedRangeColumns() {
        A1Notation notation = A1Notation.of("F10:C10");

        assertEquals(4, notation.width());
    }

    @Test
    @DisplayName("should return count of columns when reference targets whole columns")
    void wholeColumns() {
        A1Notation notation = A1Notation.of("A:D");

        assertEquals(4, notation.width());
    }

    @Test
    @DisplayName("should throw error when reference targets whole rows")
    void wholeRowsThrows() {
        A1Notation notation = A1Notation.of("11:15");

        assertThrows(UnboundedDimensionException.class, notation::width);
    }

    @Test
    @DisplayName("should throw error when reference targets sheet only")
    void sheetOnlyThrows() {
        A1Notation notation = A1Notation.of("'Quarter Report'");

        assertThrows(UnboundedDimensionException.class, notation::width);
    }

    @Test
    @DisplayName("should ignore sheet prefix when computing width")
    void withSheetPrefix() {
        A1Notation notation = A1Notation.of("'North Region'!K2:N6");

        assertEquals(4, notation.width());
    }
}
