package ciamb.demo.springsecuritybe.config.security.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* Questa classe Java rappresenta un entry point di autenticazione JWT (JSON Web Token)
* per le richieste HTTP non autorizzate.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /*
    * Il metodo commence() è il punto d'ingresso dell'autenticazione per le richieste HTTP non autorizzate.
    * Viene chiamato quando un utente tenta di accedere a una risorsa protetta
    * senza essere autenticato o senza avere le autorizzazioni necessarie.
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        /*
        * Il metodo sendError() invia una risposta HTTP con lo stato "401 Unauthorized" e un messaggio di errore.
        * Questo indica al client che l'accesso è stato negato perché non è stato fornito un token JWT valido
        * o perché non sono state fornite le credenziali di autenticazione corrette.
         */
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
