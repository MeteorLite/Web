package meteor.web.security

import meteor.web.repository.ApiKeyRepository
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
    private val apiKeyRepository: ApiKeyRepository
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        val filter = ApiKeyFilter(apiKeyRepository)
        filter.setAuthenticationManager {
            if (it.principal == null) {
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
            .antMatchers(HttpMethod.POST, "/transports").authenticated()
            .antMatchers("/regions/instance/**").authenticated()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .and()
            .addFilter(filter)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS )
    }
}
