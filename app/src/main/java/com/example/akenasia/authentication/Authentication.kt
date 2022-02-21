package com.example.akenasia.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.R
import com.example.akenasia.databinding.AuthenticationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Authentication : AppCompatActivity(){

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    public override fun onCreate(savedInstanceState: Bundle?) {
        val binding = AuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        binding.SignInButton.setOnClickListener(){
            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.signin, null)
            dialogBuilder.setView(dialogView)
        }

        binding.SignInButton.setOnClickListener{
            val intent = Intent(this, SignIn::class.java)
            this.startActivity(intent)
        }

        binding.SignUpButton.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            this.startActivity(intent)
        }
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    private fun reload() {
    }

    companion object {
        const val TAG = "EmailPassword"
    }
}
