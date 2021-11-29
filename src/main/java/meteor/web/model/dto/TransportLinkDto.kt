package meteor.web.model.dto

import meteor.web.model.TransportLink

data class TransportLinkDto(
    val source: String,
    val destination: String,
    val action: String,
    val objName: String,
    val objId: Int
) {
    constructor(transportLink: TransportLink) : this(
        transportLink.source,
        transportLink.destination,
        transportLink.action,
        transportLink.objName,
        transportLink.objId
    )

    override fun toString(): String {
        return "$source $destination $action $objName $objId"
    }
}
