package meteor.web.security

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class ApiKeyFilter(
    val header: String
) : AbstractPreAuthenticatedProcessingFilter() {
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any? {
        return request.getHeader(header)
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any {
        return ""
    }
}
