package com.example.loginapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel: ViewModel() {

    /**
     * Purpose:
     * This is an instance of Firebase Authentication.
     * It's used to access Firebase's authentication functions,
     * such as getting the current user, signing in, signing out, etc.
     *
     * Why It's Needed:
     * You need auth to check if a user is logged in (auth.currentUser),
     * as well as to perform any authentication-related actions in the app.
     * */
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Purpose:
     * This is a mutable live data object that holds
     * the current authentication state (e.g., authenticated, unauthenticated, loading, error).
     *
     * Why It's Needed:
     * _authState is used within the ViewModel to
     * update the authentication status.
     * It is private to ensure that only AuthViewModel can modify its value.
     * */
    private val _authState = MutableLiveData<AuthState>()

    /**
     * Purpose:
     * This is an immutable LiveData version of _authState.
     * The UI observes authState to react to changes in authentication status.
     *
     *
     * Why It's Needed:
     * By exposing authState as LiveData,
     * only the ViewModel can change the value while the UI can observe it,
     * maintaining separation of concerns.
     * */
    val authState: LiveData<AuthState> = _authState

    fun checkAuthStatus(){
        when (auth.currentUser){
            null -> {
                _authState.value = AuthState.Unauthenticated
            }
            is FirebaseUser -> _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Nie uzupelnij danych logowania")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task ->
            if (task.isSuccessful){
                _authState.value = AuthState.Authenticated
            }
            else {
                _authState.value = AuthState.Error(
                    /**
                     * String? ->, it means that the variable can hold either a String value or null.
                     *
                     * The ?. operator allows you to safely access properties
                     * or methods on an object that might be null.
                     *
                     * If task.exception is null, task.exception?.message will also be null (avoiding a NullPointerException).
                     * If task.exception is not null, then it will proceed to access message.
                     *
                     * If task.exception?.message is null, it will return "Unknown error occurred".
                     * If task.exception?.message has a value, it will use that value.
                     * */
                    task.exception?.message ?: " Wystapił błąd przy logowaniu ")
            }
        }
    }


}

sealed class AuthState{

    /**
     * Since AuthState is a sealed class,
     * each subclass (like Authenticated, Unauthenticated, etc.) is a
     * specific type of AuthState that needs to be explicitly referenced.
     * If you’re trying to use AuthState somewhere without specifying the subclass,
     * Kotlin requires you to initialize it, hence the error.
     * */
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    object Loading: AuthState()

    /**
     * object declarations represent singletons — only one instance of that object exists in memory.
     * Error needs to hold a message,
     * which means each instance of Error might have a different message (like different error details).
     * Therefore, Error needs to be a data class with a message parameter so
     * that multiple instances of Error with different messages can exist.
     * */
    data class Error(val message: String): AuthState()
}