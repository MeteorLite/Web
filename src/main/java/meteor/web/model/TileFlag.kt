package meteor.web.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
class TileFlag(
    val x: Int?,
    val y: Int?,
    val z: Int?,
    val flag: Int?,
    val region: Int?,
    val regionX: Int?,
    val regionY: Int?,
)
