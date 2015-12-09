package org.validoc.notary.users
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

trait User[A] {
  def userName(a: A): String
}

sealed trait LoginResult
case class LoginSuccessful(userName: String) extends LoginResult
case class LoginFailed(reason: String) extends LoginResult

trait UserInRequest {
  def userName(implicit request: Request[_]): LoginResult
}

trait UserInSession {
  def userName(implicit request: RequestHeader): Option[String]
  def addUserName(result: Result, userName: String)(implicit request: RequestHeader): Result
  def clear(result: Result)(implicit request: RequestHeader): Result
}

object SimpleUserInRequest extends UserInRequest {
  val loginForm = Form(tuple("user" -> text, "password" -> text))
  def userName(implicit request: Request[_]) =
    loginForm.bindFromRequest.fold(withErrors => throw new RuntimeException,
      { case (user, password) => LoginSuccessful(user) })
}

object SimpleUserInSession extends UserInSession {
  val userNameKey = "userName"
  def userName(implicit request: RequestHeader) =
    request.session.get(userNameKey)
  def addUserName(result: Result, userName: String)(implicit request: RequestHeader) =
    result.addingToSession(userNameKey -> userName)
  def clear(result: Result)(implicit request: RequestHeader) =
    result.removingFromSession(userNameKey)
}

object SimpleUser {
  implicit object SimpleUserAccess extends User[SimpleUser] {
    def userName(a: SimpleUser) = a.userName
  }
}

case class SimpleUser(userName: String)