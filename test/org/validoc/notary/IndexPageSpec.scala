package org.validoc.notary

import org.scalatestplus.play._
import org.validoc.notary.common.BrowserSpec

class IndexPageSpec extends BrowserSpec with OneServerPerSuite with OneBrowserPerTest with ChromeFactory {
  "The index page" must {
    "initially not show the digest result" in {
      go to (s"http://localhost:$port/")
      divContents(xmlSource, "digestText") mustEqual (None)
    }
    "allow text to be posted and display its digest" in {
      go to (s"http://localhost:$port/")
      textArea("notary.text").value = "Some string"
      submit
      spanContents(xmlSource, "digestValue") mustEqual (Some("3febe4d69db2a2d620fa73388dbd3aed38be5575"))
    }
  }
}