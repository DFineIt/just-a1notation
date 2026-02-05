package cloud.fineit.just.a1notation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("References should")
class ReferencesTest {

    @ParameterizedTest
    @CsvSource({
            "A1,A,1",
            "B2,B,2",
            "Z999,Z,999",
            "AA10,AA,10",
            "ABC123,ABC,123"
    })
    @DisplayName("extract column and row")
    void extractColumnAndRow(String input, String expectedCol, int expectedRow) {
        A1Column col = References.extractColumn(input);

        A1Row row = References.extractRow(input);

        assertEquals(expectedCol, col.value());
        assertEquals(expectedRow, row.value());
    }

    @ParameterizedTest
    @CsvSource({
            "A",
            "ABC",
            "XYZ"
    })
    @DisplayName("throw on references without row digits")
    void throwOnNoDigits(String input) {
        assertThrows(IllegalArgumentException.class, () -> References.extractRow(input));
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "12",
            "9999"
    })
    @DisplayName("throw on references without column letters")
    void throwOnOnlyRow(String input) {
        assertThrows(IllegalArgumentException.class, () -> References.extractColumn(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A1", "Z99", "AA10", "ABC123"})
    @DisplayName("recognize A1 cell references")
    void recognizeCells(String input) {
        boolean result = References.isCell(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "1", "A:", ":1", "A1:", ":B2", "A1:B", "A:B2", "!A1"})
    @DisplayName("not recognize invalid A1 cell references")
    void notRecognizeCells(String input) {
        boolean result = References.isCell(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A1:C10", "B2:B2", "AA10:AB20"})
    @DisplayName("recognize rectangular ranges")
    void recognizeRanges(String input) {
        boolean result = References.isRange(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A1", "A:C", "1:10", "A1:", ":B2", "A1:2", "A:2"})
    @DisplayName("not recognize invalid rectangular ranges")
    void notRecognizeRanges(String input) {
        boolean result = References.isRange(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A:C", "B:B", "AA:AZ"})
    @DisplayName("recognize whole-column ranges")
    void recognizeWholeColumnRanges(String input) {
        boolean result = References.isWholeColumnRange(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A1:C10", "1:10", "A", ":C", "A:", ":B"})
    @DisplayName("not recognize invalid whole-column ranges")
    void notRecognizeWholeColumnRanges(String input) {
        boolean result = References.isWholeColumnRange(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1:10", "2:2", "10:100"})
    @DisplayName("recognize whole-row ranges")
    void recognizeWholeRowRanges(String input) {
        boolean result = References.isWholeRowRange(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A:C", "A1:C10", "A", "0:10", "01:10", ":10", "1:"})
    @DisplayName("not recognize invalid whole-row ranges")
    void notRecognizeWholeRowRanges(String input) {
        boolean result = References.isWholeRowRange(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"'My Sheet'", "'Jon\\'s_Data'"})
    @DisplayName("recognize quoted sheet names")
    void recognizeQuotedSheets(String input) {
        boolean result = References.isQuotedSheet(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"''", "'Bad", "Bad'", "'Unescaped's'"})
    @DisplayName("not recognize invalid quoted sheet names")
    void notRecognizeQuotedSheets(String input) {
        boolean result = References.isQuotedSheet(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Sheet1", "Data_2025", "My Sheet", "'My Sheet'"})
    @DisplayName("recognize sheet-only names")
    void recognizeSheetOnly(String input) {
        boolean result = References.isSheetOnly(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A1", "A1:C10", "A:C", "1:10", "Sheet1!A1", "'My Sheet'!A1"})
    @DisplayName("not recognize non sheet-only names")
    void notRecognizeSheetOnly(String input) {
        boolean result = References.isSheetOnly(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "Z", "AA", "abc", "Bc"})
    @DisplayName("recognize column-only tokens")
    void recognizeColumnOnly(String input) {
        boolean result = References.isColumnOnly(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1", "A1", "1A", "A-", "A!"})
    @DisplayName("not recognize invalid column-only tokens")
    void notRecognizeColumnOnly(String input) {
        boolean result = References.isColumnOnly(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "10", "999"})
    @DisplayName("recognize row-only tokens")
    void recognizeRowOnly(String input) {
        boolean result = References.isRowOnly(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "01", "A", "A1", "-1", "1.0"})
    @DisplayName("not recognize invalid row-only tokens")
    void notRecognizeRowOnly(String input) {
        boolean result = References.isRowOnly(input);

        assertFalse(result);
    }
}
