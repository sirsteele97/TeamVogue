To setup:
    1) Import Project to IDE of choice, I am using IntelliJ. If given the option import it as a Maven Project.
    2) Ensure Node.JS and npm is installed, here's a link if you need to install them https://nodejs.org/en/download/
    3) Right Click on the pom.xml, this holds all dependencies, so If you wish to add any add the xml code to this document
        after clicking run the maven import/reimport command to install all dependencies.
    4) Run the program using application as the configuration, Application creates a local server which can be accessed
        by going into http://localhost:8080/

            An issue I had was the application will crash if it is Https instead of HTTP since the token is encrypted
            and not read as a normal string for the route.

    Extra info)
        https://vaadin.com/components contains API info for Vaadin, click on a component you want and it will give example
        code, API info and versions of the component.

        src/main/java/com/packagename/myapp holds the initial java code, MainView is a GUI View, views can be managed through
        hyperlink like routing. GreetService is a basic class that holds the sample methods used by the Main View

        Under Frontend/Styles holds css scripts to change the theme.

        Dont look under node_modules, it scares me how much bullshit Node.JS has to install so our simple app can run.

CLONE THIS---------------------

https://github.com/rohit7209/AccessRemoteMySQLDB

-clone this and import the .jar file.
-to import a jar into a intellij project click on file (top right) the click all these buttons at the same time:
CTRL + SHIFT + ALT + S
-got to dependencies in the tab and click the + at the right side of the screen, select Jar and select the file and your
good to go!!