package meteor.web.service

import meteor.web.model.TransportLink
import meteor.web.model.dto.TransportLinkDto
import meteor.web.repository.TransportLinkRepository
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import javax.annotation.PostConstruct

@Service
class TransportService(
    val transportLinkRepository: TransportLinkRepository
) {
    fun saveAll(transports: List<TransportLinkDto>) = transportLinkRepository
        .saveAll(transports.map { TransportLink(it) })
        .map { TransportLinkDto(it) }

    fun getAll() = transportLinkRepository
        .findAll()
        .filter { it.enabled ?: false }
        .map { TransportLinkDto(it) }
        .joinToString("\n")

    @PostConstruct
    fun initTransports() {
        if (Files.notExists(Path.of("./transports.txt"))) {
            return
        }

        val out = mutableListOf<TransportLinkDto>()
        Files.readAllLines(Path.of("./transports.txt")).forEach {
            if (it.isBlank() || it.startsWith("#")) {
                return@forEach
            }

            val split = it.split(" ")
            val action = split[6]
            val idString = split[split.size - 1]
            out.add(
                TransportLinkDto(
                    "${split[0]} ${split[1]} ${split[2]}",
                    "${split[3]} ${split[4]} ${split[5]}",
                    action,
                    it.substringAfter(action).substringBefore(idString).trim(),
                    idString.toInt()
                )
            )
        }

        saveAll(out)
    }
}
