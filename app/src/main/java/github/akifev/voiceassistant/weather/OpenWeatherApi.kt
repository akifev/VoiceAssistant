package github.akifev.voiceassistant.weather

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Class to provide interaction with OpenWeather API
 */
class OpenWeatherApi {
    private val httpClient: HttpClient = HttpClient(CIO)

    enum class ResponseFormat {
        JSON, XML, HTML;

        override fun toString(): String {
            return super.toString().toLowerCase()
        }
    }

    enum class UnitsOfMeasurement {
        STANDART, METRIC, IMPERIAL;

        override fun toString(): String {
            return super.toString().toLowerCase()
        }
    }

    /**
     * @param cityName city name.
     * @param apiKey API key.
     * @param responseFormat response format.
     * @param units units of measurement.
     * @param languageCode language code.
     *
     * @see [OpenWeather](https://openweathermap.org/current#name)
     */
    suspend fun getWeatherByCityName(
        cityName: String,
        apiKey: String,
        responseFormat: ResponseFormat = ResponseFormat.JSON,
        units: UnitsOfMeasurement = UnitsOfMeasurement.STANDART,
        languageCode: String
    ): String {
        return httpClient.request {
            url("http://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$apiKey&mode=$responseFormat&units=$units&lang=$languageCode")
            method = HttpMethod.Get
        }
    }
}