name := "test"

version := "1.0-SNAPSHOT"

autoScalaLibrary := false

unmanagedSourceDirectories in Compile := Seq(baseDirectory.value / "src/main",
    baseDirectory.value / "src/main/uk/ac/york/minesweeper/Launcher",
    baseDirectory.value / "src/test")
unmanagedSourceDirectories in Test := Seq(baseDirectory.value / "src/test")

mainClass in Compile := Some("uk.ac.york.minesweeper.Launcher")

// http://mvnrepository.com/artifact/junit/junit
libraryDependencies ++= Seq("junit" % "junit" % "4.12",
    "com.novocode" % "junit-interface" % "0.10" % "test",
    "org.javassist" % "javassist" % "3.22.0-GA")

//docs https://github.com/sbt/junit-interface
testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

//mainClass in (Compile, run) := Some("src.main.uk.ac.york.minesweeper.Launcher")