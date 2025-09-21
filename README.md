# Tic Tac Toe Game

![GitHub release (latest by date)](https://img.shields.io/github/v/release/Sikandar-irfan/Java-project)
![GitHub](https://img.shields.io/github/license/Sikandar-irfan/Java-project)
![Platform](https://img.shields.io/badge/platform-Windows%20%7C%20Linux%20%7C%20macOS%20%7C%20Android-blue)

## Project Title
**Tic Tac Toe** - Multi-Platform Game

## Description
A simple GUI-based Tic Tac Toe game implemented in Java using Swing for desktop platforms and native Android UI for mobile. Players can choose to play against each other or against a computer opponent. This project showcases basic game logic, user interface design, and the use of algorithms for computer gameplay.

## Features
- **Player vs Player** or **Player vs Computer** modes
- Score tracking for both players
- Reset game functionality
- Simple and intuitive GUI
- **Minimax algorithm** for intelligent computer AI
- **Cross-platform support**: Windows, Linux, macOS, Android
- **Multiple distribution formats**: JAR, EXE, AppImage, APK

## Downloads & Installation

### 🖥️ Desktop (Windows, Linux, macOS)

#### Option 1: Pre-built Releases (Recommended)
Download the latest release from the [Releases page](https://github.com/Sikandar-irfan/Java-project/releases):

- **Windows**: Download `TicTacToe-x.x.x-Windows.exe` - Double-click to run
- **Linux**: Download `TicTacToe-x.x.x-Linux.AppImage` - Make executable and run
- **macOS**: Download `TicTacToe-x.x.x-macOS.app` - Drag to Applications folder
- **Cross-platform**: Download `TicTacToe-x.x.x.jar` - Requires Java 17+

#### Option 2: Run JAR file
```bash
# Requires Java 17 or higher
java -jar TicTacToe-1.0.0.jar
```

### 📱 Android

1. Download `TicTacToe-Android.apk` from the [Releases page](https://github.com/Sikandar-irfan/Java-project/releases)
2. Enable "Install from unknown sources" in your Android settings
3. Install the APK file
4. Launch the app from your app drawer

## Build from Source

### Prerequisites
- **Java 17** or higher
- **Maven 3.6+** (for desktop version)
- **Android Studio** (for Android version)

### Desktop Version

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Sikandar-irfan/Java-Project.git
   cd Java-Project/TicTacToe
   ```

2. **Build executable JAR**:
   ```bash
   mvn clean package
   ```

3. **Run the game**:
   ```bash
   java -jar target/TicTacToe-1.0.0.jar
   ```

#### Platform-specific builds:

**Windows EXE**:
```bash
mvn clean package  # Creates .exe file using Launch4j
```

**Linux AppImage**:
```bash
mvn clean package -P linux
```

**Windows MSI Installer**:
```bash
mvn clean package -P windows
```

### Android Version

1. **Open Android Studio**
2. **Import project** from `android/` directory
3. **Build APK**: Build → Build Bundle(s) / APK(s) → Build APK(s)
4. **Install on device** or emulator

### Automated Builds

This project uses GitHub Actions for automated builds. On every release tag, it automatically:

- Builds JAR file for cross-platform use
- Creates Windows EXE executable
- Generates Linux AppImage
- Builds macOS app bundle
- Compiles Android APK
- Publishes all artifacts to GitHub Releases

## Usage

### Desktop Version
1. **Launch the game** using one of the installation methods above
2. **Select game mode**: Choose between "Player vs Player" or "Player vs Computer"
3. **Enter player names** (for Player vs Player mode)
4. **Play the game**: Click on the grid to place your X or O
5. **Track scores**: The game keeps track of wins for each player
6. **Reset or change mode** using the buttons

### Android Version
1. **Install and launch** the app
2. **Select game mode** from the dialog
3. **Tap the grid** to make your moves
4. **Use buttons** to reset game or change mode

## Game Rules
- Players take turns placing X and O on a 3×3 grid
- First player to get three in a row (horizontally, vertically, or diagonally) wins
- If the grid is full with no winner, it's a draw
- In computer mode, the AI uses the Minimax algorithm for optimal play

## Development

### Project Structure
```
Java-Project/
├── TicTacToe/                 # Desktop Java Swing version
│   ├── src/main/java/         # Java source code
│   ├── src/test/java/         # Unit tests
│   └── pom.xml               # Maven build configuration
├── android/                   # Android version
│   ├── src/main/java/         # Android Java source
│   ├── src/main/res/          # Android resources
│   └── build.gradle          # Android build configuration
├── .github/workflows/         # CI/CD pipelines
│   ├── build-releases.yml     # Multi-platform builds
│   └── build-android.yml      # Android APK builds
└── README.md                 # This file
```

### Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue for any suggestions or improvements.

**Development setup**:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

For contributions, you can contact:
- **Sikandar Irfan**: sikandarirfan150162@gmail.com
- **Srihari**: srihari4989@gmail.com
- **Vinay Shivananda Jumnal**: vinayjumnal24@gmail.com

### Running Tests
```bash
cd TicTacToe
mvn test
```

## Technical Details

### Desktop Version
- **Language**: Java 17
- **UI Framework**: Swing
- **Build Tool**: Maven
- **AI Algorithm**: Minimax with Alpha-Beta pruning
- **Packaging**: Launch4j (Windows), jpackage (Linux/macOS)

### Android Version
- **Language**: Java
- **UI Framework**: Android SDK
- **Build Tool**: Gradle
- **Target SDK**: 34 (Android 14)
- **Minimum SDK**: 21 (Android 5.0)

## License
This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## Contact Information
For questions or feedback, please reach out to:
- **Sikandar Irfan**
- **Email**: sikandarirfan150162@gmail.com
- **GitHub**: [@Sikandar-irfan](https://github.com/Sikandar-irfan)

## Changelog

### Version 1.0.0 (Latest)
- Initial release
- Cross-platform desktop support (Windows, Linux, macOS)
- Android support
- Player vs Player and Player vs Computer modes
- Minimax AI algorithm
- Score tracking
- Automated builds and releases

---

## Support

If you find this project helpful, please consider:
- ⭐ **Starring** the repository
- 🐛 **Reporting bugs** via Issues
- 💡 **Suggesting features** via Issues
- 🤝 **Contributing** via Pull Requests

## Release Status

![Build Status](https://github.com/Sikandar-irfan/Java-project/workflows/Build%20and%20Release/badge.svg)
![Android Build](https://github.com/Sikandar-irfan/Java-project/workflows/Build%20Android%20APK/badge.svg)

For the latest releases and pre-built binaries, visit: [Releases Page](https://github.com/Sikandar-irfan/Java-project/releases)
