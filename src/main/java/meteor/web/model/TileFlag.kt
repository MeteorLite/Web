package meteor.web.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(TileId::class)
class TileFlag(
    @Id val x: Int,
    @Id val y: Int,
    @Id val z: Int,
    val flag: Int,
    val region: Int,
    val regionX: Int,
    val regionY: Int
)
