package meteor.web.controller

import meteor.web.collisions.CollisionMap
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.nio.file.Files
import java.util.zip.GZIPInputStream

@RestController
@RequestMapping("regions")
class RegionController(
    private val collisionMap: CollisionMap
) {
    val dbVersion = 2

    @PutMapping("/{version}")
    fun file(@PathVariable version: Int, @RequestParam("file") file: MultipartFile) {
        if (version != dbVersion) {
            return
        }

        val newMap = CollisionMap(GZIPInputStream(ByteArrayInputStream(file.bytes)).readAllBytes())
        for ((regionId, region) in newMap.regions.withIndex()) {
            if (region == null) {
                continue
            }

            collisionMap.regions[regionId] = region
        }

        collisionMap.writeToFile().also { newFile ->
            Files.write(newFile.toPath(), newFile.readBytes())
        }
    }

    @GetMapping
    fun getAll(): ResponseEntity<Resource> {
        val file = collisionMap.writeToFile()
        val resource = InputStreamResource(FileInputStream(file))

        return ResponseEntity.ok()
            .contentLength(file.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource)
    }
}
