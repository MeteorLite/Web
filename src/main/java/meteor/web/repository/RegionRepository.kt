package meteor.web.repository

import meteor.web.model.TileId
import meteor.web.model.TileFlag
import org.springframework.data.jpa.repository.JpaRepository

interface RegionRepository : JpaRepository<TileFlag, TileId> {
    fun findByRegion(regionId: Int): List<TileFlag>
}
