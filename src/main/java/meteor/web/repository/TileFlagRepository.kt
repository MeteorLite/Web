package meteor.web.repository

import meteor.web.model.TileFlag
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface RegionRepository : MongoRepository<TileFlag, Long> {
    fun findByRegion(regionId: Int): List<TileFlag>

    fun findFirstByXAndYAndZ(x: Int, y: Int, z: Int): Optional<TileFlag>
}
