package org.validoc.notary

import controllers.notary.NotaryController
import scala.xml._
import org.validoc.notary.common.ViewSpec
import play.api.data.Form
import org.validoc.notary.common.MessagesForTest

class IndexViewSpec extends ViewSpec {

  implicit val messages = MessagesForTest("notary.text" -> "TextToBeNotarised", "digest.title" -> "DigestTitle")

  def getContent(digest: Option[String], form: Form[String] = NotaryController.NotaryForm.inputForm): Elem =
    XML.loadString(views.html.page.notary.index.render(digest, form, messages, NotaryController.notaryCss).body)

  "The index view with None as digest" should {
    "hide the digest class" in {
      val content = getContent(None)
      divContents(content, "digestText") mustEqual (None)
      spanContents(content, "digestTitle") mustEqual (None)
      spanContents(content, "digestValue") mustEqual (None)
    }
  }
  "The index view with Some(digest)" should {
    "show the digest class and digest itself" in {
      val content = getContent(Some("actualDigest"))
      divContents(content, "digestText").isDefined mustBe (true)
      spanContents(content, "digestTitle") mustEqual (Some("DigestTitle")) 
      spanContents(content, "digestValue") mustEqual (Some("actualDigest"))
    }
  }
  val contentWithSomeText =  getContent(None, NotaryController.NotaryForm.inputForm.fill("someText"))

  "The index view" should {
    "have the text in the 'form' displayed in a testarea" in {
      onlyTextArea(contentWithSomeText).text mustEqual ("someText")
    }
    "have the only textarea inside a form" in {
      val form = onlyForm(contentWithSomeText) 
      onlyTextArea(form).text mustEqual ("someText")
    }
    "have a submit button inside a form" in {
      (onlyButton(contentWithSomeText) \ "@type").text mustEqual ("submit")
    }
  }
}