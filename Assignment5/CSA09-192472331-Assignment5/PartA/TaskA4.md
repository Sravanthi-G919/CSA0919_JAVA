# Task A4 – What Should Be Serialized?

## A4.1 Fields and object graph
Mark the live `Connection` reference `transient`: it represents a runtime resource and is not a durable object. Passwords should generally not be serialized in plaintext; preferably do not keep them in the object graph, or mark the field transient and use a secure credential store. The `Hostel`, its serializable `Room` objects and serializable `Resident` objects are traversed recursively; reachable non-transient serializable fields are written. Every referenced object must be serializable or serialization can fail with `NotSerializableException`.

## A4.2 Schema changes
When a new `phoneNumber` field is added, deserializing an older stream normally gives the new field its default value (null for a reference type) if the class has a compatible `serialVersionUID`. If the UID differs, `InvalidClassException` can occur. An explicit stable `serialVersionUID` helps compatibility but does not replace migration/validation logic.

## Alternative: CSV or JSON
Text formats are inspectable and can be more portable, but may be larger and require parsing; binary Java serialization may be compact/quick for Java object graphs but is Java-specific and brittle across class changes. For n residents, save/load time is generally O(n) for either approach; memory depends on whether the full graph/document is built in memory. CSV/JSON can evolve with optional fields and explicit defaults, while Java serialization uses class-version compatibility rules. Never write passwords to an ordinary CSV/JSON file.
