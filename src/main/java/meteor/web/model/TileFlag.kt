package meteor.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class TileFlag(
    @JsonProperty @Id val regionCoords: String,
    @JsonProperty val x: Int,
    @JsonProperty val y: Int,
    @JsonProperty val z: Int,
    @JsonProperty val flag: Int,
    @JsonProperty val region: Int,
)
