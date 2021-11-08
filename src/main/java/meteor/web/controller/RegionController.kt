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
    fun save(@RequestBody tileFlags: List<TileFlag>): ResponseEntity<Any> {
        return try {
            service.saveAll(tileFlags)
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping
    fun getAll(): List<TileFlag> {
        return service.findAll()
    }
}
