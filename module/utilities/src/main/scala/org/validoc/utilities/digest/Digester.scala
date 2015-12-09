package org.validoc.notary.utilities.digest

import java.security.MessageDigest

object ToHex {
  def apply(b: Byte): String = { val s = "0" + b.toInt.toHexString; s.substring(s.length - 2) }
  def apply(b: Array[Byte]): String = b.map(ToHex(_)).mkString("")
}

object Digester {
  def asByteArray(s: String) = {
    val md = MessageDigest.getInstance("SHA");
    md.update(s.getBytes);
    md.digest()
  }
  def apply(s: String) = ToHex(asByteArray(s))
}
