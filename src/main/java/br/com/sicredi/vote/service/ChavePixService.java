package br.com.sicredi.vote.service;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixResponseDTO;
import br.com.sicredi.vote.dto.StatusChave;
import br.com.sicredi.vote.dto.TipoPessoaConta;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.repository.ChavePixRepository;
import br.com.sicredi.vote.util.Validate;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChavePixService {

    private final ChavePixRepository chavePixRepository;
    private final ContaService contaService;

    public UUID addPix(PixPostRequestDTO pixRequestDTO) throws PixException {

        Conta conta = contaService.insereOuBuscaConta(pixRequestDTO);

        return persistChavePix(pixRequestDTO, conta);
    }

    private UUID persistChavePix(PixPostRequestDTO pixRequestDTO, Conta conta) throws PixException {
        validaChave(pixRequestDTO, conta);

        try {
            return chavePixRepository.save(ChavePix.builder()
                    .tipoChave(pixRequestDTO.getTipoChave())
                    .valorChave(pixRequestDTO.getValChave())
                    .conta(conta)
                    .dataCadastro(Calendar.getInstance())
                    .statusChave(StatusChave.ATIVO)
                    .build()).getId();

        } catch (DataIntegrityViolationException e) {
            throw new PixException("Esta Chave ja esta em uso.");
        } catch (Exception e) {
            throw new PixException(e.getMessage());
        }
    }

    private void validaChave(PixPostRequestDTO pixRequestDTO, Conta conta) throws PixException {
        if ((conta.getTipoPessoaConta().equals(TipoPessoaConta.PF) && chavePixRepository.findByConta(conta).size() >= 5)
                || (conta.getTipoPessoaConta().equals(TipoPessoaConta.PJ) && chavePixRepository.findByConta(conta).size() >= 20))
            throw new PixException("Limite de chave excedido!");

        switch (pixRequestDTO.getTipoChave()) {
            case CPF:
                Validate.validaCpf(pixRequestDTO.getValChave());
                break;
            case CNPJ:
                Validate.isCNPJ(pixRequestDTO.getValChave());
                break;
            case EMAIL:
                Validate.isValidEmailAddress(pixRequestDTO.getValChave());
                break;
            default:
                throw new PixException("Tipo de chave invalida!");
        }
    }

    public PixResponseDTO consultaPorId(UUID id) throws PixException {
        Optional<ChavePix> chaveOptional = chavePixRepository.findById(id);

        if (chaveOptional.isPresent()) {
            ChavePix chave = chaveOptional.get();
            return PixResponseDTO.builder()
                    .dataHoraCadastro(chave.getDataCadastro())
                    .dataHoraInativacao(chave.getDataInativacao())
                    .id(chave.getId())
                    .numAgencia(chave.getConta().getNumAgencia())
                    .numConta(chave.getConta().getNumConta())
                    .nomeCorrentista(chave.getConta().getNomeCorrentista())
                    .sobrenomeCorrentista(chave.getConta().getSobrenomeCorrentista())
                    .valChave(chave.getValorChave())
                    .tipoConta(chave.getConta().getTipoConta())
                    .tipoChave(chave.getTipoChave())
                    .build();
        } else {
            throw new PixException("Chave nao encontrada.");
        }
    }

    public PixResponseDTO inativaChavePix(UUID id) throws PixException {
        Optional<ChavePix> chaveOptional = chavePixRepository.findById(id);

        ChavePix chave;
        if (chaveOptional.isPresent() && chaveOptional.get().getStatusChave().equals(StatusChave.ATIVO)) {
            chave = chaveOptional.get();
            chave.setStatusChave(StatusChave.INATIVO);
            chave.setDataInativacao(Calendar.getInstance());
            chavePixRepository.save(chave);
        } else {
            throw new PixException("Chave invalida para inativacao.");
        }
        return PixResponseDTO.builder()
                .dataHoraCadastro(chave.getDataCadastro())
                .dataHoraInativacao(chave.getDataInativacao())
                .id(chave.getId())
                .numAgencia(chave.getConta().getNumAgencia())
                .numConta(chave.getConta().getNumConta())
                .nomeCorrentista(chave.getConta().getNomeCorrentista())
                .sobrenomeCorrentista(chave.getConta().getSobrenomeCorrentista())
                .valChave(chave.getValorChave())
                .tipoConta(chave.getConta().getTipoConta())
                .tipoChave(chave.getTipoChave())
                .build();
    }

}
