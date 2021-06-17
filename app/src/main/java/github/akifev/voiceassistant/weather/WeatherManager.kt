package github.akifev.voiceassistant.weather

import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.channel.aimybox.aimybox
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.helpers.logging.logger
import com.justai.jaicf.reactions.Reactions
import github.akifev.voiceassistant.BuildConfig
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.json.*
import kotlin.math.abs

class WeatherManager(private val actionContext: ActionContext<ActivatorContext, BotRequest, Reactions>) {
    private val openWeatherApi = OpenWeatherApi()

    fun getWeatherByCityName(cityName: String) {
        try {
            val response: String? = runBlocking {
                withTimeoutOrNull(5000) {
                    openWeatherApi.getWeatherByCityName(
                        cityName = cityName,
                        apiKey = BuildConfig.OPENWEATHER_API_TOKEN,
                        units = OpenWeatherApi.UnitsOfMeasurement.METRIC,
                        languageCode = "ru"
                    )
                }
            }
            if (response == null) {
                actionContext.reactions.say("Не могу загрузить информацию о погоде.")
                return
            }

            val element = Json.parseToJsonElement(response)
            val description: String = element
                .jsonObject["weather"]!!.jsonArray[0]
                .jsonObject["description"]?.jsonPrimitive!!.content
            val temperature: Int = element
                .jsonObject["main"]!!
                .jsonObject["temp"]?.jsonPrimitive?.float!!.toInt()

            actionContext.reactions.say(
                "В городе $cityName $temperature ${degreesWithValidEnding(temperature)}, $description."
            )
        } catch (e: Throwable) {
            actionContext.logger.error("Error when loading weather info")
            actionContext.reactions.say("Ошибка при загрузке информации о погоде.")
            actionContext.reactions.aimybox?.endConversation()
        }
    }

    private fun degreesWithValidEnding(temperature: Int): String {
        val n = abs(temperature)
        val z = n % 10
        val yz = n % 100

        return when {
            z == 1 && yz != 11 -> "градус"
            z in 2..4 && yz !in 12..14 -> "градуса"
            else -> "градусов"
        }
    }
}