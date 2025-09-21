# Step-by-Step Guide to Create Releases

This guide will help you create releases for your Tic Tac Toe game in multiple formats.

## Quick Start (Automated Releases)

### Option 1: Using GitHub Actions (Recommended)

1. **Push your changes to GitHub**:
   ```bash
   cd "C:\Users\Sikandar Irfan\OneDrive\Desktop\java\Java-project"
   git add .
   git commit -m "Add multi-platform build configuration"
   git push origin main
   ```

2. **Create a release tag**:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

3. **Wait for builds to complete**: GitHub Actions will automatically build all platform versions and create a release.

### Option 2: Manual Release Creation

1. **Go to your GitHub repository**
2. **Click "Releases"** → **"Create a new release"**
3. **Choose a tag**: `v1.0.0`
4. **Release title**: `Tic Tac Toe Game v1.0.0`
5. **Description**: Copy from the template below
6. **Click "Publish release"**

The GitHub Actions will automatically attach the built files to your release.

## Manual Building (Local Development)

### Prerequisites Installation

1. **Install Java 17**:
   - Download from: https://adoptium.net/
   - Install and set JAVA_HOME environment variable

2. **Install Maven**:
   - Download from: https://maven.apache.org/download.cgi
   - Extract and add to PATH environment variable

3. **Install Android Studio** (for Android APK):
   - Download from: https://developer.android.com/studio

### Building Different Formats

#### 1. Cross-Platform JAR
```bash
cd TicTacToe
mvn clean package
# Output: target/TicTacToe-1.0.0.jar
```

#### 2. Windows EXE
```bash
cd TicTacToe
mvn clean package
# Output: target/TicTacToe-1.0.0.exe (using Launch4j)
```

#### 3. Linux AppImage
```bash
cd TicTacToe
mvn clean package -P linux
# Output: target/dist/TicTacToe
```

#### 4. Windows MSI Installer
```bash
cd TicTacToe
mvn clean package -P windows
# Output: target/dist/TicTacToe-1.0.0.msi
```

#### 5. Android APK
1. Open Android Studio
2. Import the `android/` directory as a project
3. Build → Build Bundle(s) / APK(s) → Build APK(s)
4. Output: `android/build/outputs/apk/debug/android-debug.apk`

## Release Template

When creating a manual release, use this description template:

```markdown
## 🎮 Tic Tac Toe Game v1.0.0

A cross-platform Tic Tac Toe game with intelligent AI opponent.

### 📦 Downloads

Choose the version for your platform:

| Platform | File | Requirements |
|----------|------|--------------|
| 🪟 **Windows** | `TicTacToe-1.0.0-Windows.exe` | Windows 10+ |
| 🐧 **Linux** | `TicTacToe-1.0.0-Linux.AppImage` | Any Linux distribution |
| 🍎 **macOS** | `TicTacToe-1.0.0-macOS.app` | macOS 10.14+ |
| ☕ **Cross-platform** | `TicTacToe-1.0.0.jar` | Java 17+ |
| 📱 **Android** | `TicTacToe-Android.apk` | Android 5.0+ |

### ✨ Features

- 🎯 Player vs Player and Player vs Computer modes
- 🧠 Smart AI using Minimax algorithm
- 📊 Score tracking
- 🔄 Game reset and mode switching
- 🎨 Clean, intuitive interface

### 🚀 Installation

**Windows**: Download `.exe` and double-click to run
**Linux**: Download `.AppImage`, make executable: `chmod +x TicTacToe-*.AppImage`
**macOS**: Download `.app` and drag to Applications folder
**Cross-platform**: Download `.jar` and run: `java -jar TicTacToe-1.0.0.jar`
**Android**: Download `.apk` and install (enable unknown sources)

### 🐛 Bug Reports

Found an issue? Please report it [here](https://github.com/Sikandar-irfan/Java-project/issues).
```

## Troubleshooting

### Common Issues

1. **"Java not found"**:
   - Install Java 17+ from https://adoptium.net/
   - Set JAVA_HOME environment variable

2. **"Maven not found"**:
   - Install Maven from https://maven.apache.org/
   - Add Maven bin directory to PATH

3. **Build fails**:
   - Check Java version: `java -version`
   - Check Maven version: `mvn -version`
   - Clean and retry: `mvn clean package`

4. **Android build issues**:
   - Update Android Studio to latest version
   - Install required SDK platforms
   - Sync project with Gradle

### GitHub Actions Not Running

1. Check that workflows are enabled in your repository settings
2. Ensure you have the correct branch names in the workflow files
3. Check the Actions tab for error messages

## Next Steps

1. **Test your releases** on different platforms
2. **Update version numbers** in pom.xml and build.gradle for future releases
3. **Add screenshots** to your repository for better presentation
4. **Consider code signing** for Windows executables (requires certificate)
5. **Set up automatic dependency updates** using Dependabot

## Support

If you need help:
1. Check the [Issues](https://github.com/Sikandar-irfan/Java-project/issues) page
2. Create a new issue with detailed error messages
3. Contact: sikandarirfan150162@gmail.com