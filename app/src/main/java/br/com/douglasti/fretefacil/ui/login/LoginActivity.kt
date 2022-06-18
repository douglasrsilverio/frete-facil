package br.com.douglasti.fretefacil.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import br.com.douglasti.fretefacil.R
import br.com.douglasti.fretefacil.data.local.SharedPrefs
import br.com.douglasti.fretefacil.databinding.ActivityLoginBinding
import br.com.douglasti.fretefacil.ui.base.BaseAppCompactActivity
import br.com.douglasti.fretefacil.ui.menu.MenuActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseAppCompactActivity() {

    private val bind by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        initView()
    }

    private fun initView() {
        SharedPrefs.initSharedPreferences(this)
        setBtEnter()

        viewModel.autoLogin()

        handleState()
        handleEvents()
    }

    private fun handleState() = collectLatestLifecycleFlow(viewModel.loginState) {
        if (it.invalidUserMsg != null) {
            val msg = it.invalidUserMsg.asString(this@LoginActivity)
            bind.etUsername.error = msg
            bind.etUsername.requestFocus()
        } else
            bind.etUsername.error = null

        if (it.invalidPasswordMsg != null) {
            val msg = it.invalidPasswordMsg.asString(this@LoginActivity)
            bind.etPassword.error = msg
            bind.etPassword.requestFocus()
        } else
            bind.etPassword.error = null
    }


    private fun handleEvents() = collectLatestLifecycleFlow(viewModel.loginEvent) {
        when(it) {
            is LoginUiEvent.LoginSucessful -> {
                showToast(getString(R.string.sucessful_login))
                openMenuActivity()
            }
        }
    }

    private fun setBtEnter() = bind.btEnter.setOnClickListener {
        viewModel.login(getStringEtUser(), getStringEtPassword())
    }

    private fun openMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun getStringEtUser() = bind.etUsername.text.toString()

    private fun getStringEtPassword() = bind.etPassword.text.toString()
}