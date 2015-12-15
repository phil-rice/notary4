package org.validoc.notary.common

import org.scalatestplus.play.PlaySpec
import scala.xml._
import org.openqa.selenium.WebDriver
import scala.concurrent.Future
import play.api.mvc.Result

trait XmlSpec extends PlaySpec {
  def textIn(elemTag: String, trim: Boolean)(xml: Node, filter: Node => Boolean) = {
    (xml \\ elemTag).filter(filter) match {
      case x if x.size == 0 => None
      case x if x.size == 1 => Some(if (trim) x.text.trim else x.text)
      case x                => throw new RuntimeException(s"Cannot find $elemTag in \n" + xml)
    }
  }
  def textInTagWithClass(elemTag: String, trim: Boolean = false)(xml: Node, elementClass: String) =
    textIn(elemTag, trim)(xml, t => (t \ "@class").text == elementClass)

  val divContents = textInTagWithClass("div")_
  val spanContents = textInTagWithClass("span")_
  val textAreaContents = textIn("textarea", trim = true)_

  def onlyTagIn(elemTag: String)(xml: Node) = (xml \\ elemTag).toList match {
    case x :: Nil => x
    case x        => throw new RuntimeException(s"Cannot find $elemTag in \n" + x.mkString("\n") + "\nWhole xml is\n" + xml)
  }
  val onlyTextArea = onlyTagIn("textarea")_
  val onlyForm = onlyTagIn("form")_
  val onlyButton = onlyTagIn("button")_

  def onlyTagWithId(elemTag: String)(xml: Node, id: String) =
    (xml \\ elemTag).filter(x => (x \ "@id").text == id).toList match {
      case x :: Nil => x
      case x        => throw new RuntimeException(s"Cannot find $elemTag in\n" + xml)
    }

}

trait ViewSpec extends XmlSpec

trait ControllerSpec extends XmlSpec {
  import play.api.test.Helpers._

  def xmlOfResult(result: Future[Result]) =
    XML.loadString(contentAsString(result))
}

trait BrowserSpec extends PlaySpec with XmlSpec {

  def xmlSource(implicit driver: WebDriver) = XML.loadString(driver.getPageSource)
}