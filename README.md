## Compiling and Executing

Compile and run all test cases with the following command.

```bash
javac -d class *.java && java -cp class TestRunner class
```

Compile all classes with the following command.
This will not run anything, but classes can be run at a later time with the
`java <class>` command.

```bash
javac -d class *.java
```

## Running

Run the test cases with the following command.

```bash
java -cp class TestRunner class
```

## Reproducibility

If `nix` is installed, run `nix-shell` for a reproducible environment.

