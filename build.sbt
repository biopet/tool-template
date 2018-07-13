organization := "com.github.biopet"
organizationName := "Biopet"

//TODO: Start year should reflect the tools original start year on github.com/biopet/biopet in the tools section
startYear := Some(2017)

//TODO: change name
name := "ToolTemplate"
biopetUrlName := "tool-template"

// TODO: Is it a tool?
biopetIsTool := true

//TODO: Remove this line as soon as release testing is finished.
biocondaMainGitUrl := "git@github.com/biopet/bioconda-recipes.git"

// TODO: Check if mainClass is correct
mainClass in assembly := Some(
  s"nl.biopet.tools.${name.value.toLowerCase()}.${name.value}")

developers := List(
  Developer(id = "ffinfo",
            name = "Peter van 't Hof",
            email = "pjrvanthof@gmail.com",
            url = url("https://github.com/ffinfo")),
  Developer(id = "rhpvorderman",
            name = "Ruben Vorderman",
            email = "r.h.p.vorderman@lumc.nl",
            url = url("https://github.com/rhpvorderman"))
)

crossScalaVersions := Seq("2.11.12", "2.12.5")
scalaVersion := "2.11.12"

libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.3.1"
libraryDependencies += "com.github.biopet" %% "tool-test-utils" % "0.2.2" % Test
