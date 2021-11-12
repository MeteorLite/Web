package meteor.web.controller

import meteor.web.model.TileFlag
import meteor.web.service.RegionService
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.FileInputStream

@RestController
@RequestMapping("regions")
class RegionController(
    private val service: RegionService
) {
    @PostMapping
    fun save(@RequestBody tileFlags: List<TileFlag>) = service.saveAll(tileFlags)

    @GetMapping
    fun getAll(): ResponseEntity<Resource> {
        val file = service.writeToFile()
        val resource = InputStreamResource(FileInputStream(file))

        return ResponseEntity.ok()
            .contentLength(file.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource)
    }

    @GetMapping("/{x}/{y}/{z}")
    fun getTile(@PathVariable x: Int, @PathVariable y: Int, @PathVariable z: Int) = service.findTile(x, y, z)

    @GetMapping("/{regionId}")
    fun getByRegionId(@PathVariable regionId: Int) = service.findByRegionId(regionId)
}
