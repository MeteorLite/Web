package meteor.web.controller

import meteor.web.model.dto.TransportLinkDto
import meteor.web.service.TransportService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("transports")
class TransportController(
    val transportService: TransportService,
) {
    @GetMapping
    fun getTransports() = transportService.getAll()

    @PostMapping
    fun saveTransports(@RequestBody transports: List<TransportLinkDto>) = transportService.saveAll(transports)
}
