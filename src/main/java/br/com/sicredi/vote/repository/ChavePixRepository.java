package br.com.sicredi.vote.repository;

import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.model.ChavePix;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChavePixRepository extends CrudRepository<ChavePix, Integer> {

    List<ChavePix> findByConta(Conta conta);

}
