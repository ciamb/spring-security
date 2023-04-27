package ciamb.demo.springsecuritybe.config.security.jwt;

import ciamb.demo.springsecuritybe.config.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${springsecuritydemo.app.jwtSecret}")
    private String jwtSecret;

    @Value("${springsecuritydemo.app.jwtExpirationMs}")
    private String jwtExpirationMs;

    @Value("${springsecuritydemo.app.jwtCookieName}")
    private String jwtCookieName;

    /*
     * generateTokenFromUsername(String username): questo metodo accetta uno username e genera un nuovo token JWT
     * che scade dopo un tempo specificato dalla proprietà jwtExpirationMs.
     * Il token JWT viene firmato con l'algoritmo di firma HS512
     * e la chiave segreta specificata nella proprietà jwtSecret.
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + Long.parseLong(jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    /*
    * getJwtFromCookies(HttpServletRequest request): questo metodo accetta un oggetto HttpServletRequest e
    * cerca un cookie JWT utilizzando il nome del cookie specificato nella proprietà jwtCookieName.
    * Se trova il cookie, restituisce il valore del cookie come stringa; altrimenti, restituisce null.
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }


    /*
    * generateJwtCookie(UserDetailsImpl userPrincipal): questo metodo accetta un oggetto UserDetailsImpl,
    * che rappresenta le informazioni di autenticazione dell'utente, e genera un nuovo cookie HTTP
    * che contiene un token JWT firmato. Il cookie ha il nome specificato nella proprietà jwtCookieName,
    * una durata di 24 ore e un flag HttpOnly impostato su true per proteggere il cookie da attacchi XSS.
     */
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, jwt).path("/api").maxAge(24*60*60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie
                .from(jwtCookieName,null)
                .path("/api")
                .build();
    }

    public String getUserFromJwtToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /*
    * validateJwtToken(String authToken): questo metodo accetta un token JWT e
    * verifica se il token è valido e non è scaduto. Restituisce true se il token è valido;
    * altrimenti, restituisce false e stampa un messaggio di log per segnalare l'errore.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken);

            return true;
        } catch (Exception e) {
            System.out.println("ci andranno i vari log");
        }
        return false;
    }

}
