name := "test"

version := "1.0-SNAPSHOT"

autoScalaLibrary := false

unmanagedSourceDirectories in Compile := Seq(baseDirectory.value / "src/main")
unmanagedSourceDirectories in Test := Seq(baseDirectory.value / "src/test")

// http://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12"

libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % "test"

//docs https://github.com/sbt/junit-interface
testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")