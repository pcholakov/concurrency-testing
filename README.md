# Concurrency testing with [`jcstress`](https://wiki.openjdk.java.net/display/CodeTools/jcstress)

Build the tests:

```
mvn package
```

Run the tests:

```
java -jar target/jcstress.jar -m quick
```

You should see detected instances of the race conditions such as:

```
  [FAILED] demo.ReporterBrokenStressTest
    (JVM args: [-server])
  Observed state   Occurrences   Expectation  Interpretation
               1     2,722,117    ACCEPTABLE
               2       648,613     FORBIDDEN  No default case provided, assume FORBIDDEN
```

The directory `results` will contain generated HTML reports summarising the observed outcomes.
