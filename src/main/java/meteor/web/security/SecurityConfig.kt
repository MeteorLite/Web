package meteor.web.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${regions.api-key}") private val validApiKey: String,
    @Value("\${regions.api-key-header}") private val apiKeyHeader: String,
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        val filter = ApiKeyFilter(apiKeyHeader)
        filter.setAuthenticationManager {
            val principal = it.principal as String
            if (principal != validApiKey) {
                throw BadCredentialsException("Invalid api key")
            }

            it.also {
                it.isAuthenticated = true
            }
        }

        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/regions").authenticated()
            .antMatchers("/regions/instance/**").authenticated()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .and()
            .addFilter(filter)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS )
    }
}
