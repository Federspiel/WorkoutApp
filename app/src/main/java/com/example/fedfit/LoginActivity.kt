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


class LoginActivity : AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var btnSignIn: Button? = null
    private var btnSignUp: Button? = null
    private var btnResetPassword: Button? = null
    private var progressBar: ProgressBar? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_login)

        btnSignIn = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_signup)
        inputEmail = findViewById(R.id.email_input)
        inputPassword = findViewById(R.id.password)
        progressBar = findViewById(R.id.progressBar)
        btnResetPassword = findViewById(R.id.btn_reset_password)

        btnSignUp?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btnResetPassword?.setOnClickListener{
                startActivity(Intent(this, PasswordResetActivity::class.java))
        }

        btnSignIn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var email = inputEmail?.text.toString()
                var password = inputPassword?.text.toString()

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(applicationContext, "Enter an email!", Toast.LENGTH_SHORT).show()
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar?.visibility = View.VISIBLE;

                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this@LoginActivity
                    ) { task ->
                        progressBar?.visibility = View.GONE
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Authentication failed." + task.exception,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            startActivity(
                                Intent(
                                    this@LoginActivity,
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