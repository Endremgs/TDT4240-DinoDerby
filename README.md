# TDT4240-Progark
Repository for DinoDerby

# How to run the project
To play on a physical android device through android studio.
1. Make sure your device has Android API 30 or later.
2. Make sure developer options are turned on on the device. The device can be connected to
android studio either through USB-debugging or wireless debugging.
3. Build the project in android studio.
4. Run the application on the connected device

# code structure

- **android/src/com/mygdx/game** - contains android launcher and firebase interface
- **core/src/com/mygdx/game** - The game logic, seperated into multiple packages, as well as the main game class
  - **views** - contains the different screens of the game
  - **util** - misc. utils used by the game
  - **factories** - factory classes for creating game objects
  - **entity** - contains the components and systems of the ECS. The engine is located within the playScreen in views
