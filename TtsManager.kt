class TtsManager {
    private lateinit var tts: TextToSpeech

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Initialization success
            } else {
                // Initialization failed
            }
        }
    }

    fun speak(text: String) {
        if (this::tts.isInitialized) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
}