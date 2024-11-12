package com.example.loginapp.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.loginapp.AuthViewModel
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu


@Composable
fun SignUpPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){


    /**
     * The by keyword here is part of Kotlin's property delegation.
     * It lets you delegate a property's behavior to another object.
     * In this case, email and password are delegating to
     * remember { mutableStateOf("") },
     * which handles state for those variables.
     * */
    var email by remember {
        mutableStateOf("")
    }

    var expanded by remember { mutableStateOf(false) }


    /**
     * remember
     *
     * It helps ensure that the state (like email and password)
     * persists across recompositions
     * and is not reset every time the UI updates.
     * */

    var password by remember {
        mutableStateOf("")
    }

    var userType by remember { mutableStateOf("Patient") } // Default user type
    var authNumber by remember { mutableStateOf("") } // For doctors
    var selectedDoctor by remember { mutableStateOf("") } // For patients

    // Placeholder for the list of doctors
    val doctorList = listOf("Dr. Smith", "Dr. Johnson", "Dr. Williams")

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // User Type Selection
        Row {
            RadioButton(
                selected = userType == "Patient",
                onClick = { userType = "Patient" }
            )
            Text(text = "Patient")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = userType == "Doctor",
                onClick = { userType = "Doctor" }
            )
            Text(text = "Doctor")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Common Fields
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Conditional Fields
        if (userType == "Doctor") {
            OutlinedTextField(
                value = authNumber,
                onValueChange = { authNumber = it },
                label = { Text(text = "Authentication Number") }
            )
        } else {
            // For Patients: Doctor Selection
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                doctorList.forEach { doctor ->
                    DropdownMenuItem(onClick = {
                        selectedDoctor = doctor
                        expanded = false // Close the dropdown when a doctor is selected
                    }) {
                        Text(text = doctor)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Handle sign-up based on user type
            },
        ) {
            Text(text = "Create Account")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text(text = "Already have an account? Login")
        }
    }

}