package github.akifev.voiceassistant.activator.caila

import com.justai.jaicf.activator.caila.client.CailaKtorClient
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Class for testing CAILA intents
 */
class CailaNLUTest {
    private val cailaClient = CailaKtorClient(
        cailaNLUSettings.accessToken,
        cailaNLUSettings.cailaUrl,
        cailaNLUSettings.classifierNBest
    )

    private fun getIntent(query: String) = cailaClient.simpleInference(query)?.intent
    private fun getEntities(query: String) = cailaClient.entitiesLookup(query)?.entities

    private data class Query(
        val query: String,
        val expectedIntentName: String,
        val expectedSlots: Map<String, String>? = null
    )

    private fun checkQueries(vararg queries: Query) {
        queries.forEach { query ->
            val intent = getIntent(query.query)
            assertEquals(
                query.expectedIntentName,
                intent?.name,
                "Unexpected intent on query `${query.query}`"
            )
            val slots = intent?.slots?.map { slot -> slot.name }?.toHashSet()
            if (query.expectedSlots != null || slots != null) {
                val entities = getEntities(query.query)
                val entitiesMap = entities
                    ?.filter { slots?.contains(it.entity) == true && it.default == true }
                    ?.map { Pair(it.entity, it.value) }
                    ?.toMap()
                assertEquals(
                    query.expectedSlots,
                    entitiesMap,
                    "Unexpected slots `${query.expectedSlots} on query `${query.query}`"
                )
            }
        }
    }

    @Test
    fun `test Hello intent`() {
        checkQueries(
            Query("Привет", "Hello"),
            Query("Доброе утро", "Hello"),
            Query("Добрый день", "Hello"),
            Query("Добрый вечер", "Hello"),
            Query("Здравствуй", "Hello"),
            Query("Здравствуйте", "Hello"),
            Query("Приветствую", "Hello"),
            Query("Здарова", "Hello"),
            Query("Хай", "Hello")
        )
    }

    @Test
    fun `test Bye intent`() {
        checkQueries(
            Query("Пока", "Bye"),
            Query("Давай пока", "Bye"),
            Query("До свидания", "Bye"),
            Query("Всего доброго", "Bye"),
            Query("Всего хорошего", "Bye"),
            Query("Прощай", "Bye"),
            Query("Замолчи", "Bye"),
            Query("Заткнись", "Bye"),
            Query("Уходи", "Bye")
        )
    }

    @Test
    fun `test City intent`() {
        checkQueries(
            Query("Москва", "City", mapOf("city" to "Москва")),
            Query("МСК", "City", mapOf("city" to "Москва")),

            Query("Санкт-Петербург", "City", mapOf("city" to "Санкт-Петербург")),
            Query("Питер", "City", mapOf("city" to "Санкт-Петербург")),
            Query("Петербург", "City", mapOf("city" to "Санкт-Петербург")),
            Query("Ленинград", "City", mapOf("city" to "Санкт-Петербург")),
            Query("СПб", "City", mapOf("city" to "Санкт-Петербург")),

            Query("Новосибирск", "City", mapOf("city" to "Новосибирск")),
            Query("Новосиб", "City", mapOf("city" to "Новосибирск")),

            Query("Екатеринбург", "City", mapOf("city" to "Екатеринбург")),
            Query("ЕКБ", "City", mapOf("city" to "Екатеринбург")),

            Query("Казань", "City", mapOf("city" to "Казань")),

            Query("Нижний Новгород", "City", mapOf("city" to "Нижний Новгород")),

            Query("Челябинск", "City", mapOf("city" to "Челябинск")),
            Query("Челяб", "City", mapOf("city" to "Челябинск")),

            Query("Самара", "City", mapOf("city" to "Самара")),

            Query("Омск", "City", mapOf("city" to "Омск")),

            Query("Ростов-на-Дону", "City", mapOf("city" to "Ростов-на-Дону")),
            Query("Ростов", "City", mapOf("city" to "Ростов-на-Дону"))
        )
    }

    @Test
    fun `test skills intent`() {
        checkQueries(
            Query("Что ты умеешь делать", "Skills"),
            Query("Что ты умеешь", "Skills"),
            Query("Что умеешь", "Skills"),
            Query("Что ты можешь делать", "Skills"),
            Query("Что ты можешь", "Skills"),
            Query("Что можешь", "Skills")
        )
    }

    @Test
    fun `test Weather intent`() {
        checkQueries(
            Query("Как погодка", "Weather", emptyMap()),
            Query("Погода", "Weather", emptyMap()),
            Query("Какая погода", "Weather", emptyMap())
        )
    }

    @Test
    fun `test WeatherCity intent`() {
        checkQueries(
            Query("Как погодка в Москве", "WeatherCity", mapOf("city" to "Москва")),
            Query("Погода СПб", "WeatherCity", mapOf("city" to "Санкт-Петербург")),
            Query("Погодка в Питере", "WeatherCity", mapOf("city" to "Санкт-Петербург")),
            Query("Погодка в Ростове-на-Дону", "WeatherCity", mapOf("city" to "Ростов-на-Дону")),
            Query("Какая погода в Ростове", "WeatherCity", mapOf("city" to "Ростов-на-Дону")),
            Query("ЕКБ погода", "WeatherCity", mapOf("city" to "Екатеринбург")),
            Query("Какая погода в Нижнем Новгороде", "WeatherCity", mapOf("city" to "Нижний Новгород"))
        )
    }
}