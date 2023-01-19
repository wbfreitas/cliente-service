package br.com.fiap.enginnering.clienteservice.controller;

import br.com.fiap.enginnering.clienteservice.dto.LoginDTO;
import br.com.fiap.enginnering.clienteservice.dto.TokenDTO;
import br.com.fiap.enginnering.clienteservice.item.PersonItem;
import br.com.fiap.enginnering.clienteservice.service.PersonService;
import br.com.fiap.enginnering.clienteservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/api/v1")
public class PersonController {

    @Autowired
    TokenService tokenService;

    @Autowired
    PersonService service;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO login) {
        return service.login(login);

    }
    @PostMapping
    public PersonItem create(@RequestBody @Validated PersonItem item)  {
       return service.save(item);
    }


    @GetMapping
    public PersonItem get(@RequestHeader (name="Authorization") String token) {
        return service.getUserByToken(token);
    }

    @GetMapping("/{id}")
    public PersonItem getById(@PathVariable String id) throws ChangeSetPersister.NotFoundException {
        return service.getUserById(id);
    }

    @PostMapping("/users-by-ids")
    public Iterable<PersonItem> listByIDs(@RequestBody List<String> ids) {
        return service.getUserbyId(ids);
    }

   @GetMapping("/consultoras-de-amamentacao")
    public Iterable<PersonItem> getExperts() {
    return service.getExperts();
    }

    @GetMapping
    @RequestMapping("/health")
    public String health() {
        return "health";
    }

}

