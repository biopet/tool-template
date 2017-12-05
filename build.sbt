organization := "com.github.biopet"
//TODO: change name
name := "ToolTemplate"

homepage := Some(url(s"https://github.com/biopet/$urlToolName"))
licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))

scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/biopet/$urlToolName"),
    s"scm:git@github.com:biopet/$urlToolName.git"
  )
)

developers := List(
  Developer(id="ffinfo", name="Peter van 't Hof", email="pjrvanthof@gmail.com", url=url("https://github.com/ffinfo")),
  Developer(id="rhpvorderman", name="Ruben Vorderman", email="r.h.p.vorderman@lumc.nl", url=url("https://github.com/rhpvorderman"))
)

publishMavenStyle := true

scalaVersion := "2.11.11"

resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.2"
libraryDependencies += "com.github.biopet" %% "tool-test-utils" % "0.1" % Test

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
  releaseStepCommand("ghpagesPushSite"),
  releaseStepCommand("publishSigned"),
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges,
  releaseStepCommand("git checkout develop"),
  releaseStepCommand("git merge master"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

// Documentation stuff
//TODO: Change these two variables
lazy val urlToolName="tool-template"
lazy val classPrefix="nl.biopet.tools.template"

import LaikaKeys._
enablePlugins(LaikaSitePlugin)
enablePlugins(SiteScaladocPlugin)
enablePlugins(GhpagesPlugin)
enablePlugins(PreprocessPlugin)

val docsDir: String="target/markdown/"
val readme: String="./README.md"
val ghpagesDir: String="target/gh"

sourceDirectory in LaikaSite := file(docsDir)
sourceDirectories in Laika := Seq((sourceDirectory in LaikaSite).value)
rawContent in Laika := true

git.remoteRepo := s"git@github.com:biopet/$urlToolName.git"
ghpagesRepository := file(ghpagesDir)

// Puts Scaladoc output in `in /api subfolder`

siteSubdirName in SiteScaladoc := {
  if (isSnapshot.value) {"develop/api"}
  else s"${version.value}/api"
 }
siteDirectory in Laika  := file("target/site")

// FileFilter that only includes current version for deletion.
// The redirector is also included for deletion if version is not a snapshot.
includeFilter in ghpagesCleanSite := new FileFilter{
  def accept(f: File) = {
    println("path=" + f.getPath)
    if (isSnapshot.value) {
      f.getParent.contains("develop")
    } else {
      f.getPath.contains(s"${version.value}") ||
        f.getPath == new java.io.File(ghpagesRepository.value, "index.html").getPath
    }
  }
}
lazy val generateDocs = taskKey[Unit]("Generate documentation files")
lazy val generateReadme = taskKey[Unit]("Generate readme")

generateDocs := {
  import sbt.Attributed.data
  val r = (runner in Runtime).value
  val input = Seq("--generateDocs", s"outputDir=$docsDir,version=${version.value},release=${!isSnapshot.value}", version.value)
  val classPath =  (fullClasspath in Runtime).value
  r.run(
    s"$classPrefix.${name.value}",
    data(classPath),
    input,
    streams.value.log
  ).foreach(sys.error)
  }
generateReadme := {
  import sbt.Attributed.data
  val r: ScalaRun = (runner in Runtime).value
  val input = Seq("--generateReadme", readme)
  val classPath =  (fullClasspath in Runtime).value
  r.run(
    s"$classPrefix.${name.value}",
    data(classPath),
    input,
    streams.value.log
  ).foreach(sys.error)
}
makeSite := (makeSite triggeredBy generateDocs).value
makeSite := (makeSite dependsOn generateDocs).value
ghpagesPushSite := (ghpagesPushSite dependsOn makeSite).value
