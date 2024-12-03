package com.example.exameneventos2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exameneventos2.ui.theme.ExamenEventos2Theme
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CurrentClassActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenEventos2Theme {
                CurrentClassScreen()
            }
        }
    }
}

@Composable
fun CurrentClassScreen() {
    val db = FirebaseFirestore.getInstance()
    val currentTime = remember { SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()) }
    val currentDay = remember { SimpleDateFormat("EEEE", Locale.getDefault()).format(Date()) }
    val currentDate = remember { SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date()) }
    var currentClassName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(currentTime, currentDay) {
        db.collection("Clase")
            .whereEqualTo("dia", currentDay)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val horaInicio = document.getString("horaInicio") ?: continue
                    val horaFin = document.getString("horaFin") ?: continue
                    val nombre = document.getString("nombre") ?: continue

                    if (currentTime >= horaInicio && currentTime <= horaFin) {
                        currentClassName = nombre
                        break
                    }
                }
                if (currentClassName == null) {
                    currentClassName = "No hay clase ahora"
                }
            }
            .addOnFailureListener {
                currentClassName = "Error al obtener los datos"
            }
    }

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
                text = "Mi Horario - Qué Toca Ahora",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Día: $currentDay",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Fecha: $currentDate",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Hora: $currentTime",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = currentClassName ?: "Cargando...",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentClassScreenPreview() {
    ExamenEventos2Theme {
        CurrentClassScreen()
    }
}
