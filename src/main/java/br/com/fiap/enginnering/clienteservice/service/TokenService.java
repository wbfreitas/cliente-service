package br.com.fiap.enginnering.clienteservice.service;

import br.com.fiap.enginnering.clienteservice.item.PersonItem;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenService {
    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {


        try {
            PersonItem user = (PersonItem) authentication.getPrincipal();

            Date now = new Date();
            Date exp = new Date(now.getTime() + Long.parseLong(expiration));

            String token = Jwts.builder().setIssuer("clienteservice")
                    .setSubject(user.getId())
                    .setIssuedAt(new Date())
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, secret).compact();
            return token;
        }catch (Exception e) {
            return "";
        }

    }

    public boolean isTokenValid(String token) {
        if(token == null) return false;
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public  String getTokenId(String token) {
        token = token.replace("Bearer ", "");
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return body.getSubject();
    }
}
