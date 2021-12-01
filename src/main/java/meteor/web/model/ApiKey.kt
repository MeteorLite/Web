package meteor.web.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ApiKey(
    @Id
    val discord: String,
    val token: String
)
