package br.com.sicredi.vote.repository;

import br.com.sicredi.vote.dto.enums.TipoChave;
import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.model.Conta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChavePixRepository extends CrudRepository<ChavePix, UUID> {

    List<ChavePix> findByConta(Conta conta);

    @Query("SELECT p FROM ChavePix p " +
            " JOIN p.conta c " +
            " WHERE 1=1 " +
            " AND (:tipoChave is null or p.tipoChave = :tipoChave) " +
            " AND (:valorChave is null or p.valorChave = :valorChave) " +
            " AND (:dataCadastro is null or p.dataCadastro = :dataCadastro) " +
            " AND (:dataInativacao is null or p.dataInativacao = :dataInativacao) " +
            " AND (:numAgencia is null or c.numAgencia = :numAgencia) " +
            " AND (:numConta is null or c.numConta = :numConta) " +
            " AND (:nomeCorrentista is null or c.nomeCorrentista like '%'+ :nomeCorrentista + '%') " +
            " AND (:sobrenomeCorrentista is null or c.sobrenomeCorrentista like '%'+ :sobrenomeCorrentista +'%' ) "
    )
    List<ChavePix> findByFilter(@Param("tipoChave") TipoChave tipoChave,
                                @Param("valorChave") String valorChave,
                                @Param("dataCadastro") Calendar dataCadastro,
                                @Param("dataInativacao") Calendar dataInativacao,
                                @Param("numAgencia") Long numAgencia,
                                @Param("numConta") Long numConta,
                                @Param("nomeCorrentista") String nomeCorrentista,
                                @Param("sobrenomeCorrentista") String sobrenomeCorrentista);

}
