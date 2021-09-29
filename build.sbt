scalaVersion := "3.0.2"

val Version = new {
  val akka = "2.6.16"
  val akkaHttp = "10.2.6"
}

val Akka = Seq(
  "com.typesafe.akka" %% "akka-actor-typed"         % Version.akka,
  "com.typesafe.akka" %% "akka-stream"              % Version.akka,
  "com.typesafe.akka" %% "akka-http"                % Version.akkaHttp,
  "com.typesafe.akka" %% "akka-http-spray-json"     % Version.akkaHttp,
  "com.typesafe.akka" %% "akka-slf4j"               % Version.akka,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % Version.akka      % Test
).map(_.cross(CrossVersion.for3Use2_13))

val commons = Seq(
  organization := "dev.seoh.example.akka",
  scalaVersion := "3.0.2",
  libraryDependencies := Akka ++ Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.6"
  )
)

lazy val up          = project.in(file("ch02-up-and-running")).settings(commons: _*)
// lazy val test        = project.in(file("ch03-testdriven")).settings(commons: _*)
// lazy val fault       = project.in(file("ch04-fault-tolerance")).settings(commons: _*)
// lazy val futures     = project.in(file("ch05-futures")).settings(commons: _*)
// lazy val remoting    = project.in(file("ch06-remoting")).settings(commons: _*)
// lazy val conf        = project.in(file("ch07-conf-deploy")).settings(commons: _*)
// lazy val structure   = project.in(file("ch08-structure")).settings(commons: _*)
// lazy val routing     = project.in(file("ch09-routing")).settings(commons: _*)
// lazy val channels    = project.in(file("ch10-channels")).settings(commons: _*)
// lazy val state       = project.in(file("ch11-state")).settings(commons: _*)
// lazy val integration = project.in(file("ch12-integration")).settings(commons: _*)
// lazy val stream      = project.in(file("ch13-stream")).settings(commons: _*)
// lazy val cluster     = project.in(file("ch14-cluster")).settings(commons: _*)
// lazy val persistence = project.in(file("ch15-persistence")).settings(commons: _*)
// lazy val looking     = project.in(file("ch17-looking-ahead")).settings(commons: _*)
