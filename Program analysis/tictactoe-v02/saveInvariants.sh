#!/bin/sh

# Runs Synoptic from the compiled JAR file, passing all command
# line argument directly to main().

java -ea -jar ./lib/synoptic/lib/synoptic.jar traces/trace-* --outputInvariantsToFile=true --noModelOutput=true -o rowGameApp-arch
