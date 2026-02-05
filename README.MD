# A1 Notation

Lightweight Java library for parsing and formatting spreadsheet addresses in A1 notation. No
additional runtime dependencies.

## A1 notation

A syntax used to define a cell or range of cells with a string that contains the sheet name plus the starting and ending cell coordinates using column letters and row numbers. This method is the most common and useful when referencing an absolute range of cells.

Examples:

- Sheet1!A1:B2 refers to all the cells in the first two rows and columns of Sheet1.
- Sheet1!A:A refers to all the cells in the first column of Sheet1.
- Sheet1!1:2 refers to all the cells in the first two rows of Sheet1.
- Sheet1!A5:A refers to all the cells of the first column of Sheet 1, from row 5 onward.
- A1:B2 refers to all the cells in the first two rows and columns of the first visible sheet.
- Sheet1 refers to all the cells in Sheet1.
- 'Jon's_Data'!A1:D5 refers to all the cells in the first five rows and four columns of a sheet named "Jon's_Data."
- 'My Custom Sheet'!A:A refers to all the cells in the first column of a sheet named "My Custom Sheet."
- 'My Custom Sheet' refers to all the cells in "My Custom Sheet".

Note:

- Single quotes are required for sheet names with spaces or special characters.

Tip:

- Where possible, use distinct names for objects in your spreadsheets. For example, A1 (without quotes) refers to cell A1 in the first visible sheet. 'A1' refers to a sheet with the name A1. Similarly, Sheet1 refers to a sheet with the name Sheet1. However, if there's a named range titled "Sheet1", then Sheet1 refers to the named range and 'Sheet1' refers to the sheet.

## Installation

You can find the library on [Maven Central](https://central.sonatype.com/artifact/cloud.fine-it/just-a1notation).

### Maven
```xml
<dependency>
    <groupId>cloud.fine-it</groupId>
    <artifactId>just-a1notation</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Gradle
```kotlin
implementation("cloud.fine-it:just-a1notation:1.0.2")
```

## Usage

### Instantiation

`A1Notation` provides several ways to create instances, depending on your needs.

#### From String
Parse an existing A1 notation string:
```java
A1Notation notation = A1Notation.of("Sheet1!A1:B2");
```

#### Using Static Factory Methods
Create notations for common scenarios without a sheet name:
```java
A1Notation cell = A1Notation.cell("A", 1);               // A1
A1Notation row = A1Notation.row(5);                     // 5:5
A1Notation rows = A1Notation.rows(1, 10);               // 1:10
A1Notation column = A1Notation.column("B");             // B:B
A1Notation columns = A1Notation.columns("A", "C");      // A:C
A1Notation range = A1Notation.range("A", 1, "C", 10);   // A1:C10
```

#### Using Builder (with Sheet Name)
Use the builder to specify a sheet name and other details:
```java
A1Notation notation = A1Notation.withSheet("My Sheet")
    .forCell("A", 1); // 'My Sheet'!A1

A1Notation range = A1Notation.withSheet("Data")
    .range("A", 1, "B", 2); // Data!A1:B2
```

The builder also supports domain objects like `A1Row` and `A1Column`:
```java
A1Notation notation = A1Notation.withSheet("Sheet1")
    .forRow(new A1Row(5));
```

### Public Methods

The `A1Notation` interface provides methods to inspect the notation:

- `type()`: Returns the `NotationType` (e.g., `CELL`, `RANGE`, `ROW`, `COLUMN`).
- `width()`: Returns the number of columns covered by the notation.
- `height()`: Returns the number of rows covered by the notation.
- `toShortString()`: Returns a concise string representation.
- `toString()`: Returns the full A1 notation string.

## Running locally

- Prerequisites: Java 11+, Gradle Wrapper bundled in the repo
- Build: `./gradlew build`
- Run tests: `./gradlew test`

