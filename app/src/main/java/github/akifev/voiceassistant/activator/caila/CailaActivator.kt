package github.akifev.voiceassistant.activator.caila

import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.CailaNLUSettings
import github.akifev.voiceassistant.BuildConfig

private val jaicpApiToken = BuildConfig.JAICP_API_TOKEN

val cailaNLUSettings = CailaNLUSettings(jaicpApiToken)

fun createCailaActivator(): CailaIntentActivator.Factory {
    return CailaIntentActivator.Factory(cailaNLUSettings)
}