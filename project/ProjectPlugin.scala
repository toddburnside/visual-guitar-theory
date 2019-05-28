import sbt.{Def, _}
import sbt.Keys._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    lazy val V = new {
      val cats       = "1.6.0"
      val catsEffect = "1.3.0"
      val kittens    = "1.2.1"
      val mouse      = "0.21"
      val http4s     = "0.20.1"
      val circe      = "0.11.1"
      val specs2     = "4.3.4"
      val logback    = "1.2.3"
    }
  }

  import autoImport.V

  private lazy val commonSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core"     % V.cats,
      "org.typelevel" %% "cats-effect"   % V.catsEffect,
      "org.typelevel" %% "kittens"       % V.kittens,
      "org.typelevel" %% "mouse"         % V.mouse,
      "io.circe"      %% "circe-core"    % V.circe,
      "io.circe"      %% "circe-generic" % V.circe,
      "io.circe"      %% "circe-parser"  % V.circe
    )
  )

  lazy val serverSettings: Seq[Def.Setting[_]] = commonSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.http4s"     %% "http4s-blaze-server" % V.http4s,
      "org.http4s"     %% "http4s-circe"        % V.http4s,
      "org.http4s"     %% "http4s-dsl"          % V.http4s,
      "org.specs2"     %% "specs2-core"         % V.specs2 % "test",
      "ch.qos.logback" % "logback-classic"      % V.logback
    )
  )

  lazy val theorySettings: Seq[Def.Setting[_]] = commonSettings ++ Seq(
    initCommands(
      "io.github.toddburnside.guitartheory.theory._",
      "Note._",
      "NoteLetter._",
      "NoteModifier._",
      "ScaleTone._",
      "Scale._",
      "ScaleDegree._",
      "io.circe.syntax._"))

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    scalaVersion := "2.12.8",
    addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6"),
    addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")
  )

  def initCommands(additionalImports: String*) =
    initialCommands := (List("cats._", "cats.implicits._") ++ additionalImports)
      .mkString("import ", ", ", "\n")
}
