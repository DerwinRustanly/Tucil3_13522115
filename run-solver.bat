@echo off
echo Compiling Java source files...

REM Create bin directory if it doesn't exist
if not exist "bin" mkdir bin

REM Compile all Java files in the src directory
javac -d bin src\*.java

echo Compilation complete. Check the bin directory for class files.
java -cp bin src.WordLadderGUI
