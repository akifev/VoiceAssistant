package github.akifev.voiceassistant.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.builder.append
import com.justai.jaicf.channel.aimybox.aimybox

val MainScenario = Scenario {
    state("hello") {
        activators {
            intent("Hello")
        }

        action {
            reactions.say("Привет!")
            reactions.buttons("Что ты умеешь?")
        }
    }

    state("bye") {
        activators {
            intent("Bye")
        }

        action {
            reactions.run {
                sayRandom("До встречи!", "До свидания!")
                aimybox?.endConversation()
            }
        }
    }

    state("skills") {
        activators {
            intent("Skills")
        }

        action {
            reactions.say("Могу подсказать погоду.")
            reactions.buttons("Как погодка?")
        }
    }

    state("city") {
        activators {
            intent("City")
        }

        action {
            val city = activator.caila?.slots?.get("city")
            context.temp["city"] = city

            reactions.say("Этот город я знаю! Могу что-то подсказать.")
            reactions.buttons("Погода $city")
        }
    }

    fallback {
        reactions.sayRandom(
            "Извините, у меня не получится ответить.",
            "Простите, я не понимаю."
        )
    }
}.append(GetWeatherScenario)