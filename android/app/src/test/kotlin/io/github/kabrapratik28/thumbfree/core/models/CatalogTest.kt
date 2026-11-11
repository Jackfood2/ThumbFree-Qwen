package io.github.kabrapratik28.thumbfree.core.models

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CatalogTest {
    @Test
    fun unifiedQ8Pins() {
        val model = Catalog.PARAKEET_UNIFIED_Q8

        assertThat(model.id).isEqualTo(
            "handy-computer/parakeet-unified-en-0.6b-gguf/parakeet-unified-en-0.6b-Q8_0.gguf",
        )
        assertThat(model.fileName).isEqualTo("parakeet-unified-en-0.6b-Q8_0.gguf")
        assertThat(model.sizeBytes).isEqualTo(731_357_568)
        assertThat(model.sha256).isEqualTo("4b50b6dd862bf6e346929aaf4f5eaacec003bfa3f56462d6c874b41ef2f38795")
        assertThat(model.languages).isEqualTo(listOf("en"))
        assertThat(Catalog.all).isEqualTo(
            listOf(
                Catalog.PARAKEET_UNIFIED_Q8, Catalog.PARAKEET_TDT_V3_Q8, Catalog.CANARY_180M_FLASH_Q8,
                Catalog.GRANITE_SPEECH_470M_Q8, Catalog.QWEN3_ASR_0_6B_Q8,
            ),
        )
    }

    // The multilingual model, pinned like the others (the Hugging Face API's LFS size and SHA-256 at this revision).
    @Test
    fun multilingualPins() {
        val model = Catalog.PARAKEET_TDT_V3_Q8

        assertThat(model.id).isEqualTo("handy-computer/parakeet-tdt-0.6b-v3-gguf/parakeet-tdt-0.6b-v3-Q8_0.gguf")
        assertThat(model.fileName).isEqualTo("parakeet-tdt-0.6b-v3-Q8_0.gguf")
        assertThat(model.sizeBytes).isEqualTo(739_508_576)
        assertThat(model.sha256).isEqualTo("5859f77944efcd8eafa23a6350731960b2b55b2203df51f319665c807d802cc7")
        assertThat(model.revision).isEqualTo("90f082450fcbacdb54e5900c44ef697c9ea59622")
        // The GGUF's general.languages, in its order.
        assertThat(model.languages).containsExactly(
            "bg", "hr", "cs", "da", "nl", "en", "et", "fi", "fr", "de", "el", "hu", "it", "lv", "lt", "mt", "pl", "pt", "ro",
            "ru", "sk", "sl", "es", "sv", "uk",
        ).inOrder()
    }

    // The English models tell the engine and the cleanup "en"; the multilingual one tells neither anything, since it hears
    // the language itself.
    @Test
    fun languageHint() {
        assertThat(Catalog.PARAKEET_UNIFIED_Q8.languageHint).isEqualTo("en")
        assertThat(Catalog.CANARY_180M_FLASH_Q8.languageHint).isEqualTo("en")
        assertThat(Catalog.GRANITE_SPEECH_470M_Q8.languageHint).isEqualTo("en")
        // 30 languages, so no single hint: the engine rejects one and auto-detects instead.
        assertThat(Catalog.QWEN3_ASR_0_6B_Q8.languageHint).isNull()
        assertThat(Catalog.PARAKEET_TDT_V3_Q8.languageHint).isNull()
    }

    @Test
    fun canaryPins() {
        val model = Catalog.CANARY_180M_FLASH_Q8

        assertThat(model.id).isEqualTo("handy-computer/canary-180m-flash-gguf/canary-180m-flash-Q8_0.gguf")
        assertThat(model.fileName).isEqualTo("canary-180m-flash-Q8_0.gguf")
        assertThat(model.sizeBytes).isEqualTo(218_447_552)
        assertThat(model.sha256).isEqualTo("e13c7f5d0952b056a027cfffec13e3a3a134d1608babed24f983568f141e297c")
        assertThat(model.languages).isEqualTo(listOf("en"))
        assertThat(model.revision).isEqualTo("456e6049062ecf06f1a0f4607f2ee3dc80ebbf8a")
    }

    // The Granite model, pinned like the others (the Hugging Face API's LFS size and SHA-256 at this revision). It has no
    // stream, so it never offers the live preview.
    @Test
    fun granitePins() {
        val model = Catalog.GRANITE_SPEECH_470M_Q8

        assertThat(model.id).isEqualTo(
            "handy-computer/granite-speech-5.0-470m-turboctc-gguf/granite-speech-5.0-470m-turboctc-Q8_0.gguf",
        )
        assertThat(model.fileName).isEqualTo("granite-speech-5.0-470m-turboctc-Q8_0.gguf")
        assertThat(model.sizeBytes).isEqualTo(505_606_496)
        assertThat(model.sha256).isEqualTo("0408fe0b33be19af5be423d8ba6b43768608b6f065e1ac97b78c84b55c140738")
        assertThat(model.languages).isEqualTo(listOf("en"))
        assertThat(model.revision).isEqualTo("0cf5be9f4bcd479989e6a24d142094d34d552daa")
        assertThat(model.livePreview).isFalse()
    }

    // The Qwen3-ASR model, pinned like the others (the Hugging Face API's LFS size and SHA-256 at this revision). It has no
    // stream, so it never offers the live preview.
    @Test
    fun qwen3Pins() {
        val model = Catalog.QWEN3_ASR_0_6B_Q8

        assertThat(model.id).isEqualTo("handy-computer/Qwen3-ASR-0.6B-gguf/Qwen3-ASR-0.6B-Q8_0.gguf")
        assertThat(model.fileName).isEqualTo("Qwen3-ASR-0.6B-Q8_0.gguf")
        assertThat(model.sizeBytes).isEqualTo(850_423_456)
        assertThat(model.sha256).isEqualTo("f081b2d5e23bd669d92cc331d722a8a0681943b8e6f34b48996fd5c319b5acd8")
        assertThat(model.revision).isEqualTo("7e7c69a577fa2725dfedbe34b5e999336e30a19b")
        assertThat(model.livePreview).isFalse()
        // The Qwen3-ASR model card's list of the languages it detects, in its order.
        assertThat(model.languages).containsExactly(
            "zh", "en", "yue", "ar", "de", "fr", "es", "pt", "id", "it", "ko", "ru", "th", "vi", "ja", "tr", "hi",
            "ms", "nl", "sv", "da", "fi", "pl", "cs", "fil", "fa", "el", "hu", "mk", "ro",
        ).inOrder()
    }

    @Test
    fun byId() {
        assertThat(Catalog.byId(Catalog.CANARY_180M_FLASH_Q8.id)).isEqualTo(Catalog.CANARY_180M_FLASH_Q8)
        assertThat(Catalog.byId(Catalog.PARAKEET_UNIFIED_Q8.id)).isEqualTo(Catalog.PARAKEET_UNIFIED_Q8)
        assertThat(Catalog.byId(Catalog.PARAKEET_TDT_V3_Q8.id)).isEqualTo(Catalog.PARAKEET_TDT_V3_Q8)
        assertThat(Catalog.byId(Catalog.GRANITE_SPEECH_470M_Q8.id)).isEqualTo(Catalog.GRANITE_SPEECH_470M_Q8)
        assertThat(Catalog.byId(Catalog.QWEN3_ASR_0_6B_Q8.id)).isEqualTo(Catalog.QWEN3_ASR_0_6B_Q8)
        assertThat(Catalog.byId("nope")).isNull()
    }
}
