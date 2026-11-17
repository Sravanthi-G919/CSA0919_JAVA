# Task A1 – Design a File Format

## A1.1 Record layouts
**Delimited text (CSV):** `passId,regNo,name,routeNo,boardingStop,validityDate,amountPaid` followed by newline. Use UTF-8. Quote/escape fields according to a defined CSV rule; commas, quotes and newlines inside a name/stop must be quoted and embedded quotes doubled. A plain `String.split(",")` is not a correct general CSV parser. For this simple implementation, the Part B sample format explicitly disallows commas in fields; production CSV should use a CSV library.

**Binary using DataOutputStream:** write fields in this fixed order: `writeUTF(passId)`, `writeUTF(regNo)`, `writeUTF(name)`, `writeInt(routeNo)`, `writeUTF(boardingStop)`, `writeUTF(validityDate ISO-8601)`, `writeDouble(amountPaid)`. This is length-prefixed for UTF strings rather than fixed-size fields; readers must use exactly the same order and types. Add a format version/magic header for a production file.

## A1.2 Pseudocode (text file)
### ADD-PASS
```text
ALGORITHM ADD-PASS(record, path)
    VALIDATE all required fields and numeric/date values
    OPEN BufferedWriter using UTF-8 with CREATE and APPEND
    WRITE record using the agreed escaping/encoding format
    WRITE newline
    FLUSH and CLOSE writer
    REPORT success
END ALGORITHM
```
`BufferedWriter` reduces small write calls; `FileWriter` is a convenient character writer but its default charset is platform-dependent unless explicitly configured. Prefer `Files.newBufferedWriter(path, UTF_8, CREATE, APPEND)`.

### FIND-PASS
```text
ALGORITHM FIND-PASS(passId, path)
    IF path does not exist THEN REPORT missing file; RETURN NOT-FOUND
    OPEN BufferedReader for UTF-8
    FOR EACH line
        PARSE line using the defined format
        IF parsing fails OR field count is wrong THEN record line number and CONTINUE
        IF parsed pass ID equals passId THEN CLOSE reader; RETURN record
    END FOR
    CLOSE reader
    RETURN NOT-FOUND
END ALGORITHM
```

### LIST-BY-ROUTE
```text
ALGORITHM LIST-BY-ROUTE(route, path)
    IF path does not exist THEN REPORT missing file; RETURN empty list
    OPEN BufferedReader for UTF-8
    FOR EACH line
        PARSE line
        IF damaged THEN report line number; CONTINUE
        IF record.routeNo equals route THEN DISPLAY record
    END FOR
    CLOSE reader
END ALGORITHM
```
Use `BufferedReader` for efficient line-based reading and `BufferedWriter` for appending records. `FileInputStream`/`FileOutputStream` are byte streams, suitable for binary data or explicit byte processing, not the most convenient choice for delimited text.

## A1.3 Damaged data
- Damaged line: validate field count and each field's type/date; skip malformed rows, record the line number and continue. Do not silently treat a damaged row as a valid record.
- Missing file: distinguish `NoSuchFileException` from other I/O errors; report it and either initialize a new store for an add operation or return an empty/not-found result for a read operation.
- Interrupted write: write a temporary file and atomically rename it for whole-file rewrites; for append-only storage, validate the last line on startup and use a journal/checksum or database transaction for stronger guarantees. A newline alone cannot guarantee that a power-failed append is complete.

## Alternative: RandomAccessFile with fixed-length records
A direct seek is O(1) only when the record index is already known. Finding a pass by ID still needs O(n) scanning unless there is an ID-to-offset index. If record `i` is known, seek to `headerSize + i × recordSize` and read that record in O(1). Fixed-size fields simplify seeking but waste space when values are shorter than their allocated capacity; long values need a defined maximum/truncation policy. A text-file ID scan is O(n), with O(1) extra working space.
