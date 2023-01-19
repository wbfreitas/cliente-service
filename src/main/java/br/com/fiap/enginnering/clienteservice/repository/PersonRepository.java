package br.com.fiap.enginnering.clienteservice.repository;

import br.com.fiap.enginnering.clienteservice.item.PersonItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<PersonItem, String> {

    @Query("{email: '?0', senha: ?1}")
    public PersonItem login(String email, String senha);

    @Query("{email: '?0'}")
    Optional<PersonItem> findByEmail(String email);

    @Query("{mamae: false}")
    Iterable<PersonItem> findExperts();
}
