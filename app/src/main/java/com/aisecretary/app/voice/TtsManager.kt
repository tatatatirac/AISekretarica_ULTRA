package com.aisecretary.app.voice
import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
class TtsManager(context: Context) {
    private val tts = TextToSpeech(context) { if (it==TextToSpeech.SUCCESS) tts.language = Locale("sr","RS") }
    fun speak(t:String){ tts.speak(t, TextToSpeech.QUEUE_FLUSH, null, null) }
    fun shutdown(){ tts.shutdown() }
}