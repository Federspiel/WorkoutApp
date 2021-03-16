package com.example.fedfit

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class PasswordResetActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private var btnResetPassword: Button? = null
    private var btnLoginLink: Button? = null
    private var inputEmail: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        auth = FirebaseAuth.getInstance()
        btnResetPassword = findViewById(R.id.btn_reset_password_send_mail)
        btnLoginLink = findViewById(R.id.login_link_from_reset)
        inputEmail = findViewById(R.id.email_password_reset_input)

        btnLoginLink?.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnResetPassword?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val email = inputEmail?.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(
                            applicationContext,
                            "Enter email address!",
                            Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                auth!!.sendPasswordResetEmail(email)
            }
        })

    }

}
