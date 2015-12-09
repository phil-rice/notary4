package org.validoc.notary.users

import controllers._
import play.api.mvc._
import play.api.libs.json._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future
import org.scalatestplus.play._

class LoginIntegrationSpec extends PlaySpec with OneServerPerSuite with OneBrowserPerTest with ChromeFactory {

  "The login page" must {
    "diplay 'Logged In as" in {
      go to (s"http://localhost:$port/login?user=Phil&password=SomePassWord")
      pageSource must include("Logged in as Phil")
    }
  }

  "The logout page" must {
    "diplay 'Logged out if logged in before calling" in {
      go to (s"http://localhost:$port/login?user=Phil&password=SomePassWord")
      go to (s"http://localhost:$port/logout")
      pageSource must include("Logged Out")
    }
    "diplay 'Logged out even if not logged in" in {
      go to (s"http://localhost:$port/logout")
      pageSource must include("Logged Out")
    }
  }

  "The who page" must {
    "return None before logging" in {
      go to (s"http://localhost:$port/who")
      pageSource must include("You are logged in as None")
    }

    "return who after logging in" in {
      go to (s"http://localhost:$port/login?user=Phil&password=SomePassWord")
      go to (s"http://localhost:$port/who")
      pageSource must include("You are logged in as Some(Phil)")
    }

    "display None after log in and log out" in {
      go to (s"http://localhost:$port/who")
      pageSource must include("You are logged in as None")

      go to (s"http://localhost:$port/login?user=Phil&password=SomePassWord")
      go to (s"http://localhost:$port/logout")
      go to (s"http://localhost:$port/who")
      pageSource must include("You are logged in as None")
    }
  }
}