# Do not forget to run
# chmod +x compile_and_run.sh


#!/bin/bash
echo "Compiling Java source files..."

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]; then
  mkdir bin
fi

# Compile all Java files in the src directory
javac -d bin src/*.java

echo "Compilation complete. Check the bin directory for class files."
java -cp bin src.WordLadderGUI
