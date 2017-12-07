organization := "com.github.biopet"
//TODO: change name
name := "ToolTemplate"
biopetUrlToolName := "tool-template"
homepage := Some(url(s"https://github.com/biopet/${biopetUrlToolName.value}"))
licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))

scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/biopet/${biopetUrlToolName.value}"),
    s"scm:git@github.com:biopet/${biopetUrlToolName.value}.git"
  )
)

developers := List(
  Developer(id="ffinfo", name="Peter van 't Hof", email="pjrvanthof@gmail.com", url=url("https://github.com/ffinfo")),
  Developer(id="rhpvorderman", name="Ruben Vorderman", email="r.h.p.vorderman@lumc.nl", url=url("https://github.com/rhpvorderman"))
)

libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.2"
libraryDependencies += "com.github.biopet" %% "tool-test-utils" % "0.1" % Test changing()

publishMavenStyle := true

scalaVersion := "2.11.11"

enablePlugins(GhpagesPlugin)
enablePlugins(LaikaSitePlugin)
enablePlugins(SiteScaladocPlugin)

git.remoteRepo := "git@github.com:biopet/tool-template.git"
//sourceDirectory in LaikaSite := biopetDocsDir.value
//sourceDirectories in Laika := Seq((sourceDirectory in LaikaSite).value)