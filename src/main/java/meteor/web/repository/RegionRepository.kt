package meteor.web.repository

import meteor.web.model.TileId
import meteor.web.model.TileFlag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface RegionRepository : JpaRepository<TileFlag, TileId>
