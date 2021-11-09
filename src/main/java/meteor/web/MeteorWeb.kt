package meteor.web

import meteor.web.repository.RegionRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = [RegionRepository::class])
class MeteorWeb

fun main(args: Array<String>) {
    runApplication<MeteorWeb>(*args)
}
