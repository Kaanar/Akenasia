package com.example.akenasia.authentication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.akenasia.databinding.SigninBinding
import com.example.akenasia.home.MainActivity

class SignIn: AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    lateinit var binding: SigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = SigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        binding.SignInConfirmer.setOnClickListener{
            signIn(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString())
        }
    }

    fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Authentication.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Authentication.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}