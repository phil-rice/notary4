package org.validoc.notary.common

import java.text.MessageFormat
import play.api.i18n._
import play.api.mvc._

object MessagesForTest {
  def apply(kvs: (String, String)*) = {
    val map = Map(kvs: _*)
    Messages(Lang.defaultLang, new MessagesApi {
      def apply(key: String, args: Any*)(implicit lang: Lang): String =
        map.get(key) match {
          case Some(value)                        => MessageFormat.format(value, args)
          case _ if key.startsWith("constraint.") => key
          case _ if key.startsWith("error.") => key
          case _                                  => throw new RuntimeException(key)
        }

      def apply(keys: Seq[String], args: Any*)(implicit lang: Lang): String = ???
      def messages = Map()
      def preferred(candidates: Seq[Lang]): Messages = ???
      def preferred(request: RequestHeader): Messages = ???
      def preferred(request: play.mvc.Http.RequestHeader) = ???
      def setLang(result: Result, lang: Lang) = ???
      def clearLang(result: Result) = ???
      def translate(key: String, args: Seq[Any])(implicit lang: Lang) = ???
      def isDefinedAt(key: String)(implicit lang: Lang): Boolean = ???
      def langCookieName: String = ???
      def langCookieSecure: Boolean = ???
      def langCookieHttpOnly: Boolean = ???
    })
  }
}