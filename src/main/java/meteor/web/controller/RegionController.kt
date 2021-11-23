package meteor.web.controller

import meteor.web.collisions.CollisionMap
import meteor.web.model.TileFlag
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.nio.file.Files

@RestController
@RequestMapping("regions")
class RegionController(
    private val collisionMap: CollisionMap,
) {
    val dbVersion = 1

    @PostMapping("/{version}")
    fun saveAll(@PathVariable version: Int, @RequestBody tiles: List<TileFlag>) {
        if (version != dbVersion) {
            return
        }

        tiles.forEach { tileFlag ->
            val region = tileFlag.region ?: return@forEach
            val x = tileFlag.x ?: return@forEach
            val y = tileFlag.y ?: return@forEach
            val z = tileFlag.z ?: return@forEach

            if (collisionMap.regions[region] == null) {
                collisionMap.createRegion(region)
            }

            if (tileFlag.isObstacle()) {
                collisionMap.set(x, y, z, 0, false)
                collisionMap.set(x, y, z, 1, false)
            } else {
                collisionMap.set(x, y, z, 0, true)
                collisionMap.set(x, y, z, 1, true)

                if (!tileFlag.north) {
                    collisionMap.set(x, y, z, 0, false)
                }

                if (!tileFlag.east) {
                    collisionMap.set(x, y, z, 1, false)
                }
            }
        }

        collisionMap.writeToFile().also { newFile ->
            Files.write(newFile.toPath(), newFile.readBytes())
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getAll(): FileSystemResource {
        return FileSystemResource("./regions")
    }
}
