package com.example.exameneventos2

import android.os.Bundle
import android.widget.Toast
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

class AddClassActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenEventos2Theme {
                AddClassScreen()
            }
        }
    }
}

@Composable
fun AddClassScreen() {
    var nombre by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    val horas = (8..14).map { "$it:00" }
    val dias = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    var expandedInicio by remember { mutableStateOf(false) }
    var expandedFin by remember { mutableStateOf(false) }
    var expandedDia by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

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
                text = "Mi Horario - Agregar Asignatura",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box {
                TextField(
                    value = horaInicio,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hora de inicio") },
                    trailingIcon = {
                        IconButton(onClick = { expandedInicio = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expandedInicio,
                    onDismissRequest = { expandedInicio = false }
                ) {
                    horas.forEach { hora ->
                        DropdownMenuItem(
                            text = { Text(hora) },
                            onClick = {
                                horaInicio = hora
                                expandedInicio = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box {
                TextField(
                    value = horaFin,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hora de fin") },
                    trailingIcon = {
                        IconButton(onClick = { expandedFin = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expandedFin,
                    onDismissRequest = { expandedFin = false }
                ) {
                    horas.forEach { hora ->
                        DropdownMenuItem(
                            text = { Text(hora) },
                            onClick = {
                                horaFin = hora
                                expandedFin = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box {
                TextField(
                    value = dia,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Día") },
                    trailingIcon = {
                        IconButton(onClick = { expandedDia = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expandedDia,
                    onDismissRequest = { expandedDia = false }
                ) {
                    dias.forEach { diaItem ->
                        DropdownMenuItem(
                            text = { Text(diaItem) },
                            onClick = {
                                dia = diaItem
                                expandedDia = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    db.collection("Clase")
                        .whereEqualTo("horaInicio", horaInicio)
                        .get()
                        .addOnSuccessListener { documents ->
                            if (documents.isEmpty) {
                                val clase = Clase(nombre, horaInicio, horaFin, dia)
                                db.collection("Clase")
                                    .add(clase)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Clase añadida", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(context, "Error al añadir clase: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(context, "Ya existe una clase con esa hora de inicio", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error al verificar la hora de inicio: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }) {
                    Text("Aceptar")
                }
                Button(onClick = { /* TODO: Handle cancel action */ }) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddClassScreenPreview() {
    ExamenEventos2Theme {
        AddClassScreen()
    }
}