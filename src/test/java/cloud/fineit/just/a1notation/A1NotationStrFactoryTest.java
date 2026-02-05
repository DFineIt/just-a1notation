package cloud.fineit.just.a1notation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Correctly instantiates A1Notation from string that represents")
class A1NotationStrFactoryTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "A1",
            "B2",
            "Z999",
            "AA10",
            "A1:B2",
            "C3:D4"
    })
    @DisplayName("single cells and simple ranges")
    void singleCellsAndRanges(String input) {
        A1Notation notation = A1Notation.of(input);
        assertNotNull(notation, "factory must return a non-null instance");
        assertEquals(input, notation.toString(),
                "string representation must equal original input");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "A:A",
            "Z:Z"
    })
    @DisplayName("whole column references")
    void wholeColumnReferences(String input) {
        A1Notation notation = A1Notation.of(input);
        assertNotNull(notation, "factory must return a non-null instance");
        assertEquals(input, notation.toString(),
                "string representation must equal original input");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1:1",
            "1:2"
    })
    @DisplayName("whole row references")
    void wholeRowReferences(String input) {
        A1Notation notation = A1Notation.of(input);
        assertNotNull(notation, "factory must return a non-null instance");
        assertEquals(input, notation.toString(),
                "string representation must equal original input");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Sheet1!A1",
            "Sheet1!A1:B2",
            "Data_2025!A:A",
            "Data_2025!1:10"
    })
    @DisplayName("unquoted sheet references")
    void unquotedSheetNames(String input) {
        A1Notation notation = A1Notation.of(input);
        assertNotNull(notation, "factory must return a non-null instance");
        assertEquals(input, notation.toString(),
                "string representation must equal original input");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "'My Custom Sheet'!A1:D5",
            "'My Custom Sheet'!A:A",
            "'My Custom Sheet'",
            "'Jon\\'s_Data'!A1:D5",
            "'Лист 1'!B2:C3"
    })
    @DisplayName("quoted sheet references")
    void quotedSheetNames(String input) {
        A1Notation notation = A1Notation.of(input);
        assertNotNull(notation, "factory must return a non-null instance");
        assertEquals(input, notation.toString(),
                "string representation must equal original input");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Sheet1",
            "'Finance Q4'"
    })
    @DisplayName("sheet-only references")
    void sheetOnlyReferences(String input) {
        A1Notation notation = A1Notation.of(input);
        assertNotNull(notation, "factory must return a non-null instance");
        assertEquals(input, notation.toString(),
                "string representation must equal original input");
    }
}
