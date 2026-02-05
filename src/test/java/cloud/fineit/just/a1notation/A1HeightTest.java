package cloud.fineit.just.a1notation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A1Notation height calculation")
class A1HeightTest {

    @Test
    @DisplayName("should return 1 when reference targets single cell")
    void singleCell() {
        A1Notation notation = A1Notation.of("H4");

        assertEquals(1, notation.height());
    }

    @Test
    @DisplayName("should return count of rows when reference targets rectangular range")
    void rectangularRange() {
        A1Notation notation = A1Notation.of("C2:C8");

        assertEquals(7, notation.height());
    }

    @Test
    @DisplayName("should return count of rows when reference has reversed endpoints")
    void reversedRangeRows() {
        A1Notation notation = A1Notation.of("B15:B11");

        assertEquals(5, notation.height());
    }

    @Test
    @DisplayName("should return count of rows when reference targets whole rows")
    void wholeRows() {
        A1Notation notation = A1Notation.of("5:9");

        assertEquals(5, notation.height());
    }

    @Test
    @DisplayName("should throw error when reference targets whole columns")
    void wholeColumnsThrows() {
        A1Notation notation = A1Notation.of("M:Q");

        assertThrows(UnboundedDimensionException.class, notation::height);
    }

    @Test
    @DisplayName("should throw error when reference targets sheet only")
    void sheetOnlyThrows() {
        A1Notation notation = A1Notation.of("Report_2024");

        assertThrows(UnboundedDimensionException.class, notation::height);
    }

    @Test
    @DisplayName("should ignore sheet prefix when computing height")
    void withSheetPrefix() {
        A1Notation notation = A1Notation.of("'Ops West'!D4:D10");

        assertEquals(7, notation.height());
    }
}
