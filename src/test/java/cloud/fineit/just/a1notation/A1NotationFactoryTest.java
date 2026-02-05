package cloud.fineit.just.a1notation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("A1Notation factories should")
class A1NotationFactoryTest {

    @Test
    @DisplayName("create a row reference")
    void row() {
        A1Notation notation = A1Notation.row(5);

        assertEquals("5:5", notation.toString());
    }

    @Test
    @DisplayName("create a rows range")
    void rows() {
        A1Notation notation = A1Notation.rows(1, 10);

        assertEquals("1:10", notation.toString());
    }

    @Test
    @DisplayName("create a column reference")
    void column() {
        A1Notation notation = A1Notation.column("A");

        assertEquals("A:A", notation.toString());
    }

    @Test
    @DisplayName("create a columns range")
    void columns() {
        A1Notation notation = A1Notation.columns("A", "Z");

        assertEquals("A:Z", notation.toString());
    }

    @Test
    @DisplayName("create a cell reference")
    void cell() {
        A1Notation notation = A1Notation.cell("B", 2);

        assertEquals("B2", notation.toString());
    }

    @Test
    @DisplayName("create a rectangular range")
    void range() {
        A1Notation notation = A1Notation.range("A", 1, "C", 10);

        assertEquals("A1:C10", notation.toString());
    }

    @Nested
    @DisplayName("when using builder with domain objects")
    class BuilderWithDomainObjects {

        @Test
        @DisplayName("create a row reference")
        void forRow() {
            A1Notation notation = A1Notation.withSheet("Sheet1").forRow(new A1Row(5));

            assertEquals("Sheet1!5:5", notation.toString());
        }

        @Test
        @DisplayName("create a rows range")
        void forRows() {
            A1Notation notation = A1Notation.withSheet("Sheet1").forRows(new A1Row(1), new A1Row(10));

            assertEquals("Sheet1!1:10", notation.toString());
        }

        @Test
        @DisplayName("create a column reference")
        void forColumn() {
            A1Notation notation = A1Notation.withSheet("Sheet1").forColumn(new A1Column("A"));

            assertEquals("Sheet1!A:A", notation.toString());
        }

        @Test
        @DisplayName("create a columns range")
        void forColumns() {
            A1Notation notation = A1Notation.withSheet("Sheet1").forColumns(new A1Column("A"), new A1Column("Z"));

            assertEquals("Sheet1!A:Z", notation.toString());
        }

        @Test
        @DisplayName("create a cell reference")
        void forCell() {
            A1Notation notation = A1Notation.withSheet("Sheet1").forCell(new A1Column("B"), new A1Row(2));

            assertEquals("Sheet1!B2", notation.toString());
        }

        @Test
        @DisplayName("create a rectangular range")
        void range() {
            A1Notation notation = A1Notation.withSheet("Sheet1").range(
                    new A1Column("A"), new A1Row(1),
                    new A1Column("C"), new A1Row(10)
            );

            assertEquals("Sheet1!A1:C10", notation.toString());
        }
    }
}
