# Task A2 – Don’t Read What You Don’t Need

## A2.1 Predict the cost
A 50 MB file is approximately 50 × 1,048,576 = 52,428,800 bytes (or 50,000,000 bytes if MB is decimal). A loop calling `FileInputStream.read()` once per byte makes roughly one Java read call per byte. The operating system/JVM may buffer internally, so this does not necessarily mean one physical disk operation per byte; the repeated method-call and per-byte processing overhead is still high.

## A2.2 Buffered redesign
```text
ALGORITHM COUNT-LINES(path)
    count ← 0
    OPEN BufferedReader for path
    WHILE readLine() returns a line
        count ← count + 1
    END WHILE
    CLOSE reader
    RETURN count
END ALGORITHM
```
For a byte-oriented approach, use `BufferedInputStream` or a byte array (for example 8192 bytes), scan each returned chunk for the newline byte, and count it. Buffering obtains blocks at a time and reduces calls across the Java-to-native/stream boundary. Exact physical disk calls depend on the OS and filesystem cache.

## A2.3 When buffering helps little
It may help little when the source is already memory-backed, data arrives in large chunks from an upstream buffered source, or the workload is dominated by CPU processing rather than I/O.

## Alternative: Files.readAllLines()
`Files.readAllLines()` loads all decoded lines into memory: O(file size) time and O(file size plus object overhead) memory. A buffered streaming line counter takes O(file size) time but O(buffer size) auxiliary memory. Use `readAllLines()` when the file is small and all lines are needed simultaneously; stream when files are large or memory is constrained.
