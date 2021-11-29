package meteor.web.controller

import meteor.web.model.TileFlag
import meteor.web.service.RegionService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("regions")
class RegionController(
    private val regionService: RegionService
) {
    val dbVersion = 2

    @PostMapping("/{version}")
    fun saveAll(@PathVariable version: Int, @RequestBody tiles: List<TileFlag>) {
        if (version != dbVersion) {
            return
        }

        regionService.save(tiles)
    }

    @GetMapping("/instance/{region}")
    fun instance(@PathVariable region: Int) = regionService.deleteInstancedRegion(region)

    @GetMapping(produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getAll() = regionService.getFile()

    @GetMapping("/unmapped")
    fun getUnmapped() = regionService.getUnmapped()

    @GetMapping("/{region}/{z}")
    fun getRegion(@PathVariable region: Int, @PathVariable z: Int) = regionService.getMappedTiles(region, z)

    @GetMapping("/{region}")
    fun getRegion(@PathVariable region: Int) = regionService.getMappedTiles(region)
}
