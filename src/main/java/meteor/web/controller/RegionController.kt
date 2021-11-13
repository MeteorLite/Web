package meteor.web.controller

import meteor.web.collisions.CollisionMap
import meteor.web.config.RegionConfig
import meteor.web.model.TileFlag
import meteor.web.repository.RegionRepository
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.GZIPInputStream

@RestController
@RequestMapping("regions")
class RegionController(
    private val collisionMap: CollisionMap,
    private val regionRepository: RegionRepository,
) {
    val dbVersion = 1

    @GetMapping("/{x}/{y}/{z}")
    fun getTile(@PathVariable x: Int, @PathVariable y: Int, @PathVariable z: Int) =
        regionRepository.findFirstByXAndYAndZ(x, y, z)

    @GetMapping("/{regionId}")
    fun getByRegionId(@PathVariable regionId: Int) = regionRepository.findByRegion(regionId)

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
