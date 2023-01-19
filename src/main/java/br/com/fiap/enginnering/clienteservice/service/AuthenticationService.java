package br.com.fiap.enginnering.clienteservice.service;

import br.com.fiap.enginnering.clienteservice.item.PersonItem;
import br.com.fiap.enginnering.clienteservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    PersonRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            Optional<PersonItem> item = repository.findByEmail(username);
            if(item.isPresent()) {
                return item.get();
            }
        throw new UsernameNotFoundException("Senha ou usuario invalido");
    }
}
