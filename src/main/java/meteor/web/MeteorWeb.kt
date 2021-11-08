package meteor.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MeteorWeb

fun main(args: Array<String>) {
    runApplication<MeteorWeb>(*args)
}
