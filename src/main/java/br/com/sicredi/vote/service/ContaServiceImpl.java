package br.com.sicredi.vote.service;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixPutRequestDTO;
import br.com.sicredi.vote.dto.PixPutResponseDTO;
import br.com.sicredi.vote.dto.enums.StatusChave;
import br.com.sicredi.vote.dto.enums.TipoPessoaConta;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.repository.ChavePixRepository;
import br.com.sicredi.vote.repository.ContaRepository;
import br.com.sicredi.vote.service.interfaces.ContaService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;
    private final ChavePixRepository chavePixRepository;

    @Override
    public Conta insereOuBuscaConta(PixPostRequestDTO pixRequestDTO) throws PixException {
        Optional<Conta> contaOptional = contaRepository.findOne(Example.of(Conta.builder().numConta(pixRequestDTO.getNumConta()).numAgencia(pixRequestDTO.getNumAgencia()).build()));

        Conta conta;

        if (!contaOptional.isPresent()) {
            try {
                conta = contaRepository.save(Conta.builder()
                        .numAgencia(pixRequestDTO.getNumAgencia())
                        .numConta(pixRequestDTO.getNumConta())
                        .nomeCorrentista(pixRequestDTO.getNomeCorrentista())
                        .sobrenomeCorrentista(pixRequestDTO.getSobrenomeCorrentista())
                        .tipoConta(pixRequestDTO.getTipoConta())
                        .tipoPessoaConta(isNull(pixRequestDTO.getTipoPessoaConta()) ? TipoPessoaConta.PF : pixRequestDTO.getTipoPessoaConta())
                        .build());

            } catch (DataIntegrityViolationException e) {
                throw new PixException("Esta conta ja existe");
            }
        } else {
            conta = contaOptional.get();
        }

        return conta;
    }

    @Override
    public PixPutResponseDTO atualizaConta(PixPutRequestDTO putRequestDTO) throws PixException {
        Optional<ChavePix> chave = chavePixRepository.findById(putRequestDTO.getId());

        Conta conta;

        if (chave.isPresent() && chave.get().getStatusChave().equals(StatusChave.ATIVO)) {
            try {
                conta = chave.get().getConta();
                conta.setNumAgencia(putRequestDTO.getNumAgencia());
                conta.setNumConta(putRequestDTO.getNumConta());
                conta.setNomeCorrentista(putRequestDTO.getNomeCorrentista());
                conta.setSobrenomeCorrentista(putRequestDTO.getSobrenomeCorrentista());
                conta.setTipoConta(putRequestDTO.getTipoConta());
                conta = contaRepository.save(conta);
            } catch (DataIntegrityViolationException e) {
                throw new PixException("Campos de conta preenchidos incorretamente");
            }
        } else {
            throw new PixException("Conta nao encontrada!");
        }

        return contaParaPixPutResponseDTO(conta, chave.get());
    }

    private PixPutResponseDTO contaParaPixPutResponseDTO(Conta conta, ChavePix chavePix) {
        return PixPutResponseDTO.builder()
                .id(chavePix.getId())
                .numConta(conta.getNumConta())
                .numAgencia(conta.getNumAgencia())
                .sobrenomeCorrentista(conta.getSobrenomeCorrentista())
                .nomeCorrentista(conta.getNomeCorrentista())
                .tipoConta(conta.getTipoConta())
                .valorChave(chavePix.getValorChave())
                .tipoChave(chavePix.getTipoChave())
                .dataHoraCadastro(chavePix.getDataCadastro())
                .build();
    }

}
