
package org.validoc.notary.users

import org.scalatestplus.play.PlaySpec
import play.api.mvc._

class SimpleUserSpec extends PlaySpec with Results {

  "The Simple User" must {
    "Return the user name" in {
      val su = SimpleUser("someName")
      implicitly[User[SimpleUser]].userName(su) mustBe "someName"
    }
  }
}