package com.example.bmi

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.TextView

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var regUsername = findViewById<TextInputEditText>(R.id.regUsername)
        var regEmail = findViewById<TextInputEditText>(R.id.regEmail)
        var regPassword = findViewById<TextInputEditText>(R.id.regPassword)
        var regConfirmPassword = findViewById<TextInputEditText>(R.id.regConfirmPassword)
        var btnSubmit = findViewById<Button>(R.id.btnSubmit)
        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var regUsernameWarning = findViewById<TextView>(R.id.regUsernameWarning)
        var regEmailWarning = findViewById<TextView>(R.id.regEmailWarning)
        var regPasswordWarning = findViewById<TextView>(R.id.regPasswordWarning)

        btnSubmit.setOnClickListener {
            val isInputValid = validateInput(
                regUsername.text.toString(),
                regEmail.text.toString(),
                regPassword.text.toString(),
                regConfirmPassword.text.toString(),
                regUsernameWarning,
                regEmailWarning,
                regPasswordWarning
            )

            if (isInputValid) {
                val intentLanding = Intent(this, LandingActivity::class.java).apply {
                    putExtra(
                        LandingActivity.EXTRA_PARCEL,
                        User(
                            regUsername.text.toString(),
                            regEmail.text.toString(),
                            regPassword.text.toString()
                        )
                    )
                }
                startActivity(intentLanding)

//                val intentLogin = Intent(this, LoginActivity::class.java).apply {
//                    putExtra(
//                        "USER_DATA_LOGIN",
//                        User(
//                            regUsername.text.toString(),
//                            regEmail.text.toString(),
//                            regPassword.text.toString()
//                        )
//                    )
//                }

                val sharedPref = getSharedPreferences("REGISTER_DATA", MODE_PRIVATE)
                sharedPref.edit().apply {
                    putString("USERNAME", regUsername.text.toString())
                    putString("EMAIL", regEmail.text.toString())
                    putString("PASSWORD", regPassword.text.toString())
                    apply()
                }
            }
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateInput(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        usernameWarning: TextView,
        emailWarning: TextView,
        passwordWarning: TextView
    ): Boolean {
        var isValid = true

        // Validasi username
        if (username.isBlank()) {
            usernameWarning.text = "Username tidak boleh kosong!"
            usernameWarning.visibility = View.VISIBLE
            isValid = false
        } else {
            usernameWarning.visibility = View.GONE
        }

        // Validasi email
        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailWarning.text = "Email tidak valid!"
            emailWarning.visibility = View.VISIBLE
            isValid = false
        } else {
            emailWarning.visibility = View.GONE
        }

        // Validasi password
        if (password.isEmpty()) {
            passwordWarning.text = "Password wajib diisi!"
            passwordWarning.visibility = View.VISIBLE
            isValid = false
        } else if(password != confirmPassword) {
            passwordWarning.text = "Konfirmasi password tidak cocok!"
            passwordWarning.visibility = View.VISIBLE
            isValid = false
        } else {
            passwordWarning.visibility = View.GONE
        }

        return isValid
    }
}