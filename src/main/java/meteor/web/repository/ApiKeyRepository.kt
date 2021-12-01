package meteor.web.repository

import meteor.web.model.ApiKey
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ApiKeyRepository : JpaRepository<ApiKey, String> {
    fun findFirstByToken(token: String): Optional<ApiKey>
}
