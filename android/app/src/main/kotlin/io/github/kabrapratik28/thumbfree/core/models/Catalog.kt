package io.github.kabrapratik28.thumbfree.core.models

data class ModelFile(
    val id: String,
    val fileName: String,
    val sizeBytes: Long,
    val sha256: String,
    val languages: List<String>,
    val revision: String = "",
    /** It runs the live preview's stream, tested (Settings > Bubble > Show words while I speak). */
    val livePreview: Boolean = false,
) {
    /**
     * The language the engine is told and the text's cleanup assumes: a one-language model's, or null for a multilingual
     * model, which hears which language is spoken by itself.
     */
    val languageHint: String? get() = languages.singleOrNull()
}

/**
 * Models ThumbFree knows: Parakeet Unified EN (Q8_0), the default and the most accurate for English; Parakeet TDT 0.6B v3
 * (Q8_0) and Qwen3-ASR 0.6B (Q8_0), the two multilingual ones; Canary 180M Flash (Q8_0) to try instead; and Granite
 * Speech 5.0 470M TurboCTC (Q8_0), the smallest and the fastest, for English.
 */
object Catalog {
    val PARAKEET_UNIFIED_Q8 = ModelFile(
        id = "handy-computer/parakeet-unified-en-0.6b-gguf/parakeet-unified-en-0.6b-Q8_0.gguf",
        fileName = "parakeet-unified-en-0.6b-Q8_0.gguf",
        sizeBytes = 731_357_568,
        sha256 = "4b50b6dd862bf6e346929aaf4f5eaacec003bfa3f56462d6c874b41ef2f38795",
        languages = listOf("en"),
        revision = "7e948f21b7bdbac698d3318db9d350f1096f3b6c",
        livePreview = true, // its buffered stream runs at 70-13-4, as tested
    )

    // The Hugging Face GGUF of nvidia/canary-180m-flash (CC BY 4.0). At this revision the file is byte-identical to the
    // local conversion that was measured before it was added.
    val CANARY_180M_FLASH_Q8 = ModelFile(
        id = "handy-computer/canary-180m-flash-gguf/canary-180m-flash-Q8_0.gguf",
        fileName = "canary-180m-flash-Q8_0.gguf",
        sizeBytes = 218_447_552,
        sha256 = "e13c7f5d0952b056a027cfffec13e3a3a134d1608babed24f983568f141e297c",
        languages = listOf("en"),
        revision = "456e6049062ecf06f1a0f4607f2ee3dc80ebbf8a",
    )

    // The Hugging Face GGUF of nvidia/parakeet-tdt-0.6b-v3 (CC BY 4.0), in its general.languages order: 25 European
    // languages, the one spoken detected by the model itself (stt.capability.lang_detect; it takes no language prompt).
    val PARAKEET_TDT_V3_Q8 = ModelFile(
        id = "handy-computer/parakeet-tdt-0.6b-v3-gguf/parakeet-tdt-0.6b-v3-Q8_0.gguf",
        fileName = "parakeet-tdt-0.6b-v3-Q8_0.gguf",
        sizeBytes = 739_508_576,
        sha256 = "5859f77944efcd8eafa23a6350731960b2b55b2203df51f319665c807d802cc7",
        languages = listOf(
            "bg", "hr", "cs", "da", "nl", "en", "et", "fi", "fr", "de", "el", "hu", "it", "lv", "lt", "mt", "pl", "pt", "ro",
            "ru", "sk", "sl", "es", "sv", "uk",
        ),
        revision = "90f082450fcbacdb54e5900c44ef697c9ea59622",
        livePreview = false, // offline only: its streaming is untested
    )

    // The Hugging Face GGUF of ibm-granite/granite-speech-5.0-470m-turboctc (Apache 2.0). A CTC model: it reads the audio
    // in one pass, so it is the smallest of the four and the fastest, and the only one whose text comes back without
    // capitals or punctuation. English only, and it has no stream, so no live preview.
    val GRANITE_SPEECH_470M_Q8 = ModelFile(
        id = "handy-computer/granite-speech-5.0-470m-turboctc-gguf/granite-speech-5.0-470m-turboctc-Q8_0.gguf",
        fileName = "granite-speech-5.0-470m-turboctc-Q8_0.gguf",
        sizeBytes = 505_606_496,
        sha256 = "0408fe0b33be19af5be423d8ba6b43768608b6f065e1ac97b78c84b55c140738",
        languages = listOf("en"),
        revision = "0cf5be9f4bcd479989e6a24d142094d34d552daa",
        livePreview = false, // it has no stream at all
    )

    // The Hugging Face GGUF of Qwen/Qwen3-ASR-0.6B (Apache 2.0). An encoder with a small language model for a decoder, so
    // it is the biggest and the slowest of the five, and it hears which of its 30 languages is spoken by itself. This port
    // rejects a language hint outright, so the catalog lists the 30 it detects and languageHint stays null: the app then
    // tells the engine nothing and the model decides, as it already does for Parakeet TDT 0.6B v3. No stream, so no live
    // preview.
    val QWEN3_ASR_0_6B_Q8 = ModelFile(
        id = "handy-computer/Qwen3-ASR-0.6B-gguf/Qwen3-ASR-0.6B-Q8_0.gguf",
        fileName = "Qwen3-ASR-0.6B-Q8_0.gguf",
        sizeBytes = 850_423_456,
        sha256 = "f081b2d5e23bd669d92cc331d722a8a0681943b8e6f34b48996fd5c319b5acd8",
        languages = listOf(
            "zh", "en", "yue", "ar", "de", "fr", "es", "pt", "id", "it", "ko", "ru", "th", "vi", "ja", "tr", "hi",
            "ms", "nl", "sv", "da", "fi", "pl", "cs", "fil", "fa", "el", "hu", "mk", "ro",
        ),
        revision = "7e7c69a577fa2725dfedbe34b5e999336e30a19b",
        livePreview = false, // it has no stream
    )

    val all: List<ModelFile> =
        listOf(PARAKEET_UNIFIED_Q8, PARAKEET_TDT_V3_Q8, CANARY_180M_FLASH_Q8, GRANITE_SPEECH_470M_Q8, QWEN3_ASR_0_6B_Q8)

    fun byId(id: String): ModelFile? = all.firstOrNull { it.id == id }
}
