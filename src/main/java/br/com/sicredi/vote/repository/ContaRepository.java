package br.com.sicredi.vote.repository;

import br.com.sicredi.vote.model.Conta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ContaRepository extends CrudRepository<Conta, UUID>, QueryByExampleExecutor<Conta> {

}
