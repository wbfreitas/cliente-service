package br.com.fiap.enginnering.clienteservice.config;

import br.com.fiap.enginnering.clienteservice.item.PersonItem;
import br.com.fiap.enginnering.clienteservice.repository.PersonRepository;
import br.com.fiap.enginnering.clienteservice.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class TokenAuthenticationFilter  extends OncePerRequestFilter {
    PersonRepository repository;
    TokenService tokenService;

    public TokenAuthenticationFilter(PersonRepository repository, TokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenFromHeader = getTokenFromHeader(request);
        boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
        if(tokenValid) {
            this.authenticate(tokenFromHeader);
        }


        filterChain.doFilter(request, response);

    }

    private void authenticate(String tokenFromHeader) {
        String id = tokenService.getTokenId(tokenFromHeader);

        Optional<PersonItem> person = repository.findById(id);

        if(person.isPresent()) {
            PersonItem  user= person.get();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }


}
