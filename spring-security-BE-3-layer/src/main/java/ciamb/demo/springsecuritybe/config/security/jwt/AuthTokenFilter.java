package ciamb.demo.springsecuritybe.config.security.jwt;

import ciamb.demo.springsecuritybe.config.security.service.UserDetailsServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
* OncePerRequestFilter è una classe di base astratta che estende il filtro servlet standard di Java
* e assicura che il filtro venga eseguito solo una volta per ogni richiesta.
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /*
    * doFilterInternal() è il metodo principale in cui viene implementata la logica del filtro.
    * Esso viene chiamato una volta per ogni richiesta in cui il filtro viene applicato.
     */
    @Override
    protected void doFilterInternal(
            /*
            * HttpServletRequest e HttpServletResponse sono due oggetti che rappresentano rispettivamente
            * la richiesta HTTP in ingresso e la risposta HTTP in uscita.
             */
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken((jwt))) {
                String username = jwtUtils.getUserFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                /*
                * UsernamePasswordAuthenticationToken è un'istanza di autenticazione in cui
                * vengono forniti il nome utente, la password e l'elenco delle autorizzazioni.
                 */
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                /*
                * SecurityContextHolder è una classe di Spring Security che fornisce un'API
                * per accedere al contesto di sicurezza corrente.
                 */
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            System.out.println("// logger");
        }

        /*
        * FilterChain è un'interfaccia che consente di passare la richiesta e la risposta
        * corrente al filtro successivo nella catena di filtri.
         */
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        return jwtUtils
                .getJwtFromCookies(request);
    }
}
