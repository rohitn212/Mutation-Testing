# README for HW3 for CS474

Team members:

Name - Harsh Patel, UIN - '669601398', UID - hpate34, Undergrad

Name - Harsha Dodda, UIN - '660851227', UID - dodda2, Undergrad

Name - Rohit Nambiar, UIN - '679882752', UID - rnambi2, Undergrad

----------------------------------- 

To set this project up, first download the folder. In this folder you will see the build.sbt and application files.
To run the sbt build, go to the root folder of the application, the directory that contains the build.sbt file
and run the following command:

# SBT:

to build, use the command: ```sbt clean compile test```

to run, use the command: ```sbt run```

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

Proceedure:

Step 1:

Step 2:

Step 3:

-------------------------------------

Configuration File Setup:

The configuration file is set up in the following manner:

mutationName,methodNameinTestFile,classToTest

![Scheme](/Images/configFile.png)

-------------------------------------




