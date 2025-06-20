    package com.example.noteapplication.Model
    import android.content.Context
    import android.content.Intent
    import androidx.activity.compose.ManagedActivityResultLauncher
    import com.google.android.gms.auth.api.signin.GoogleSignIn
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions


    fun signInWithGoogle(
        googleSignInLauncher: ManagedActivityResultLauncher<Intent, androidx.activity.result.ActivityResult>,
        context: Context
    ) {
        val webClientID = "810527282862-f005d1g8t3bjsqdguahvcatu1lel0g20.apps.googleusercontent.com"

        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientID)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

        // Đăng xuất để hiển thị lại hộp thoại chọn tài khoản
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }
