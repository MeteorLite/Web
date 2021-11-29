package meteor.web.model

import meteor.web.model.dto.TransportLinkDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class TransportLink(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val source: String?,
    val destination: String?,
    val action: String?,
    val objName: String?,
    val objId: Int?,
    val enabled: Boolean?
) {
    constructor(transportLink: TransportLinkDto) : this(
        null,
        transportLink.source,
        transportLink.destination,
        transportLink.action,
        transportLink.objName,
        transportLink.objId,
        false
    )
}
