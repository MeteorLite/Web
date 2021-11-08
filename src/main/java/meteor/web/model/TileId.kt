package meteor.web.model

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class TileId(
    var x: Int,
    var y: Int,
    var z: Int
) : Serializable
