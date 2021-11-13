package meteor.web.repository

import meteor.web.model.TileFlag
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*
import java.util.stream.Stream

interface RegionRepository : MongoRepository<TileFlag, Long> {
    fun findBy(): Stream<TileFlag>

    fun findByRegion(regionId: Int): List<TileFlag>

    fun findFirstByXAndYAndZ(x: Int, y: Int, z: Int): Optional<TileFlag>
}
