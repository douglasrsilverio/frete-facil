package br.com.douglasti.fretefacil.data.local


import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPrefs {

    companion object {
        private lateinit var sharedPreferences: SharedPreferences

        fun initSharedPreferences(context: Context) {
            if(! this::sharedPreferences.isInitialized)
                sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun setUser(user: String) {
            val editor = sharedPreferences.edit() //1
            editor.putString("Name", user) //2
            editor.apply()
        }

        fun getUser(): String {
            return sharedPreferences.getString("Name", "")!! //testing
        }
    }
}