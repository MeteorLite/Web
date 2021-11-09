package meteor.web.repository

import meteor.web.model.TileFlag
import org.springframework.data.mongodb.repository.MongoRepository

interface RegionRepository : MongoRepository<TileFlag, Long> {
    fun findByRegion(regionId: Int): List<TileFlag>
}
