package github.akifev.voiceassistant.activator.caila

import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.CailaNLUSettings
import github.akifev.voiceassistant.BuildConfig

fun createCailaActivator(): CailaIntentActivator.Factory {
    val accessToken = BuildConfig.JAICP_API_TOKEN

    return CailaIntentActivator.Factory(CailaNLUSettings(accessToken))
}