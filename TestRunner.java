// TestRunner.java
// Preston Hager

import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

// Define the @Test annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
}

// Create a Test class to run the tests
public class TestRunner {
    // Method to run all test methods in the specified class
    public static void run(Class<?> testClass) {
        System.out.println("Running tests in class: " + testClass.getName());

        // Get all declared methods of the test class
        Method[] methods = testClass.getDeclaredMethods();
        int passed = 0, failed = 0;

        // Loop through the methods and check for @Test annotation
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    // Invoke the test method
                    method.invoke(testClass.getDeclaredConstructor().newInstance());
                    System.out.println(method.getName() + " PASSED");
                    passed++;
                } catch (Exception e) {
                    System.out.println(method.getName() + " FAILED: " + e.getCause());
                    failed++;
                }
            }
        }
        // Summary of results
        System.out.println("\nTest results: " + passed + " passed, " + failed + " failed.");
    }

    // Assert function to check equality
    public static void assertEqual(Object expected, Object actual) {
        if (expected == null) {
            if (actual == null) return;
            throw new RuntimeException("Assertion failed: expected null but got " + actual);
        }
        if (!expected.equals(actual)) {
            throw new RuntimeException("Assertion failed: expected " + expected
                + " but got " + actual);
        }
    }

    public static void expectThrows(Class<? extends Throwable> exceptionClass, Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            if (!exceptionClass.isInstance(e)) {
                throw new RuntimeException("Expected exception of type " + exceptionClass.getName()
                    + " but got " + e.getClass().getName());
            }
            return;
        }
        throw new RuntimeException("Expected exception of type " + exceptionClass.getName());
    }

    // Utility to find all class files in a directory and subdirectories
    public static List<Class<?>> findTestClasses(String directory) {
        List<Class<?>> testClasses = new ArrayList<>();
        File dir = new File(directory);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Invalid directory: " + directory);
            return testClasses;
        }

        // Recursively find all .class files
        findClassesRecursively(dir, "", testClasses);
        return testClasses;
    }

    // Recursive method to search through directories for .class files
    private static void findClassesRecursively(File dir, String packageName, List<Class<?>> testClasses) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // Recurse into subdirectories
                findClassesRecursively(file, packageName + file.getName() + ".", testClasses);
            } else if (file.getName().endsWith(".class")) {
                // Strip the ".class" extension to get the class name
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // Load the class and add it to the list if it has @Test methods
                    Class<?> cls = Class.forName(packageName + className);
                    if (hasTestMethods(cls)) {
                        testClasses.add(cls);
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Error loading class: " + className);
                }
            }
        }
    }

    // Check if the class contains any @Test annotated methods
    private static boolean hasTestMethods(Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                return true;
            }
        }
        return false;
    }

    // Main method to find and run all test classes
    public static void main(String[] args) {
        String directory = "."; // Default to current directory
        if (args.length > 0) {
            directory = args[0]; // Use the directory specified by the user
        }

        // Find and run all test classes in the directory
        List<Class<?>> testClasses = findTestClasses(directory);
        for (Class<?> testClass : testClasses) {
            run(testClass);
            System.out.println();
            System.out.println("--------------------------------------------------");
        }

        if (testClasses.isEmpty()) {
            System.out.println("No test classes found in the directory.");
        }
    }
}

