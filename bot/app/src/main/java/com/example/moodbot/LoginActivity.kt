package com.example.moodbot

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    //    private lateinit var googleApiClient:GoogleApiClient
    private lateinit var etId : EditText
    private lateinit var etPassword : EditText
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        etId = findViewById(R.id.etId)
        etPassword = findViewById(R.id.etPassword)
        progressBar = findViewById(R.id.progressBar)

        var btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val stEmail = etId.text.toString().trim()
            val stPassword:String = etPassword.text.toString().trim()
            if(stEmail.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(stPassword.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(stEmail, stPassword)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        val stUserEmail = user?.email
                        val stUserName = user?.displayName
                        Log.d(TAG, "stUserEmail: "+stUserEmail+", stUserName"+stUserName)
                        val loginIntent = Intent(this, MainActivity::class.java)
                        startActivity(loginIntent)
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "아이디, 비밀번호를 확인하세요.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }

        var btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val stEmail = etId.text.toString().trim()
            val stPassword:String = etPassword.text.toString().trim()
            if(stEmail.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(stPassword.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE
//            Toast.makeText(this, "Email : "+stEmail+", password : "+stPassword, Toast.LENGTH_LONG).show()
            auth.createUserWithEmailAndPassword(stEmail, stPassword)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

//        var btnGoogle = findViewById<SignInButton>(R.id.btnGoogle)
//        btnGoogle.setOnClickListener {
//
//        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }


    private fun updateUI(user: FirebaseUser?) {

    }

}