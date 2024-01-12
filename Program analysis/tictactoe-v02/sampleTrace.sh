#!/bin/bash
#
# Runs the ThreeInARowGame where the Logger class has tracing turned ON.
# Takes as input the UNIQUE_ID for the new sampled trace.
# Saves the sampled trace as "traces/trace-UNIQUE_ID".

java -Dlogger.Logger.tracing=true -cp bin RowGameApp > "traces/trace-$1"
