package com.example.akenasia.authentication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.databinding.SignupBinding
import com.example.akenasia.home.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUp: AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    lateinit var binding: SignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        binding.SignUpConfirmer.setOnClickListener{
            createAccount(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString())
        }
    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        if(TextUtils.isEmpty(email)){
            binding.emailEditText.error="l'email ne peut pas être vide"
        }
        else if (TextUtils.isEmpty(password) || password.length<6) {
            binding.passwordEditText.error="le mot de passe doit contenir au moins 6 caractères"
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Authentication.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Authentication.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}