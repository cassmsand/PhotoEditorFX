This project uses [Gradle](https://gradle.org) through the "Gradlew Wrapper" (this means that you don't need to
install) Gradle separately as its build tool and requires a Java
Development Kit version 20 If using IntelliJ IDEA, new versions of the JDK can be installed without leaving the IDE.
Open the settings pane by selecting "IntelliJ IDEA -> Settings" on MacOS and "File -> Settings" on Windows. Then
on the left side of the settings pane expand "Build, Execution, Deployment" -> "Build Tools" -> "Gradle". Then in
the pane on the right near the bottom there is a dropdown menu labeled "Gradle JVM". Click on the dropdown, then on
"+ Add SDK" -> "Download JDK". In the new popup select 20 for version and then select
"Eclipse Temurin (AdoptOpenJDK HotSpot) 20.0.2". If you have a Mac with Apple Silicon select
"Eclipse Temurin (AdoptOpenJDK HotSpot) 20.0.2 aarch64".


How is the code structured? Where are the classes that satisfy the “Code Implementation” requirements?

The code is structured with the main class found in the "PhotoEditorApplication" class.
The controller for the GUI application can be found under "PhotoEditorController" class.
When a photo file is uploaded into the application, the controller class creates a "UserPhoto" class object which implements the interface class "Photo".
The photo object and imageView of the application can be modified via methods within the controller class and through extensions of the abstract class "Filter".
The Filter classes are called: "BlackAndWhiteFilter", "GrayscaleFilter", "RedFilter", "BlueFilter", and "GreenFilter".

All classes mentioned above are found by opening the following folders: src > main > java > photoeditor.photoeditorFX.
The classes that run the .fxml file are "PhotoEditorApplication" and "PhotoEditorController". 
The .fxml named "PhotoEditorGUI.fxml" can be found by going into: src > main > resources > photoeditor.photoeditorFX > PhotoEditorGUI.fxml.


How does one run the application?

To run the application, go to src > main > java > photoeditor.photoeditorFX > PhotoEditorApplication then click the run button.
The project will compile and then start the application.


What are the functional requirements this application implements?

1. Upload photos to Editor.  
Our application has two ways for uploading photos into the photo editor application and accepts .png, jpeg, and .gif files.
The first way is by drag and dropping the photo file directly into the application window. This area is labeled "Drag and drop photo here...".
The second way is by selecting the photo file through the "Open New File" option under the "File" tab.
The option will open a File Explorer window where the user may search their device for the desired photo file.

2. Resize photos.
Our application offers the ability to change the size of an uploaded photo found under the "Resize" tab.
It can directly resize the photo, 0.0625x, .125x, .25x, .5x, 1x, 2x, 3x, 4x, 5x, times the original size. 
The photo's height and/or width sizes can also be adjusted using the height and width sliders.

3. Recolor Photos. 
Our application offers a variety of ways to recolor uploaded photos which can be found under the "Adjust Color" tab.
The recolor options include brightness, hue, contrast, and saturation affects that can be manipulated through sliders.

4. Apply Filters. 
Our application offers the ability to apply filters to the uploaded photo through the "Apply Filter" tab.
These filter options include black and white, grayscale, red, blue, and green.
There is also an original filter option to remove an applied filter.

5. Clear Workspace. 
Our application has a "Clear Workspace" option that can be found under the "File" tab.
When this tab is clicked and a photo has been uploaded to the workspace, the user is asked if they would like to save their edited photo before clearing.
Next, the application will clear out the current photo file from the workspace.

6. Save photo to device. 
Our application has a "Save" option under the "File" tab.
The save dialog window will allow the user to select the file name, save location, and the file format(.jpg, .png, .gif).
If the save is successfully completed, there is a message pop up window that states the edited photo has been saved.


What are the non-functional requirements this application implements? 

1. Quality.
Our application ensures quality as it works as intended. The display options are clear and concise.
The application also offers a variety of options for photo editing including filters, recoloring options, and size changes.
The uploading and downloading abilities operate as designed.

2. Extensibility.
One way the application illustrates Extensibility is through its utilization of the abstract class "Filter". 
The Filter class offers us a starting point to create new filters to the "Apply Filter" tab as needed.
We also have well-documented comments for classes, methods, and fields which will make debugging errors easier in the future.
There is also an album class for storing past edited photos that could be implemented in updated versions of the application.

3. Performance/response time.
Our application demonstrates great performance and response time.
User interactions within the application are processed efficiently and the photo image is adjusted accordingly.
In addition, uploading and saving a photo is quick and easy.

4. Reliability.
Our application displays excellent reliability through the filter, recolor, and size options performing as intended.
The upload and download options are reliable because of the application's accepted file types. 
The application has undergone extensive bug testing/fixing.
By sectioning the code into different classes and methods, it is easy to recognize and fix potential problems should they arise.
