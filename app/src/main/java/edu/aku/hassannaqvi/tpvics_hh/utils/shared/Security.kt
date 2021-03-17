package edu.aku.hassannaqvi.tpvics_hh.utils.shared

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.MasterKey
import java.math.BigInteger
import java.security.KeyPairGenerator
import javax.security.auth.x500.X500Principal


object Security {

    fun securityKeyGeneration(context: Context) {

        val mainKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore").apply {
            val certBuilder = KeyGenParameterSpec.Builder(context.packageName, KeyProperties.PURPOSE_ENCRYPT)
                    .setCertificateSerialNumber(BigInteger.valueOf(1L))
                    .setCertificateSubject(X500Principal("CN=MyCompany"))
                    .setUserAuthenticationRequired(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                initialize(
                        certBuilder
                                .setIsStrongBoxBacked(true)
                                .build()
                )
            } else {
                initialize(certBuilder.build())
            }
        }.also {
            val keyPair = it.generateKeyPair()
            //Continue here
        }

    }

}