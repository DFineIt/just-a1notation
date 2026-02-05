package cloud.fineit.just.a1notation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("A1Column")
class A1ColumnTest {

    @Nested
    @DisplayName("when created")
    class Creation {

        @Test
        @DisplayName("should store normalized letters")
        void storeNormalizedLetters() {
            assertAll(
                () -> assertEquals("A", new A1Column("a").value()),
                () -> assertEquals("BC", new A1Column("Bc").value()),
                () -> assertEquals("ZZ", new A1Column("zz").value())
            );
        }

        @Test
        @DisplayName("should throw exception for invalid letters")
        void throwExceptionForInvalidLetters() {
            assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new A1Column(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new A1Column("")),
                () -> assertThrows(IllegalArgumentException.class, () -> new A1Column("A1")),
                () -> assertThrows(IllegalArgumentException.class, () -> new A1Column("A B"))
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
                () -> assertEquals(0, new A1Column("A").arrayIndex()),
                () -> assertEquals(25, new A1Column("Z").arrayIndex()),
                () -> assertEquals(26, new A1Column("AA").arrayIndex()),
                () -> assertEquals(702, new A1Column("AAA").arrayIndex())
            );
        }

        @Test
        @DisplayName("should return string representation of its value")
        void returnStringRepresentation() {
            assertEquals("ABC", new A1Column("abc").toString());
        }
    }
}
