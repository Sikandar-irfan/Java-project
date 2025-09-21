@echo off
echo ========================================
echo  Tic Tac Toe Game - GitHub Upload Script
echo ========================================
echo.

cd /d "C:\Users\Sikandar Irfan\OneDrive\Desktop\java\Java-project"

echo Current directory: %CD%
echo.

echo Step 1: Adding all files to git...
git add .

echo.
echo Step 2: Committing changes...
git commit -m "Add multi-platform build configuration and release automation

- Enhanced pom.xml with plugins for Windows EXE, Linux AppImage, macOS builds
- Added GitHub Actions workflows for automated CI/CD
- Created Android version with native UI
- Updated README with comprehensive installation instructions
- Added release automation for all platforms"

echo.
echo Step 3: Pushing to GitHub...
git push origin main

echo.
echo Step 4: Creating release tag...
git tag v1.0.0
git push origin v1.0.0

echo.
echo ========================================
echo SUCCESS! Your repository has been updated.
echo.
echo Next steps:
echo 1. Go to: https://github.com/Sikandar-irfan/Java-project
echo 2. Check the "Actions" tab to see builds in progress
echo 3. Once builds complete, check "Releases" for your files
echo.
echo Platform files that will be created:
echo - TicTacToe-1.0.0.jar (Cross-platform)
echo - TicTacToe-1.0.0.exe (Windows)
echo - TicTacToe Linux AppImage
echo - TicTacToe macOS app
echo - TicTacToe-Android.apk
echo ========================================
pause