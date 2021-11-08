package meteor.web.controller

import meteor.web.model.TileFlag
import meteor.web.service.RegionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("regions")
class RegionController(
    private val service: RegionService
) {
    @PostMapping
    fun save(@RequestBody tileFlags: List<TileFlag>) = service.saveAll(tileFlags)

    @GetMapping
    fun getAll(): List<TileFlag> = service.findAll()

    @GetMapping("/{regionId}")
    fun getByRegionId(@PathVariable regionId: Int) = service.findByRegionId(regionId)
}
