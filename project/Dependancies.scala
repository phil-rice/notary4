import sbt._
object Dependencies {
  // Versions
  val scalaVersionNo = "2.11.7"
  val scalaPlusPlayTestVersion = "1.4.0-M3"

  // Libraries
  val scalaPlusPlay = "org.scalatestplus" %% "play" % scalaPlusPlayTestVersion % Test

  //Repositories
  val playRepositories = Seq(
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases")

  // Projects Dependencies
  val rootDependencies = Seq(scalaPlusPlay)
  val notaryDependencies = Seq(scalaPlusPlay)
  val userDependencies = Seq(scalaPlusPlay)
  val commonDependencies = Seq(scalaPlusPlay)
  val utilitiesDependencies = Seq(scalaPlusPlay)
}