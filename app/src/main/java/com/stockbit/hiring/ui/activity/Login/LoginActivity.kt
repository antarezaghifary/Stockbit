package com.stockbit.hiring.ui.activity.Login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.toast
import com.stockbit.hiring.ui.activity.Menu.MainActivity
import com.test.stockbit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding){
            btnLogin.setOnClickListener {
                val email: String = edtEmail.text.toString().trim()
                val password: String = edtPassword.text.toString().trim()

                if(email.isNotEmpty() && password.isNotEmpty()){
                    val iinent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(iinent)
                }else{
                    toast("Please complete the field")
                }
            }
        }
    }
}