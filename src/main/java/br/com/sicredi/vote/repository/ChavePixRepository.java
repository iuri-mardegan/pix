package br.com.sicredi.vote.repository;

import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.model.Conta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChavePixRepository extends CrudRepository<ChavePix, UUID> {

    List<ChavePix> findByConta(Conta conta);

}
