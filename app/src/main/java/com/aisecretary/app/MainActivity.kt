package com.aisecretary.app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aisecretary.app.voice.TtsManager

class MainActivity : ComponentActivity() {
    private lateinit var tts: TtsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TtsManager(this)
        setContent {
            MaterialTheme {
                var listening by remember { mutableStateOf(false) }
                Scaffold(
                    floatingActionButton = {
                        ExtendedFloatingActionButton(onClick = {
                            listening = !listening
                            if (listening) startService(android.content.Intent(this, com.aisecretary.app.voice.VoiceForegroundService::class.java))
                            else stopService(android.content.Intent(this, com.aisecretary.app.voice.VoiceForegroundService::class.java))
                        }) { Text(if (listening) "Zaustavi" else "Pokreni") }
                    }
                ) { p ->
                    Column(Modifier.padding(p).padding(24.dp)) {
                        Text("AI Sekretarica", style = MaterialTheme.typography.headlineLarge)
                        Text("Komande: pročitaj poruke, zakaži sastanak")
                        Button(onClick = { tts.speak("Sekretarica je spremna") }) { Text("Test") }
                    }
                }
            }
        }
    }
}