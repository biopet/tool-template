organization := "com.github.biopet"
//TODO: change name
name := "ToolTemplate"
biopetUrlName := "tool-template"

// TODO: Is it a tool?
biopetIsTool := true

developers := List(
  Developer(id="ffinfo", name="Peter van 't Hof", email="pjrvanthof@gmail.com", url=url("https://github.com/ffinfo")),
  Developer(id="rhpvorderman", name="Ruben Vorderman", email="r.h.p.vorderman@lumc.nl", url=url("https://github.com/rhpvorderman"))
)

scalaVersion := "2.11.11"

resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.2"
libraryDependencies += "com.github.biopet" %% "tool-test-utils" % "0.1" % Test

