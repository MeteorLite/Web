package meteor.web.service

import meteor.web.collisions.CollisionMap
import meteor.web.collisions.Constants
import meteor.web.model.MappedTile
import meteor.web.model.TileFlag
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import java.nio.file.Files

@Service
class RegionService(
    val collisionMap: CollisionMap
) {
    fun save(tiles: List<TileFlag>) {
        tiles.forEach { tileFlag ->
            val region = tileFlag.region ?: return@forEach
            val x = tileFlag.x ?: return@forEach
            val y = tileFlag.y ?: return@forEach
            val z = tileFlag.z ?: return@forEach

            if (collisionMap.regions[region] == null) {
                collisionMap.createRegion(region)
            }

            if (tileFlag.isObstacle()) {
                collisionMap.set(x, y, z, 0, false)
                collisionMap.set(x, y, z, 1, false)
            } else {
                collisionMap.set(x, y, z, 0, true)
                collisionMap.set(x, y, z, 1, true)

                if (!tileFlag.north) {
                    collisionMap.set(x, y, z, 0, false)
                }

                if (!tileFlag.east) {
                    collisionMap.set(x, y, z, 1, false)
                }
            }
        }

        collisionMap.writeToFile().also { newFile ->
            Files.write(newFile.toPath(), newFile.readBytes())
        }
    }

    fun getFile() = FileSystemResource("./regions")

    fun getUnmapped() = collisionMap.regions.withIndex().filter { it.value == null }.map { it.index }

    fun getMappedTiles(region: Int, z: Int): List<MappedTile> {
        val regionBits = collisionMap.regions[region] ?: return emptyList()
        val out = mutableListOf<MappedTile>()
        repeat(Constants.REGION_SIZE) { x ->
            repeat(Constants.REGION_SIZE) { y ->
                val worldX = ((region ushr 8) shl 6) + x
                val worldY = ((region and 0xFF) shl 6) + y
                val north = regionBits.get(x, y, z, 0)
                val east = regionBits.get(x, y, z, 1)
                out.add(MappedTile(
                    worldX, worldY, z, north, east
                ))
            }
        }

        return out
    }

    fun getMappedTiles(region: Int): List<MappedTile> {
        val out = mutableListOf<MappedTile>()
        repeat(Constants.PLANE_SIZE) {
            out.addAll(getMappedTiles(region, it))
        }

        return out
    }

    fun deleteInstancedRegion(region: Int) {
        collisionMap.regions[region] = null
    }
}
