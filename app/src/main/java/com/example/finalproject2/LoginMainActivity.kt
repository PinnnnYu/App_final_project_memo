package com.example.finalproject2

import android.os.Bundle
import android.content.Intent
import android.os.Message
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.finalproject2.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.google.firebase.auth.FirebaseUser
import com.example.finalproject2.databinding.ActivityLoginMainBinding


class loginMainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_main)

        auth = Firebase.auth
        binding = ActivityLoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            if(binding.Password.text.toString().isEmpty()){
                showMessage("請輸入密碼")
            }else{
                signIn()
            }
        }

    }
    private fun signIn(){
        val email = binding.email.text.toString()
        val password = binding.Password.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    println("---------signInWithEmail:success---------")
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent()
                    intent.setClass(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    it.exception?.message?.let{ }
                    println("---------error---------")
                    showMessage("登入失敗")
                    updateUI(null)
                }
            }
    }
    private fun showMessage(message: String){
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@loginMainActivity)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("確定"){ dialog, which -> }
        alertDialog.show()
    }
    private fun updateUI(user: FirebaseUser?){
        if(user!=null){
            binding.email.visibility = View.GONE
            binding.Password.visibility = View.GONE
            binding.button.visibility = View.GONE
            binding.button2.visibility = View.GONE
        }else{
            binding.email.visibility = View.VISIBLE
            binding.Password.visibility = View.VISIBLE
            binding.button.visibility = View.VISIBLE
            binding.button2.visibility = View.VISIBLE
        }
    }
}