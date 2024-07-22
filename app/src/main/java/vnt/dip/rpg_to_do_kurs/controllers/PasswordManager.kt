package vnt.dip.rpg_to_do_kurs.controllers

import android.content.Context
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class PasswordManager(private val context: Context) {

    private val PASSWORD_PREFS_NAME = "password_prefs"
    private val PASSWORD_KEY = "encrypted_password"

    fun savePassword(password: String) {
        val encryptedPassword = encryptPassword(password)
        val sharedPreferences = context.getSharedPreferences(PASSWORD_PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(PASSWORD_KEY, encryptedPassword).apply()
    }

    fun isPasswordCorrect(inputPassword: String): Boolean {
        val sharedPreferences = context.getSharedPreferences(PASSWORD_PREFS_NAME, Context.MODE_PRIVATE)
        val storedPassword = sharedPreferences.getString(PASSWORD_KEY, null) ?: return false
        val decryptedPassword = decryptPassword(storedPassword, inputPassword)
        return inputPassword == decryptedPassword
    }

    fun isPasswordSaved(): Boolean {
        val sharedPreferences = context.getSharedPreferences(PASSWORD_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.contains(PASSWORD_KEY)
    }

    fun deletePassword() {
        val sharedPreferences = context.getSharedPreferences(PASSWORD_PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(PASSWORD_KEY).apply()
    }

    private fun encryptPassword(password: String): String {
        val salt = "R,£3kX%0XI|4".toByteArray() // Change this to your own salt
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec("63^7&3HH2aq-".toCharArray(), salt, 65536, 256)
        val tmp = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.encoded, "AES")

        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encrypted = cipher.doFinal(password.toByteArray())

        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    private fun decryptPassword(encryptedPassword: String, password: String): String {
        val salt = "R,£3kX%0XI|4".toByteArray() // Change this to your own salt
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec("63^7&3HH2aq-".toCharArray(), salt, 65536, 256)
        val tmp = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.encoded, "AES")

        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decrypted = cipher.doFinal(Base64.decode(encryptedPassword, Base64.DEFAULT))

        return String(decrypted)
    }
}