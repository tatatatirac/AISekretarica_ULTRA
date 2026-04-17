package com.aisecretary.app.voice
import android.content.Context
class VoiceCommandProcessor(context:Context, private val tts:TtsManager){
    fun handle(t:String){
        val l=t.lowercase()
        when{
            "pročitaj" in l -> tts.speak("Nema novih poruka")
            "zakaži" in l -> tts.speak("Sastanak zakazan")
            "podseti" in l -> tts.speak("Podsetnik postavljen")
            else -> tts.speak("Reci pročitaj poruke")
        }
    }
}