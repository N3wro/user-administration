package htl.stp.at.userverwaltung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * Entwicklung:
 * standard
 * standard mit eigenem User
 * basicAuth
 * rest - nur read geht
 * csrf disable
 * role
 * authority
 * preAuthorize
 * Cross Site Request Forgery
 * csrf disable nur für pure services ohne browser
 * basic -> formLogin
 * remember-me (Cookies löschen)
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Zuständig für das Authorisieren - darf der User auf /whatever?
     *
     * @param http
     * @return
     * @throws Exception
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return custom(http);
    }


    private SecurityFilterChain custom(HttpSecurity http) throws Exception {


        var h2Matcher = AntPathRequestMatcher.antMatcher("/h2-console/**");
        http.csrf().disable()
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/", "/home", "/styles/**",  "/h2-console/**").permitAll()
                                .requestMatchers(POST, "/admin/**").permitAll()
                                .requestMatchers(POST, "/user/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("admin")
                                .anyRequest().authenticated())

                .formLogin().successHandler(myRedirectHandler())
                .and()

                .headers().frameOptions().disable();



        return http.build();
    }

    @Bean
    public RedirectHandler myRedirectHandler() {
        return new RedirectHandler();
    }



}