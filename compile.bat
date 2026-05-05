@echo off
REM ============================================================
REM compile.bat — Compiles all Java source files
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

REM Create output directory
if not exist out mkdir out

echo.
echo  Compiling...

javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls -d out ^
    src\model\Process.java ^
    src\scheduler\BaseScheduler.java ^
    src\scheduler\SJFScheduler.java ^
    src\scheduler\PriorityScheduler.java ^
    src\ui\UITheme.java ^
    src\ui\GanttChart.java ^
    src\utils\Validator.java ^
    src\app\App.java ^
    src\app\components\ButtonComponent.java ^
    src\app\components\LabelComponent.java ^
    src\app\views\MainView.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo  Compilation failed! Check the errors above.
    pause
    exit /b 1
)

echo  Compiled successfully!
echo  Now run: run.bat
echo.
pause
