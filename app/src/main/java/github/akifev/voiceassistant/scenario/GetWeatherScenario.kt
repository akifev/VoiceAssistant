package github.akifev.voiceassistant.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import github.akifev.voiceassistant.weather.WeatherManager

val GetWeatherScenario = Scenario {
    state("weather") {
        activators {
            intent("Weather")
            intent("WeatherCity")
        }

        action {
            val city: String? = activator.caila?.slots?.get("city")
                ?: context.temp["city"] as? String
                ?: context.session["city"] as? String
                ?: context.client["city"] as? String

            if (city != null) {
                context.temp["city"] = city
                WeatherManager(this).getWeatherByCityName(city)
            } else {
                reactions.say("В каком городе?")
            }
        }

        state("city") {
            activators {
                intent("City")
            }

            action {
                reactions.go("/weather")
            }
        }
    }
}