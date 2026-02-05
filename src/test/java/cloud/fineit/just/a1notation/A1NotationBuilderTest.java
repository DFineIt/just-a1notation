package cloud.fineit.just.a1notation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("A1NotationBuilder should")
class A1NotationBuilderTest {

    @Nested
    @DisplayName("produce a valid A1 Notation string for")
    class Valid {

        @ParameterizedTest
        @CsvSource({
                "Sheet1,Sheet1",
                "'My Sheet','''My Sheet'''"
        })
        @DisplayName("the sheet only input")
        void sheetOnly(String sheet, String expected) {
            A1Notation notation = A1Notation.withSheet(sheet).sheetOnly();

            assertEquals(expected, notation.toString());
        }

        @Test
        @DisplayName("the sheet only input with apostrophe")
        void sheetOnlyWithApostrophe() {
            String sheet = "Jon's";
            String expected = "'Jon\\'s'";

            A1Notation notation = A1Notation.withSheet(sheet).sheetOnly();

            assertEquals(expected, notation.toString());
        }

        @ParameterizedTest
        @CsvSource({
                "Sheet1,5,'Sheet1!5:5'",
                "Data_2025,1,'Data_2025!1:1'",
                "My Sheet,10,'''My Sheet''!10:10'"
        })
        @DisplayName("the whole-row references")
        void forRow(String sheet, int row, String expected) {
            A1Notation result = A1Notation.withSheet(sheet).forRow(row);
            assertEquals(expected, result.toString());
        }

        @ParameterizedTest
        @CsvSource({
                "Sheet1,1,10,'Sheet1!1:10'",
                "Data,2,2,'Data!2:2'",
                "My Sheet,3,5,'''My Sheet''!3:5'"
        })
        @DisplayName("the whole-rows ranges")
        void forRows(String sheet, int from, int to, String expected) {
            A1Notation result = A1Notation.withSheet(sheet).forRows(from, to);
            assertEquals(expected, result.toString());
        }

        @ParameterizedTest
        @CsvSource({
                "Sheet1,a,'Sheet1!A:A'",
                "Sheet1,Z,'Sheet1!Z:Z'",
                "My Sheet,b,'''My Sheet''!B:B'",
                "Data,aa,'Data!AA:AA'"
        })
        @DisplayName("the whole-column indexes")
        void forColumn(String sheet, String col, String expected) {
            A1Notation result = A1Notation.withSheet(sheet).forColumn(col);
            assertEquals(expected, result.toString());
        }


        @ParameterizedTest
        @CsvSource({
                "S,a,z,'S!A:Z'",
                "My Sheet,b,aa,'''My Sheet''!B:AA'"
        })
        @DisplayName("whole-columns ranges")
        void forColumns(String sheet, String from, String to, String expected) {
            A1Notation result = A1Notation.withSheet(sheet).forColumns(from, to);
            assertEquals(expected, result.toString());
        }

        @ParameterizedTest
        @CsvSource({
                "Sheet1,b,2,'Sheet1!B2'",
                "My Sheet,aa,10,'''My Sheet''!AA10'"
        })
        @DisplayName("cell")
        void forCell(String sheet, String col, int row, String expected) {
            A1Notation result = A1Notation.withSheet(sheet).forCell(col, row);
            assertEquals(expected, result.toString());
        }


        @ParameterizedTest
        @CsvSource({
                "Sheet1,a,1,c,10,'Sheet1!A1:C10'",
                "My Sheet,b,2,aa,20,'''My Sheet''!B2:AA20'"
        })
        @DisplayName("range")
        void range(String sheet, String c1, int r1, String c2, int r2, String expected) {
            A1Notation result = A1Notation.withSheet(sheet).range(c1, r1, c2, r2);
            assertEquals(expected, result.toString());
        }
    }

    @Nested
    @DisplayName("throw on")
    class Invalid {
        @ParameterizedTest
        @ValueSource(ints = {0, -3})
        @DisplayName("non-positive row index")
        void nonPositiveRows(int row) {
            assertThrows(IllegalArgumentException.class, () ->
                    A1Notation.withSheet("Sheet1").forRow(row));
        }

        @ParameterizedTest
        @CsvSource({
                "0,1",
                "2,0",
                "5,4"
        })
        @DisplayName("invalid row bounds")
        void invalidRowBounds(int from, int to) {
            assertThrows(IllegalArgumentException.class, () ->
                    A1Notation.withSheet("S").forRows(from, to));
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {"A1"})
        @DisplayName("invalid column indexes")
        void invalidColumns(String col) {
            assertThrows(IllegalArgumentException.class, () ->
                    A1Notation.withSheet("S").forColumn(col));
        }

        @Test
        @DisplayName("invalid column indexes for null column")
        void invalidColumnsNull() {
            assertThrows(IllegalArgumentException.class, () ->
                    A1Notation.withSheet("S").forColumn((String) null));
        }

        @ParameterizedTest
        @CsvSource({
                "A1,1",
                "A,0"
        })
        @DisplayName("invalid cell address")
        void invalidCell(String col, int row) {
            assertThrows(IllegalArgumentException.class, () ->
                    A1Notation.withSheet("S").forCell(col, row));
        }

        @Test
        @DisplayName("invalid cell address for null column")
        void invalidCellNull() {
            assertThrows(IllegalArgumentException.class, () ->
                    A1Notation.withSheet("S").forCell(null, 5));
        }

        @ParameterizedTest
        @CsvSource({
                "A,0,B,1",
                "A,1,B,0",
                "A1,1,B,2"
        })
        @DisplayName("invalid range boundaries")
        void invalidRange(String c1, int r1, String c2, int r2) {
            assertThrows(IllegalArgumentException.class, () ->
                    A1Notation.withSheet("S").range(c1, r1, c2, r2));
        }

        @Test
        @DisplayName("invalid range boundaries for null end column")
        void invalidRangeNullEndColumn() {
            assertThrows(IllegalArgumentException.class, () ->
                    A1Notation.withSheet("S").range("A", 1, null, 2));
        }
    }
}
