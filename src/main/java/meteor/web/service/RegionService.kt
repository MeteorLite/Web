package meteor.web.service

import meteor.web.collisions.GlobalCollisionMap
import meteor.web.model.TileFlag
import meteor.web.repository.RegionRepository
import org.springframework.stereotype.Service
import java.io.File

@Service
class RegionService(
    val regionRepository: RegionRepository
) {
    fun saveAll(tileFlags: List<TileFlag>) {
        regionRepository.saveAll(tileFlags)
    }

    fun findAll(): List<TileFlag> {
        return regionRepository.findAll()
    }

    fun findByRegionId(regionId: Int): List<TileFlag> {
        return regionRepository.findByRegion(regionId)
    }

    fun writeToFile(): File {
        val all = findAll()
        val map = GlobalCollisionMap()

        val regions = all.distinctBy { it.region }.map { it.region!! }
        for (region in regions) {
            map.createRegion(region)
        }

        for (tileFlag in all) {
            val north = (tileFlag.flag!! and 0x2) == 0
            val east = (tileFlag.flag and 0x8) == 0

            map.set(tileFlag.x!!, tileFlag.y!!, tileFlag.z!!, 0, north)
            map.set(tileFlag.x, tileFlag.y, tileFlag.z, 1, east)
        }

        return map.writeToFile()
    }
}
