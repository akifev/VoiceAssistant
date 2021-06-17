package github.akifev.voiceassistant.scenario

import com.justai.jaicf.test.ScenarioTest
import org.junit.jupiter.api.Test

/**
 * Class for testing HFSM in [GetWeatherScenario]
 */
class GetWeatherScenarioTest : ScenarioTest(GetWeatherScenario) {
    @Test
    fun `weather without city`() {
        /*
        - Как погодка
        - В каком городе?
        - Ростов
        - В городе Ростов-на-Дону ...
        - А в Питере
        - В городе Санкт-Петербург ...
         */
        intent("Weather") responds "В каком городе?"
        intent("City") endsWithState "/weather"
        intent("City") endsWithState "/weather"
    }

    @Test
    fun `weather with city`() {
        /*
        - Как погодка в Нижнем Новгороде
        - В городе Нижний Новгород ...
         */
        intent("WeatherCity") endsWithState "/weather"
    }
}