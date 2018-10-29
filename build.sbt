import ProjectPlugin._

lazy val theory = project.in(file("theory")).settings(theorySettings)

lazy val server = project.in(file("server")).settings(serverSettings).dependsOn(theory)

lazy val allRootModules: Seq[ProjectReference] = Seq(server, theory)

lazy val allRootModuleDeps: Seq[ClasspathDependency] = allRootModules.map(ClasspathDependency(_, None))

lazy val root = (project in file(".")).settings(
  organization := "io.github.toddburnside",
  name := "guitar-theory",
  version := "0.0.1-SNAPSHOT"
).aggregate(allRootModules: _*).dependsOn(allRootModuleDeps: _*)



