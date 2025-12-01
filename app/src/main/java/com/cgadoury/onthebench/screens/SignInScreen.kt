package com.cgadoury.onthebench.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.cgadoury.onthebench.MainActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Purpose - sign in screen - the initial screen used for authentication
 * @param context: The application context
 * @param modifier: The application modifier
 * @return Unit
 */
@Composable
fun SignInScreen(
    context: Context,
    modifier: Modifier = Modifier,
) {
    var email by remember { mutableStateOf<String>("") }
    var password by remember { mutableStateOf<String>("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize(1f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        TextField(
            value = email,
            onValueChange = {email = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Email")},
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = password,
            onValueChange = {password = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {Text("Password")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = {
                signInEmailPassword(
                    email,
                    password,
                    context,
                    keyboardController
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text("Sign In")
        }

        Button(
            onClick = {
                signInAnonymously(
                    email,
                    password,
                    context,
                    keyboardController
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Sign in as Guest")
        }
    }
}

/**
 * Purpose - perform sign in - perform a sign in using firebase auth
 * @param email: The sign in email
 * @param password: The sign in password
 * @param context: The application context
 * @param keyboardController: The device keyboard controller
 * @return Unit
 */
private fun signInEmailPassword(
    email: String,
    password: String,
    context: Context,
    keyboardController: SoftwareKeyboardController?
): Unit {
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("userID", FirebaseAuth.getInstance().currentUser?.uid)
                context.startActivity(intent)
            }else{
                Toast.makeText(context, "Sign In Failed", Toast.LENGTH_LONG).show()
            }
            keyboardController?.hide()
        }
}

private fun signInAnonymously(
    email: String,
    password: String,
    context: Context,
    keyboardController: SoftwareKeyboardController?
) {
    val auth = FirebaseAuth.getInstance()

    auth.signInAnonymously()
        .addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("userID", FirebaseAuth.getInstance().currentUser?.uid)
                context.startActivity(intent)
            }else{
                Toast.makeText(context, "Sign In Failed", Toast.LENGTH_LONG).show()
            }
            keyboardController?.hide()
        }
}