package github.akifev.voiceassistant.scenario

import com.justai.jaicf.test.ScenarioTest
import org.junit.jupiter.api.Test

/**
 * Class for testing HFSM in [MainScenario]
 */
class MainScenarioTest : ScenarioTest(MainScenario) {
    @Test
    fun `say hello`() {
        /*
        - Привет
        - Привет!
         */
        intent("Hello") endsWithState "/hello"
    }

    @Test
    fun `get skills`() {
        /*
        - Что ты умеешь
        - Могу подсказать погоду.
         */
        intent("Skills") endsWithState "/skills"
    }

    @Test
    fun `say goodbye`() {
        /*
        - Пока
        - До встречи!
         */
        intent("Bye") endsWithState "/bye"
    }

    @Test
    fun `get city`() {
        /*
        - Петербург
        - Этот город я знаю! Могу что-то подсказать.
         */
        intent("City") endsWithState "/city"
    }
}