Global / onChangedBuildSource := ReloadOnSourceChanges

val kyoVersion = "0.13.2"

lazy val root = project
  .in(file("."))
  .settings(
    name         := "kyo-workshop",
    version      := "0.1.0-SNAPSHOT",
    scalaVersion := "3.5.2",
    scalacOptions ++= Seq(
      "-encoding",
      "utf8",
      "-feature",
      "-unchecked",
      "-explain",
      "-deprecation",
      "-new-syntax",
      "-language:implicitConversions",
      "-Wvalue-discard",
      "-Wnonunit-statement",
      "-Xmax-inlines:100",
      "-release:21",
    ),
    libraryDependencies ++= Seq(
      "io.getkyo"     %% "kyo-core"        % kyoVersion,
      "io.getkyo"     %% "kyo-direct"      % kyoVersion,
      "io.getkyo"     %% "kyo-combinators" % kyoVersion,
      "io.getkyo"     %% "kyo-sttp"        % kyoVersion,
      "io.getkyo"     %% "kyo-tapir"       % kyoVersion,
      "io.getkyo"     %% "kyo-zio"         % kyoVersion,
      "io.getkyo"     %% "kyo-test"        % kyoVersion,
      "ch.qos.logback" % "logback-classic" % "1.5.3",
    ),
    run / fork        := true,
    scalafmtOnCompile := true,
  )

addCommandAlias("fmt", "scalafmtAll; scalafmtSbt")
