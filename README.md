This project uses [Gradle](https://gradle.org) through the "Gradlew Wrapper" (this means that you don't need to
install) Gradle separately as its build tool and requires a Java
Development Kit version 21 If using IntelliJ IDEA, new versions of the JDK can be installed without leaving the IDE.
Open the settings pane by selecting "IntelliJ IDEA -> Settings" on MacOS and "File -> Settings" on Windows. Then
on the left side of the settings pane expand "Build, Execution, Deployment" -> "Build Tools" -> "Gradle". Then in
the pane on the right near the bottom there is a dropdown menu labeled "Gradle JVM". Click on the dropdown, then on
"+ Add SDK" -> "Download JDK". In the new popup select 20 for version and then for version select
"Eclipse Temurin (AdoptOpenJDK HotSpot) 20.0.2". If you have a Mac with Apple Silicon select
"Eclipse Temurin (AdoptOpenJDK HotSpot) 20.0.2 aarch64".

The classes that satisfy the code implementation details are "UserPhoto, MakeSmallerFilter, BlackAndWhiteFilter, Album, and the interface Photo They are found by going through src > java > photoeditor.photoeditorFX > HelloApplication

to run the application go to src > java > photoeditor.photoeditorFX > HelloApplication then click the play button. The project will
compile and then start the application.