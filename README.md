Dieses File beschreibt die grundsätzlichen Informationen für das Projekt und die Entwicklungsumgebung

Um das Projekt zu verwenden einfach das Git Repository herunterladen und als Projekt in Android Studio hineinladen und das virtuelle Gerät erstellen.
Dann kann es im Debug Mode gestartet werden.

Entwickelt wurde es in Android Studio auf einem Pixel 2 XL API 31.
Es kann sein das Gradle lokal installiert/angepasst werden muss. Hierfür wurde Gradle Version: 7.0.2 Gradle Plugin 4.1.2 Gradle JDK 17.0.3.1 verwendet - Gradle muss am Pc selbst eingestellt werden bei den Settings. 

Um die SDK einzustellen Appearance&Behavior>System Settings>Android SKD --> SDK auswählen und installieren

Um Gradle einzustellen: Settings>Build Tools>Gradle und die JDK herunterladen
Dannach kann in der Projekt Structure unter Project die Gradle Version und das Plugin eingestellt werden.

Für die CSV-Datein muss die df_perceptron.csv Datei in das virtuelle Gerät geladen werden. Hierzu einfach das File auf das virtuelle Gerät ziehen.
Dann kann die Datei über den CSV Picker verwendet werden.
