package com.example.bmi

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
//    companion object {
//        val EXTRA_PARCEL = "USER_DATA_LOGIN"
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val user =intent.getParcelableExtra<User>(EXTRA_PARCEL)

        // Ambil data dari SharedPreferences
        val sharedPref = getSharedPreferences("REGISTER_DATA", MODE_PRIVATE)
        val savedUsername = sharedPref.getString("USERNAME", "")
        val savedEmail = sharedPref.getString("EMAIL", "")
        val savedPassword = sharedPref.getString("PASSWORD", "")

        var loginNameOrEmail = findViewById<TextInputEditText>(R.id.loginNameOrEmail)
        var loginPassword = findViewById<TextInputEditText>(R.id.loginPassword)
        var btnLoginSubmit = findViewById<Button>(R.id.btnLoginSubmit)
        var btnRegister = findViewById<Button>(R.id.btnRegister)
        var loginNameOrEmailWarning = findViewById<TextView>(R.id.loginNameOrEmailWarning)
        var loginPasswordWarning = findViewById<TextView>(R.id.loginPasswordWarning)

        btnLoginSubmit.setOnClickListener {
//            val isInputValid = validateInput(
//                user.username,
//                user.email,
//                user.password,
//                savedEmail.toString(),
//                savedPassword.toString(),
//                loginNameOrEmail.text.toString(),
//                loginPassword.text.toString(),
//                loginNameOrEmailWarning,
//                loginPasswordWarning
//            )

            val isInputValid = validateInput(
                savedUsername.toString(),
                savedEmail.toString(),
                savedPassword.toString(),
                loginNameOrEmail.text.toString(),
                loginPassword.text.toString(),
                loginNameOrEmailWarning,
                loginPasswordWarning
            )

            if (isInputValid) {
                val intentLanding = Intent(this, LandingActivity::class.java).apply {
                    putExtra(
                        LandingActivity.EXTRA_PARCEL,
                        User(
                            savedUsername.toString(),
                            savedEmail.toString(),
                            savedPassword.toString()
                        )
                    )
                }
                startActivity(intentLanding)
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun validateInput(
        savedUsername: String,
        savedEmail: String,
        savedPassword: String,
        nameOrEmail: String,
        password: String,
        nameOrEmailWarning: TextView,
        passwordWarning: TextView
    ): Boolean {
        var isValid = true

        // Validasi username/email
        if (nameOrEmail.isBlank()) {
            nameOrEmailWarning.text = "Username/Email tidak boleh kosong!"
            nameOrEmailWarning.visibility = View.VISIBLE
            isValid = false
        } else if (nameOrEmail != savedEmail && nameOrEmail != savedUsername){
            nameOrEmailWarning.text = "Username/Email tidak ditemukan!"
            nameOrEmailWarning.visibility = View.VISIBLE
            isValid = false
        } else {
            nameOrEmailWarning.visibility = View.GONE
        }

        // Validasi password
        if (password.isEmpty()) {
            passwordWarning.text = "Password wajib diisi!"
            passwordWarning.visibility = View.VISIBLE
            isValid = false
        } else if(password != savedPassword) {
            passwordWarning.text = "Password Salah!"
            passwordWarning.visibility = View.VISIBLE
            isValid = false
        } else {
            passwordWarning.visibility = View.GONE
        }

        return isValid
    }
}