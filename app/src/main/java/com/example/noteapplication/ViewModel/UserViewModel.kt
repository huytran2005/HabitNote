package com.example.noteapplication.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
class UserViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun saveUser(displayName: String) {
        val uid = auth.currentUser?.uid ?: return
        val user = hashMapOf("displayName" to displayName)

        db.collection("users").document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("UserViewModel", "User saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("UserViewModel", "Error saving user: ${e.message}")
            }
    }

    var displayName = mutableStateOf("")
        private set
    fun getUserDisplayName() {
        val uid = auth.currentUser?.uid ?: return

        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    displayName.value = document.getString("displayName") ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("UserViewModel", "Lỗi khi lấy tên: ${e.message}")
            }
    }
}
