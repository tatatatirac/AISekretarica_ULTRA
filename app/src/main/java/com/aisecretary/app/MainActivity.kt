package com.aisecretary.app
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.aisecretary.app.voice.TtsManager
import com.aisecretary.app.voice.VoiceForegroundService

class MainActivity : ComponentActivity() {
    private lateinit var tts: TtsManager
    
    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TtsManager(this)
        
        // traži dozvole odmah
        val perms = mutableListOf(Manifest.permission.RECORD_AUDIO)
        if (Build.VERSION.SDK_INT >= 33) {
            perms.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        requestPermission.launch(perms.toTypedArray())

        setContent {
            MaterialTheme {
                var listening by remember { mutableStateOf(false) }
                Scaffold(
                    floatingActionButton = {
                        ExtendedFloatingActionButton(onClick = {
                            listening = !listening
                            val intent = Intent(this, VoiceForegroundService::class.java)
                            if (listening) {
                                if (Build.VERSION.SDK_INT >= 26) {
                                    startForegroundService(intent)
                                } else {
                                    startService(intent)
                                }
                            } else {
                                stopService(intent)
                            }
                        }) { Text(if (listening) "Zaustavi" else "Pokreni") }
                    }
                ) { p ->
                    Column(Modifier.padding(p).padding(24.dp)) {
                        Text("AI Sekretarica", style = MaterialTheme.typography.headlineLarge)
                        Spacer(Modifier.height(8.dp))
                        Text("Prvo dozvoli mikrofon kad pita")
                        Spacer(Modifier.height(8.dp))
                        Text("Komande: pročitaj poruke, zakaži sastanak")
                        Spacer(Modifier.height(24.dp))
                        Button(onClick = { tts.speak("Sekretarica je spremna") }) { 
                            Text("Test") 
                        }
                    }
                }
            }
        }
    }
}
