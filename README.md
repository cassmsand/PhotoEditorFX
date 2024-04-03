This project uses [Gradle](https://gradle.org) through the "Gradlew Wrapper" (this means that you don't need to
install) Gradle separately as its build tool and requires a Java
Development Kit version 21 If using IntelliJ IDEA, new versions of the JDK can be installed without leaving the IDE.
Open the settings pane by selecting "IntelliJ IDEA -> Settings" on MacOS and "File -> Settings" on Windows. Then
on the left side of the settings pane expand "Build, Execution, Deployment" -> "Build Tools" -> "Gradle". Then in
the pane on the right near the bottom there is a dropdown menu labeled "Gradle JVM". Click on the dropdown, then on
"+ Add SDK" -> "Download JDK". In the new popup select 20 for version and then for version select
"Eclipse Temurin (AdoptOpenJDK HotSpot) 20.0.2". If you have a Mac with Apple Silicon select
"Eclipse Temurin (AdoptOpenJDK HotSpot) 20.0.2 aarch64".


How is the code structured? Where are the classes that satisfy the “Code Implementation” requirements?

The classes that satisfy the code implementation details are UserPhoto, MakeSmallerFilter, BlackAndWhiteFilter, Album,  
the interface class Photo, and the abstract class Filter.
They are found by going opening the following folders: src > main > java > photoeditor.photoeditorFX.
The classes to run the .fxml file are HelloApplication and HelloController. The .fxml named "PhotoEditorGUI.fxml" can 
be found by going into src > main > resources > photoeditor.photoeditorFX > PhotoEditorGUI.fxml".


How does one run the application?

To run the application, go to src > main > java > photoeditor.photoeditorFX > HelloApplication then click the run button.
The project will compile and then start the application.

Each functional requirement.
1. Upload photos to Editor. Our application has two ways for uploading photos to the editor: drag-and-dropping the photo directly into the editor and a File Explorer.

2. Resize photos(pixel size). Our application offers the ability to change the size of the photo(0.0625x, .125x, .25x, .5x, 1x, 2x, 3x, 4x, 5x) or specifically the height or width of the photo using a numerical slider.

3. Recolor Photos. Our application offers a variety of ways to recolor photos. Having the ability to adjust the photo's brightness, hue, contrast, and saturation, as well as apply various filters (Black&White, Grayscale, Red, Blue, Green).

4. Save Past Edited photos.
5. Delete Past Edited Photos.

6. Save photo to device. Our application has a simple to use save button found under the file tab. It also has a clear workspace option, which clears the current photo in the editor, that will prompt if you would like to save first. There is also a confirmation that the photo has been saved.

Each non-functional requirement. 

1. Quality. Our application ensures quality as it works as intended, has variety of options for photo editing (filters, color changes, size changes,saving edited photo)

2. Extensibility. Our application shows Extensibility by our abstract Filter class that allows us to create new filters as needed. We also have well documented comments for methods/fields that may not make sense from a glance which should make debugging errorss a simpler process if needed in the future.

3. Performance/response time. Our application shows great performance/response time with user actions and processes edits efficiently. Saving photos/loading in photos takes no time to work.

4. Reliability. Our application is shown to be reliable by every option in our photo editing application working as intended and extensive bug testing/bug fixing.
