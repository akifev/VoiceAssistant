package github.akifev.voiceassistant

import android.app.Application
import android.content.Context
import com.justai.aimybox.Aimybox
import com.justai.aimybox.components.AimyboxProvider
import com.justai.aimybox.core.Config
import com.justai.aimybox.dialogapi.jaicf.JAICFDialogApi
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech
import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.ActivatorFactory
import github.akifev.voiceassistant.activator.caila.createCailaActivator
import github.akifev.voiceassistant.scenario.MainScenario
import java.util.*

class AimyboxApplication : Application(), AimyboxProvider {
    override val aimybox by lazy { createAimybox(this) }

    private fun createAimybox(context: Context): Aimybox {
        val unitId = UUID.randomUUID().toString()

        val textToSpeech = GooglePlatformTextToSpeech(context, Locale.forLanguageTag("RU"))
        val speechToText = GooglePlatformSpeechToText(context, Locale.forLanguageTag("RU"))

        val activators: Array<ActivatorFactory> = arrayOf(
            createCailaActivator()
        )

        val engine = BotEngine(
            scenario = MainScenario,
            activators = activators
        )

        val dialogApi = JAICFDialogApi(unitId, engine)

        return Aimybox(Config.create(speechToText, textToSpeech, dialogApi))
    }
}