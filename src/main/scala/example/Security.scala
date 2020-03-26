package example

// Credits: 
// https://medium.com/@kasunpdh/how-to-store-passwords-securely-with-pbkdf2-204487f14e84
// https://alvinalexander.com/source-code/scala-how-to-convert-array-bytes-to-hex-string/

package util

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.io.UnsupportedEncodingException
import java.util.UUID

object Security extends App {
  
  def hashedPassword(password: String, salt: String): String = {
    val iterations = 10000
    val keyLength = 512
    val passwordChars = password.toCharArray
    val saltBytes = salt.getBytes
    val hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength)
    bytesToHex(hashedBytes)
  }

  def hashPassword(password: Array[Char], salt: Array[Byte], iterations: Int, keyLength: Int): Array[Byte] = try {
    val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
    val spec = new PBEKeySpec(password, salt, iterations, keyLength)
    val key = skf.generateSecret(spec)
    val res = key.getEncoded
    res
  } catch {
    case e@(_: NoSuchAlgorithmException | _: InvalidKeySpecException) =>
      throw new RuntimeException(e)
  }

  def bytesToHex(bytes: Seq[Byte]): String = {
    val sb = new StringBuilder
    bytes.foreach(b => sb.append(String.format("%02x", Byte.box(b))))
    sb.toString
  }

  // val mysalt = UUID.randomUUID.toString
  val mysalt = "mysalt"
  println(Security.hashedPassword("mypassword", mysalt))
}