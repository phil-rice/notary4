package controllers.user

import org.validoc.notary.users._
import play.api.mvc._
import play.api.http._


trait LoginController[A] {
  self: Controller =>
  implicit val userLike: User[A]

  def userInSession: UserInSession
  def userInRequest: UserInRequest

  def index = Action { Ok("Hello World") }

  def login = Action { implicit request =>
    userInRequest.userName match {
      case LoginSuccessful(userName) => userInSession.addUserName(Ok(s"Logged in as $userName"), userName)
      case LoginFailed(reason)       => Ok(s"Failed to login $reason")
    }
  }

  def who = Action { implicit request => Ok(s"You are logged in as ${userInSession.userName}") }

  def logout = Action { implicit request => userInSession.clear(Ok("Logged Out")) }

}

object LoginController extends Controller with LoginController[SimpleUser] {
  implicit val userLike = implicitly[User[SimpleUser]]
  def userInSession: UserInSession = SimpleUserInSession
  def userInRequest: UserInRequest = SimpleUserInRequest

}

object LAssets extends controllers.AssetsBuilder(LazyHttpErrorHandler)