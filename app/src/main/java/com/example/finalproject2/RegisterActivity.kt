package com.example.finalproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.finalproject2.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.button2.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.Password.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        Log.d("Test","成功註冊")
                        finish()
                    }else{
                        Log.w("Test","註冊失敗",it.exception)
                        showMessage("註冊會員失敗")
                    }
                }
        }
    }
    private fun showMessage(message: String){
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("確定"){dialog,which -> }
        alertDialog.show() }
}