package github.akifev.voiceassistant.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.channel.aimybox.aimybox

val MainScenario = Scenario {
    state("hello") {
        activators {
            intent("Hello")
        }

        action {
            reactions.say("Привет!")
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

    fallback {
        reactions.sayRandom(
            "Извините, у меня не получится ответить.",
            "Простите, я не понимаю."
        )
    }
}