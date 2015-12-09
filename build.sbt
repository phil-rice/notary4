import Dependencies._

lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := scalaVersionNo,
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
  javaOptions ++= Seq("-Xmx4G", "-XX:+UseConcMarkSweepGC"),
  resolvers ++= playRepositories
)

lazy val root = (project in file(".")). 
                settings(commonSettings: _*).
                settings(
                   libraryDependencies ++= rootDependencies
                ).enablePlugins(PlayScala).
                dependsOn( user, notary, utilities).
                aggregate( user, notary, utilities)
				  
lazy val notary = (project in file("module/notary")).
                settings(commonSettings: _*).
                settings(
                   libraryDependencies ++= notaryDependencies
                ).enablePlugins(PlayScala).
                dependsOn(common  % "test->test;compile->compile", utilities % "test->test;compile->compile")
				  
lazy val user = (project in file("module/user")).
                settings(commonSettings: _*).
                settings(
                   libraryDependencies ++= userDependencies
                ).enablePlugins(PlayScala).
                dependsOn(common, utilities)

lazy val common = (project in file("module/common")).
                settings(commonSettings: _*).
                settings(
                   libraryDependencies ++= commonDependencies
                ).enablePlugins(PlayScala).
                enablePlugins(PlayScala).
                dependsOn( utilities)

lazy val utilities = (project in file("module/utilities")).
                settings(commonSettings: _*).
                settings(
                   libraryDependencies ++= utilitiesDependencies
                )