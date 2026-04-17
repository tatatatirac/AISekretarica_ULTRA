package com.aisecretary.app.voice
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.Locale
class SpeechRecognizerManager(context: Context, private val onResult:(String)->Unit){
    private val sr = SpeechRecognizer.createSpeechRecognizer(context)
    private val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply{
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "sr-RS")
    }
    init{ sr.setRecognitionListener(object:RecognitionListener{
        override fun onResults(b:Bundle?){ val r=b?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION); if(!r.isNullOrEmpty()) onResult(r[0]); startContinuous() }
        override fun onError(i:Int){ startContinuous() }
        override fun onReadyForSpeech(p:Bundle?){}
        override fun onBeginningOfSpeech(){}
        override fun onRmsChanged(v:Float){}
        override fun onBufferReceived(b:ByteArray?){}
        override fun onEndOfSpeech(){}
        override fun onPartialResults(b:Bundle?){}
        override fun onEvent(a:Int,b:Bundle?){}
    })}
    fun startContinuous(){ sr.startListening(intent) }
    fun destroy(){ sr.destroy() }
}