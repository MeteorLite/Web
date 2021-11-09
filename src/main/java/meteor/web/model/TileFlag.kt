package meteor.web.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class TileFlag(
    @Id val regionCoords: String?,
    val x: Int?,
    val y: Int?,
    val z: Int?,
    val flag: Int?,
    val region: Int?,
)
