# README

Team members:

Name - Harsh Patel, github - https://github.com/harsh0596

Name - Harsha Dodda, github - https://github.com/harshadodda

Name - Rohit Nambiar, github - https://github.com/rohitn212

----------------------------------- 

To set this project up, first download the folder. In this folder you will see the build.gradle and application files.
To run the gradle build, go to the root folder of the application, the directory that contains the build.gradle file
and run the following commands.

# Gradle:

to build, use the command: ```gradle build```

to run, use the command: ```gradle run```

The inital repository is: https://github.com/jcowgill/java-minesweeper

------------------------------------

Examples of mutations we used:

1.Static Modifier Insertion(JSI) - adds the static modifier to change instance variables to class variables. Opposite of JSD 

JSI Mutation Example: 

![Scheme](/Images/JSI.png)

JSI Code:

![Scheme](/Images/JSIcode.png)

2.Static Modifier Deletion(JSD) - removes the static modifier to change class variables to instance variables. Opposite of JSI

JSD Mutation Example:

![Scheme](/Images/JSD.png)

JSD Code:

![Scheme](/Images/JSDcode.png)

3.Access Modifier Change(AMC) - changes access level for instance variables and methods to other access variables.

AMC Mutation Example:

![Scheme](/Images/AMC.png)

AMC Code:

![Scheme](/Images/AMCcode.png)

4.Overloading Method Deletion(OMD) - deletes overloading method declarations, one at a time in turn.

OMD Mutation Example:

![Scheme](/Images/OMD.png)

OMD Code:

![Scheme](/Images/OMDcode.png)

-------------------------------------

Configuration File Setup:

The configuration file is set up in the following manner:

mutationName,methodNameinTestFile,classToTest

![Scheme](/Images/configFile.png)

-------------------------------------

Proceedure:

Step 1: Parse the config file

Step 2: Make a an array of n threads where n is the number of mutations 

Step 3: Run the pre mutation tests

Step 4: Mutate the code

Step 5: Run the post mutation tests

Step 6: Compare the instrum list for pre mutation and post mutation tests and show changes
