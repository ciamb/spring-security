package ciamb.demo.springsecuritybe.config.security;

import ciamb.demo.springsecuritybe.config.security.jwt.AuthEntryPointJwt;
import ciamb.demo.springsecuritybe.config.security.jwt.AuthTokenFilter;
import ciamb.demo.springsecuritybe.config.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;

    /*
    * SecurityFilterChain ha sostituito il metodo @Override configure() a partire da Spring 2.7
    * -> Component Based Approach.
    * Il metodo securityFilterChain() definisce come gestire le richieste HTTP per le risorse protette.
    * In questo caso, la maggior parte delle richieste richiede un'utenticazione valida,
    * tranne quelle per la registrazione e l'autenticazione.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/v1/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        httpSecurity.authenticationProvider(authenticationProvider());

        httpSecurity.addFilterBefore(authenticationJwtTokeFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    /*
    * Il metodo authenticationJwtTokeFilter() crea un nuovo filtro
    * che verifica la presenza e la validità di un token JWT.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokeFilter() {
        return new AuthTokenFilter();
    }

    /*
    * Il metodo authenticationProvider() definisce un provider di autenticazione
    * basato su userDetailsService e passwordEncoder().
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    /*
    * Il metodo passwordEncoder() restituisce un PasswordEncoder
    * che viene utilizzato per codificare le password degli utenti.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/js/**", "/images/**");
    }

}
