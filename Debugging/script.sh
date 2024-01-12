#!/bin/bash
# Script name: bisect-script.sh

# This script is used for automating the git bisect process.
# It compiles the project using Ant, runs tests, and determines
# if the current commit is good or bad based on test results.

# Step 1: Clean the project
# This removes any previous build artifacts to ensure a clean state.
ant clean

# Check if 'ant clean' was successful. Exit with an error if it fails.
if [ $? -ne 0 ]; then
    echo "Ant clean failed."
    exit 1
fi

# Step 2: Compile the project
# This compiles the source code of the project.
ant compile

# Check if 'ant compile' was successful. Exit with an error if it fails.
if [ $? -ne 0 ]; then
    echo "Ant compile failed."
    exit 1
fi

# Step 3: Run the tests
# This runs the project's tests. Assuming 'ant' returns a non-zero status on failure.
ant -lib lib/ test

# Step 4: Determine the result of the tests
# If the tests pass (exit status 0), the commit is good. Otherwise, it's bad.
if [ $? -eq 0 ]; then
    echo "Tests passed. This is a good commit."
    exit 0
else
    echo "Tests failed. This is a bad commit."
    exit 1
fi
