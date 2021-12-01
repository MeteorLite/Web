package meteor.web.security

import meteor.web.repository.ApiKeyRepository
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class ApiKeyFilter(
    val apiKeyRepository: ApiKeyRepository
) : AbstractPreAuthenticatedProcessingFilter() {
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any? {
        val header = request.getHeader("api-key")
        if (header == null || !apiKeyRepository.findFirstByToken(header).isPresent) {
            return null
        }

        return header
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any {
        return ""
    }
}
