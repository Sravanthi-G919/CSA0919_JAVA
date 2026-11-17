# Task A5 – Design for Correctness and Safety

## A5.1 Transaction pseudocode
```text
ALGORITHM TRANSFER(fromWallet, toWallet, amount)
    OPEN JDBC connection
    SET autoCommit = false
    TRY
        UPDATE wallet SET balance = balance - amount
            WHERE wallet_id = fromWallet AND balance >= amount
        IF updated row count is not 1 THEN RAISE error
        UPDATE wallet SET balance = balance + amount WHERE wallet_id = toWallet
        IF updated row count is not 1 THEN RAISE error
        COMMIT
    CATCH any error
        ROLLBACK
        REPORT failure
    FINALLY
        RESTORE autoCommit if connection will be reused
        CLOSE statements and connection
END ALGORITHM
```
Validate positive amount and use appropriate isolation/locking. The transaction ensures both changes succeed together or neither is committed.

## A5.2 SQL injection
Concatenating untrusted input into SQL lets input change the SQL expression's structure. For example, an input containing a quote and an always-true condition can bypass a poorly constructed predicate. Use parameter binding:
```java
String sql = "SELECT * FROM users WHERE id = ? AND pwd = ?";
try (PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, id);
    ps.setString(2, pwd);
    try (java.sql.ResultSet rs = ps.executeQuery()) {
        return rs.next();
    }
}
```
Passwords should be stored as salted password hashes and verified with a password-hashing library; this snippet demonstrates parameterization only.

## Alternative: independent auto-commit statements
If the debit commits and the program fails before the credit, money is lost; if credit runs without the debit, money can be created. Two independent updates may have marginally less transaction-management overhead but violate atomicity. One transaction adds commit/rollback coordination but protects correctness; actual timing depends on the database and workload.
