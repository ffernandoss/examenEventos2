package com.example.exameneventos2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos2.ui.theme.ExamenEventos2Theme
import com.google.firebase.firestore.FirebaseFirestore

class ViewScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenEventos2Theme {
                ViewScheduleScreen()
            }
        }
    }
}

@Composable
fun ViewScheduleScreen() {
    var selectedDay by remember { mutableStateOf("") }
    val days = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
    var expandedDay by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    var classes by remember { mutableStateOf(listOf<Clase>()) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Mi Horario - Ver Horario",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box {
                TextField(
                    value = selectedDay,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Día") },
                    trailingIcon = {
                        IconButton(onClick = { expandedDay = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expandedDay,
                    onDismissRequest = { expandedDay = false }
                ) {
                    days.forEach { day ->
                        DropdownMenuItem(
                            text = { Text(day) },
                            onClick = {
                                selectedDay = day
                                expandedDay = false
                                db.collection("Clase")
                                    .whereEqualTo("dia", day)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        classes = documents.map { it.toObject(Clase::class.java) }
                                    }
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                classes.forEach { clase ->
                    Text(text = clase.nombre)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewScheduleScreenPreview() {
    ExamenEventos2Theme {
        ViewScheduleScreen()
    }
}