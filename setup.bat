@echo off
REM ============================================================
REM setup.bat — Downloads the JavaFX SDK so you don't need Maven
REM Run this ONCE before compiling.
REM ============================================================

echo.
echo  [1/3] Creating lib folder...
if not exist lib mkdir lib

echo  [2/3] Downloading JavaFX SDK 21.0.2 (this may take a minute)...
powershell -Command "Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_windows-x64_bin-sdk.zip' -OutFile 'lib\javafx-sdk.zip'"

if not exist lib\javafx-sdk.zip (
    echo.
    echo  ERROR: Download failed. Check your internet connection.
    echo  You can also download manually from: https://gluonhq.com/products/javafx/
    echo  Extract it into the lib\ folder.
    pause
    exit /b 1
)

echo  [3/3] Extracting...
powershell -Command "Expand-Archive -Path 'lib\javafx-sdk.zip' -DestinationPath 'lib' -Force"
del lib\javafx-sdk.zip

echo.
echo  Done! JavaFX SDK is ready in lib\javafx-sdk-21.0.2
echo  Now run: compile.bat
echo.
pause
