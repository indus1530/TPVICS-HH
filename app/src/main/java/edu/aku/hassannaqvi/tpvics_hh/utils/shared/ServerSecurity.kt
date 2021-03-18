package edu.aku.hassannaqvi.tpvics_hh.utils.shared

import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object ServerSecurity {

    fun encrypt(plain: String, apikey: String): String {
        return try {
            val iv = ByteArray(16)
            val cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + "PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(apikey.toByteArray(StandardCharsets.UTF_8), "AES"), IvParameterSpec(iv))
            val cipherText = cipher.doFinal(plain.toByteArray(StandardCharsets.UTF_8))
            val ivAndCipherText = ByteArray(iv.size + cipherText.size)
            System.arraycopy(iv, 0, ivAndCipherText, 0, iv.size)
            System.arraycopy(cipherText, 0, ivAndCipherText, iv.size, cipherText.size)
            Base64.encodeToString(ivAndCipherText, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.message
            "[]"
        }
    }

    fun decrypt(encoded: String, apikey: String): String {
        return try {
            val ivAndCipherText = Base64.decode(encoded, Base64.NO_WRAP)
            val iv = Arrays.copyOfRange(ivAndCipherText, 0, 16)
            val cipherText = Arrays.copyOfRange(ivAndCipherText, 16, ivAndCipherText.size)
            val cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + "PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(apikey.toByteArray(StandardCharsets.UTF_8), "AES"), IvParameterSpec(iv))
            String(cipher.doFinal(cipherText), StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.message
            "[]"
        }
    }


}