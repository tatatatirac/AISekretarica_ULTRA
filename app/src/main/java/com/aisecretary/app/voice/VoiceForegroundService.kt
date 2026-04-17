package com.aisecretary.app.voice
import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
class VoiceForegroundService: Service(){
    private lateinit var speech:SpeechRecognizerManager
    private lateinit var tts:TtsManager
    override fun onCreate(){
        super.onCreate()
        val ch="voice"
        val nm=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(NotificationChannel(ch,"Sekretarica",NotificationManager.IMPORTANCE_LOW))
        startForeground(1, NotificationCompat.Builder(this,ch).setContentTitle("Slušam").setSmallIcon(android.R.drawable.ic_btn_speak_now).build())
        tts=TtsManager(this)
        val proc=VoiceCommandProcessor(this,tts)
        speech=SpeechRecognizerManager(this){ proc.handle(it) }
        speech.startContinuous()
        tts.speak("Spremna")
    }
    override fun onBind(i:Intent?)=null
    override fun onDestroy(){ speech.destroy(); tts.shutdown(); super.onDestroy() }
}