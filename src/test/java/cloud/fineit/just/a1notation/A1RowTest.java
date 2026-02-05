package cloud.fineit.just.a1notation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("A1Row")
class A1RowTest {

    @Nested
    @DisplayName("when created")
    class Creation {

        @Test
        @DisplayName("should store positive index")
        void storePositiveIndex() {
            A1Row row = new A1Row(1);

            assertEquals(1, row.value());
        }

        @Test
        @DisplayName("should throw exception for non-positive index")
        void throwExceptionForNonPositiveIndex() {
            assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new A1Row(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new A1Row(-1))
            );
        }
    }

    @Nested
    @DisplayName("accessors")
    class Accessors {

        @Test
        @DisplayName("should return zero-based index for array access")
        void returnZeroBasedIndex() {
            assertAll(
                () -> assertEquals(0, new A1Row(1).arrayIndex()),
                () -> assertEquals(9, new A1Row(10).arrayIndex()),
                () -> assertEquals(1233, new A1Row(1234).arrayIndex())
            );
        }

        @Test
        @DisplayName("should return string representation of its value")
        void returnStringRepresentation() {
            assertEquals("5", new A1Row(5).toString());
        }
    }
}
