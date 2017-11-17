organization := "com.github.biopet"
//TODO: change name
name := "tool-template"

scalaVersion := "2.11.11"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.2-SNAPSHOT" changing()

libraryDependencies += "com.github.biopet" %% "test-utils" % "0.1" % Test

import LaikaKeys._
enablePlugins(LaikaSitePlugin)
enablePlugins(SiteScaladocPlugin)
enablePlugins(GhpagesPlugin)
enablePlugins(PreprocessPlugin)

//TODO: change mainClass
mainClass in assembly := Some("nl.biopet.tools.template.ToolTemplate")

useGpg := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

preprocessVars in Preprocess := Map("VERSION" -> version.value)
val docsDir: String="target/markdown/"
val readme: String="./README.md"

sourceDirectory in LaikaSite := file(docsDir)
sourceDirectories in Laika := Seq((sourceDirectory in LaikaSite).value)
rawContent in Laika := true
git.remoteRepo := "git@github.com:biopet/test.git"
ghpagesRepository := file("target/gh")

// Puts Scaladoc output in `target/site/api/latest`
siteSubdirName in SiteScaladoc := s"${version.value}/api"

excludeFilter in ghpagesCleanSite := new FileFilter{
  def accept(f: File) = true
}

lazy val generateDocs = taskKey[Unit]("Generate documentation files")

fullRunTask(generateDocs, Test , "nl.biopet.tools.template.Documentation", docsDir, readme)

makeSite <<= makeSite.triggeredBy(generateDocs)
makeSite <<= makeSite dependsOn(generateDocs)


import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  releaseStepCommand("git fetch"),
  releaseStepCommand("git checkout master"),
  releaseStepCommand("git pull"),
  releaseStepCommand("git merge origin/develop"),
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges,
  releaseStepCommand("git checkout develop"),
  releaseStepCommand("git merge master"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
