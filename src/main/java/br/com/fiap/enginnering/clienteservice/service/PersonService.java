package br.com.fiap.enginnering.clienteservice.service;

import br.com.fiap.enginnering.clienteservice.dto.LoginDTO;
import br.com.fiap.enginnering.clienteservice.dto.TokenDTO;
import br.com.fiap.enginnering.clienteservice.item.PersonItem;
import br.com.fiap.enginnering.clienteservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public PersonItem save(PersonItem item) {
        item.setSenha(passwordEncoder.encode(item.getSenha()));
       return repository.save(item);
    }

    public TokenDTO login(LoginDTO login) {
            String a = new BCryptPasswordEncoder().encode(login.getSenha());
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
            Authentication auth = authenticationManager.authenticate(user);

            return TokenDTO
                    .builder()
                    .type("Bearer")
                    .token(tokenService.generateToken(auth))
                    .build();
    }

    public PersonItem getUserByToken(String token) {
        String idUser = tokenService.getTokenId(token);
         return repository.findById(idUser).get();
    }

    public PersonItem getUserById(String id) throws ResponseStatusException {
        Optional<PersonItem> user = repository.findById(id);
       return user.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario nao encontrado"
                ));

    }

    public Iterable<PersonItem> getUserbyId(List<String> ids) {
        return repository.findAllById(ids);
    }

    public Iterable<PersonItem> getExperts() {
        return repository.findExperts();
    }
}
