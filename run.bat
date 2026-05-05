@echo off
REM ============================================================
REM run.bat — Runs the compiled JavaFX application
REM ============================================================

set JAVAFX_LIB=lib\javafx-sdk-21.0.2\lib

REM Check if compiled classes exist
if not exist out\app\App.class (
    echo.
    echo  ERROR: No compiled classes found. Run compile.bat first!
    echo.
    pause
    exit /b 1
)

echo.
echo  Starting JavaFX app...
echo.

java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls -cp out app.App
