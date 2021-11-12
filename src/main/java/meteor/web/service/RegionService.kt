package meteor.web.service

import meteor.web.collisions.CollisionMap
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

    fun findTile(x: Int, y: Int, z: Int) = regionRepository.findFirstByXAndYAndZ(x, y, z)

    fun writeToFile(): File {
        val tileFlags = findAll()
        val collisionMap = CollisionMap()

        for (tileFlag in tileFlags) {
            if (collisionMap.regions[tileFlag.region!!] == null) {
                collisionMap.createRegion(tileFlag.region)
            }

            if (tileFlag.obstacle) {
                collisionMap.set(tileFlag.x!!, tileFlag.y!!, tileFlag.z!!, 0, false)
                collisionMap.set(tileFlag.x, tileFlag.y, tileFlag.z, 1, false)
                continue
            }

            if (!tileFlag.north) {
                collisionMap.set(tileFlag.x!!, tileFlag.y!!, tileFlag.z!!, 0, false)
            }

            if (!tileFlag.east) {
                collisionMap.set(tileFlag.x!!, tileFlag.y!!, tileFlag.z!!, 1, false)
            }
        }

        return collisionMap.writeToFile()
    }
}
