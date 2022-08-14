package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.auth0.android.result.Credentials
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.HashMap

object CredentialsManager {


    private const val ACCESS_TOKEN = "access_token"

    private lateinit var editor: SharedPreferences.Editor

    /**
     * Saves the submitted credentials of the user
     * @property context Abstract class providing info about application
     * @property credentials The credentials of the user
     */
    fun saveCredentials(context: Context, credentials: Credentials?) {
        Log.d("CREDENTIALMANAGER" , credentials.toString())
        val masterKeyAlias = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            .build()

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(masterKeyAlias)
            .build()

        val sp: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        editor = sp.edit()
        editor.putString(ACCESS_TOKEN, credentials?.accessToken)
            .apply()
    }


    /**
     * Generates an access token
     * @property context Abstract class providing info about application
     * @return Returns the token in the form of a string
     */
    fun getAccessToken(context: Context): String? {

        val masterKeyAlias = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            .build()

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(masterKeyAlias)
            .build()

        val sp: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        return sp.getString(ACCESS_TOKEN, null)
    }
}