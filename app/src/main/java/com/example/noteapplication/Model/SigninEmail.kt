package com.example.noteapplication.Model

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.google.firebase.auth.FirebaseAuth
//fun registerWithEmail(context: Context,
//                          email: String,
//                          password: String,
//) {
//        FirebaseAuth.getInstance()
//            .createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
//                } else {
//                    val error = task.exception?.message ?: "Lỗi không xác định"
//                    Toast.makeText(context, "Đăng ký thất bại: $error", Toast.LENGTH_SHORT).show()
//                    Log.d("AuthHelper", "Đăng ký thất bại: $error")
//                }
//            }
//    }
//fun loginWithEmail(
//        context: Context,
//        email: MutableState<String>,
//        password: MutableState<String>,
//    ) {
//
//    if(email.value.isNotEmpty() && password.value.isNotEmpty())
//    FirebaseAuth.getInstance()
//        .signInWithEmailAndPassword(email.value, password.value)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
//            }
//            else {
//                val error = task.exception?.message ?: "Lỗi không xác định"
//                Toast.makeText(context, "Đăng nhập thất bại: $error", Toast.LENGTH_SHORT).show()
//            }
//        }
//    else
//        Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
//    }

