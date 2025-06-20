package com.example.noteapplication.ViewModel
import kotlinx.coroutines.tasks.await
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.noteapplication.Model.Note
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val email: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> get() = _authState

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val email = auth.currentUser?.email ?: "Unknown"
                        _authState.value = AuthState.Success(email)
                    } else {
                        _authState.value = AuthState.Error("Failed to sign-in")
                    }
                }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    private val _authStateEmail = MutableStateFlow<AuthState>(AuthState.Idle)
    val authStateEmail: StateFlow<AuthState> get() = _authStateEmail
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authStateEmail.value = AuthState.Loading
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userEmail = auth.currentUser?.email ?: "Unknown"
                        _authStateEmail.value = AuthState.Success(userEmail)
                    } else {
                        val errorMessage = task.exception?.message ?: "Lỗi không xác định"
                        _authStateEmail.value = AuthState.Error(errorMessage)
                    }
                }
        }
    }
    fun register(email: String, password: String,
                 onSuccess: () -> Unit = {},
                 onFailure:(String)-> Unit={}) {
        viewModelScope.launch {
            //CHờ xử lý sự kiện
            _authStateEmail.value = AuthState.Loading
            // Bắt đầu đăng ký
            auth.createUserWithEmailAndPassword(email, password)
                //Xử lý kết quả khi thành công đăng kí
                .addOnSuccessListener  {
                    onSuccess()
                    _authStateEmail.value = AuthState.Success("Register Success")
                }
                //Xử lý kết quả khi thất bại đăng kí
                .addOnFailureListener {
                    onFailure(it.message ?: "Error")
                }
        }
    }
    fun logout(onSuccess: () -> Unit = {},
               context: Context){

        //CHờ xử lý sự kiện
        _authStateEmail.value= AuthState.Loading

        //Bắt đầu đăng xuất
        try {
            auth.signOut()
            Toast.makeText(context,"Đăng xuất thành công", Toast.LENGTH_SHORT).show()
            //Cho State về bắt đầu trạng thái
            _authStateEmail.value = AuthState.Idle
            _authState.value = AuthState.Idle
            onSuccess()
        }

        //Xuất lỗi
        catch (e:Exception){
            Toast.makeText(context,"Đăng xuất không thành công ,Lỗi $e", Toast.LENGTH_SHORT).show()
        }

    }

}


sealed class NoteState{
    object Idle : NoteState()
    object Loading : NoteState()
    data class Success(val message: String) : NoteState()
    data class Error(val message: String) : NoteState()
}
class NoteViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    //Lưu dữ liệu toàn bộ note
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList: StateFlow<List<Note>> = _noteList

    //Lưu dữ liệu searchbox đã tìm ra
    private val _filteredNotes = MutableStateFlow<List<Note>>(emptyList())
    val filteredNotes: StateFlow<List<Note>> = _filteredNotes

    //Lưu trạng thái của Note
    private val _saveStatus = MutableStateFlow<NoteState>(NoteState.Idle)
    val saveStatus: StateFlow<NoteState> = _saveStatus

    fun saveNote(note: Note) {
        _saveStatus.value = NoteState.Loading
        viewModelScope.launch {
            val userId = auth.currentUser?.uid

            if (userId == null) {
                _saveStatus.value = NoteState.Error("Người dùng chưa đăng nhập")
                return@launch
            }

            val notesCollection = db.collection("users")
                .document(userId)
                .collection("notes")

            if (note.id.isNotBlank()) {
                // 🔁 Nếu có ID => cập nhật
                notesCollection.document(note.id)
                    .set(note)
                    .addOnSuccessListener {
                        _saveStatus.value = NoteState.Success("Đã cập nhật thành công")
                        getNotes()
                    }
                    .addOnFailureListener { e ->
                        _saveStatus.value = NoteState.Error("Cập nhật thất bại: ${e.message}")
                    }
            } else {
                // 🆕 Không có ID => tạo mới với ID tự sinh
                val newDocRef = notesCollection.document() // tạo document với ID mới
                val noteWithId = note.copy(id = newDocRef.id) // gán id vào note

                newDocRef.set(noteWithId)
                    .addOnSuccessListener {
                        _saveStatus.value = NoteState.Success("Đã lưu thành công")
                        getNotes()
                    }
                    .addOnFailureListener { e ->
                        _saveStatus.value = NoteState.Error("Lưu thất bại: ${e.message}")
                    }
            }
        }
    }

    fun getNotes() {
        _saveStatus.value = NoteState.Loading

        viewModelScope.launch {
            _saveStatus.value = NoteState.Loading

            val userId = auth.currentUser?.uid
            if (userId == null) {
                _saveStatus.value = NoteState.Error("Người dùng chưa đăng nhập")
                return@launch
            }

            try {
                val result = db.collection("users")
                    .document(userId)
                    .collection("notes")
                    .get()
                    .await()

                val notes = result.map { it.toObject(Note::class.java) }
                _noteList.value = notes
                _saveStatus.value = NoteState.Success("Nhận ghi chú thành công")

            } catch (e: Exception) {
                _saveStatus.value = NoteState.Error("Lỗi khi lấy ghi chú: ${e.message}")
            }
        }
    }

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote

    fun selectNote(note: Note) {
        _selectedNote.value = note
        Log.d("EditNoteScreen", "Note: ${selectedNote.value}")
    }

    fun searchNotes(query: String) {
        val filtered = _noteList.value.filter { note ->
            note.title.contains(query, ignoreCase = true) ||
                    note.content.contains(query, ignoreCase = true)
        }
        filtered.forEachIndexed { index, note ->
            Log.d("searchNotes", "[$index] Title: ${note.title}, Content: ${note.content}")
        }

        _filteredNotes.value = filtered
    }

    fun deleteNote(note: Note) {
        val userId = auth.currentUser?.uid

        viewModelScope.launch {
            if (userId == null) {
                _saveStatus.value = NoteState.Error("Người dùng chưa đăng nhập")
                return@launch
            }

            if (note.id.isBlank()) {
                _saveStatus.value = NoteState.Error("Ghi chú không hợp lệ")
                return@launch
            }

            db.collection("users")
                .document(userId)
                .collection("notes")
                .document(note.id)
                .delete()
                .addOnSuccessListener {
                    _saveStatus.value = NoteState.Success("Xóa ghi chú thành công")
                    getNotes() // cập nhật danh sách sau khi xóa
                }
                .addOnFailureListener { e ->
                    _saveStatus.value = NoteState.Error("Xóa thất bại: ${e.message}")
                }
        }
    }
}
class ThemePreference(context: Context)
{

    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveDarkMode(isDark: Boolean) {
        prefs.edit().putBoolean("dark_mode", isDark).apply()
    }

    fun isDarkMode(): Boolean {
        return prefs.getBoolean("dark_mode", false)
    }
}