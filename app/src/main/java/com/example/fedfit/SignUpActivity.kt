package com.example.fedfit

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var btnSignIn: Button? = null
    private var btnSignUp: Button? = null
    private var btnResetPassword: Button? = null
    private var progressBar: ProgressBar? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        btnSignIn = findViewById(R.id.reset_login_link)
        btnSignUp = findViewById(R.id.sign_up_button)
        inputEmail = findViewById(R.id.email_input)
        inputPassword = findViewById(R.id.password)
        progressBar = findViewById(R.id.progressBar)
        btnResetPassword = findViewById(R.id.btn_reset_password)

        btnSignIn?.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnResetPassword?.setOnClickListener{
            startActivity(Intent(this, PasswordResetActivity::class.java))
        }
        
        btnSignUp?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val email = inputEmail?.text.toString().trim { it <= ' ' }
                val password = inputPassword?.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(
                        applicationContext,
                        "Enter email address!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                if (password.length < 6) {
                    Toast.makeText(
                        applicationContext,
                        "Password too short, enter minimum 6 characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                progressBar?.visibility = View.VISIBLE
                //create user
                auth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this@SignUpActivity
                    ) { task ->
                        Toast.makeText(
                            this@SignUpActivity,
                            "createUserWithEmail:onComplete:" + task.isSuccessful,
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar?.visibility = View.GONE
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful) {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Authentication failed." + task.exception,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            startActivity(
                                Intent(
                                    this@SignUpActivity,
                                    MainActivity::class.java
                                )
                            )
                            finish()
                        }
                    }
            }
        })
    }
}