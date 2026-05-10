@echo off
REM ============================================================
REM build_run.bat — Compiles all Java files and runs the app
REM Uses the JavaFX SDK downloaded by setup.bat
REM ============================================================

set JAVAFX_LIB=lib\javafx-sdk-21.0.2\lib

REM Check if JavaFX SDK exists
if not exist "%JAVAFX_LIB%" (
    echo.
    echo  ERROR: JavaFX SDK not found. Run setup.bat first!
    echo.
    pause
    exit /b 1
)

REM Clean stale classes
if exist out rmdir /s /q out
mkdir out

echo.
echo  Compiling...

dir /s /B src\*.java > sources.txt
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls -d out @sources.txt
set BUILD_STATUS=%ERRORLEVEL%
del sources.txt

if %BUILD_STATUS% NEQ 0 (
    echo.
    echo  Compilation failed! Check the errors above.
    pause
    exit /b 1
)

echo  Compiled successfully!
echo.
echo  Starting JavaFX app...
echo.

java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls -cp out src.ui.App
