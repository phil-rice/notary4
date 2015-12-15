package org.validoc.notary

import org.validoc.notary.common.ControllerSpec
import org.validoc.notary.common.MessagesForTest
import controllers.notary.NotaryController
import play.api.mvc._
import play.api.libs.json.Json
import play.api.test.FakeRequest
import scala.concurrent.Future
import play.api.test.Helpers._

class NotaryControllerSpec extends ControllerSpec with Results {

  val controller = new NotaryController {
    override implicit def messages(implicit request: Request[_]) =
      MessagesForTest(
        "notary.text" -> "TextToBeNotarised",
        "digest.title" -> "DigestTitle")
  }

  def checkResults(result: Future[Result]) {
    status(result) mustEqual OK
    contentType(result) mustBe Some("text/html")
    charset(result) mustBe Some("utf-8")
  }

  "The NotaryController when index is 'GET'" should {
    "not have the digest div" in {
      val result = controller.index()(FakeRequest())

      val xml = xmlOfResult(result)
      divContents(xml, "digestText") mustEqual (None)
      checkResults(result)
    }
  }

  "The NotaryController when index is 'POST' with data from text Area" should {
    "display the digest div if there was text" in {
      val request = FakeRequest(POST, "/").
        withJsonBody(Json.parse(s"""{ "notary.text": "Some string" }"""))
      val result = controller.postText()(request)

      val xml = xmlOfResult(result)
      spanContents(xml, "digestValue") mustEqual
        (Some("3febe4d69db2a2d620fa73388dbd3aed38be5575"))

      checkResults(result)
    }

    "not display the digest div, and say 'This field is required' if there was no text in the text area" in {
      val request = FakeRequest(POST, "/").
        withJsonBody(Json.parse(s"""{ "notary.text": "" }"""))
      val result = controller.postText()(request)

      val xml = xmlOfResult(result)
      spanContents(xml, "digestValue") mustEqual (None)
      val errorDl = onlyTagWithId("dl")(xml, "notary_text_field")
      (errorDl \ "@class").text mustEqual (" error")
      checkResults(result)
    }
  }
}