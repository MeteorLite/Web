package meteor.web.config

import meteor.web.collisions.CollisionMap
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.GZIPInputStream

@Configuration
class RegionConfig {
    @Bean
    fun collisionMap(): CollisionMap {
        return CollisionMap(
            GZIPInputStream(
                ByteArrayInputStream(
                    Files.readAllBytes(Path.of("./regions"))
                )
            ).readAllBytes()
        )
    }
}
