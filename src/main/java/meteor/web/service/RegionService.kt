package meteor.web.service

import meteor.web.collisions.GlobalCollisionMap
import meteor.web.model.TileFlag
import meteor.web.repository.RegionRepository
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

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

    fun findTile(x: Int, y: Int, z: Int) = regionRepository.findFirstByXAndYAndZ(x, y, z)

    fun writeToFile(): File {
        val all = findAll()
        val map = GlobalCollisionMap()

        val regions = all.distinctBy { it.region }.map { it.region!! }
        for (region in regions) {
            map.createRegion(region)
        }

        for (tileFlag in all) {
            val north = !tileFlag.isObstacle() && !tileFlag.isWalled(TileFlag.Companion.Direction.NORTH)
            val east = !tileFlag.isObstacle() && !tileFlag.isWalled(TileFlag.Companion.Direction.EAST)

            map.set(tileFlag.x!!, tileFlag.y!!, tileFlag.z!!, 0, north)
            map.set(tileFlag.x, tileFlag.y, tileFlag.z, 1, east)
        }

        return map.writeToFile()
    }
}
