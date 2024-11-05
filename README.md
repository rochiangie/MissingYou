MissingYou is a simple Android app that keeps track of button clicks, counting the number of times a button has been pressed and displaying the total. The count resets daily to encourage regular use.

Features
Button Counter: Increments the count each time the button is pressed.
Daily Reset: Automatically resets the count each day.
Persistent Storage: Saves the current count and date, even if the app is closed.
Installation
Clone the repository:
bash
Copy code
git clone https://github.com/rochiangie/MissingYou.git
Open the project in Android Studio.
Build and run the app on an Android device or emulator.
Usage
Press the button to increase the count.
The count will reset daily at midnight.
Code Structure
MainActivity: Contains the core logic for counting, saving, and resetting.
SharedPreferences: Used for storing count data persistently.
Technologies
Android: Built with Kotlin.
SharedPreferences: For data persistence.
SimpleDateFormat: For date handling.
License
This project is licensed under the MIT License.