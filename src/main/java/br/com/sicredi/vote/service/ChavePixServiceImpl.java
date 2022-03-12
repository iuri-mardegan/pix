package br.com.sicredi.vote.service;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixResponseRequestDTO;
import br.com.sicredi.vote.dto.enums.StatusChave;
import br.com.sicredi.vote.dto.enums.TipoChave;
import br.com.sicredi.vote.dto.enums.TipoConta;
import br.com.sicredi.vote.dto.enums.TipoPessoaConta;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.repository.ChavePixRepository;
import br.com.sicredi.vote.service.interfaces.ChavePixService;
import br.com.sicredi.vote.service.interfaces.ContaService;
import br.com.sicredi.vote.util.Validate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ChavePixServiceImpl implements ChavePixService {

    private final ChavePixRepository chavePixRepository;
    private final ContaService contaService;

    @Override
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

    @Override
    public PixResponseRequestDTO consultaPorId(UUID id) throws PixException {
        Optional<ChavePix> chaveOptional = chavePixRepository.findById(id);

        if (chaveOptional.isPresent()) {
            ChavePix chave = chaveOptional.get();
            return converteParaPixResponse(chave);
        } else {
            throw new PixException("Chave nao encontrada.");
        }
    }

    @Override
    public PixResponseRequestDTO inativaChavePix(UUID id) throws PixException {
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
        return converteParaPixResponse(chave);
    }

    private PixResponseRequestDTO converteParaPixResponse(ChavePix chave) {
        return PixResponseRequestDTO.builder()
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

    @Override
    public List<PixResponseRequestDTO> consulta(PixResponseRequestDTO pixResponseRequestDTO) throws PixException {

        List<PixResponseRequestDTO> list = new ArrayList<>();
        chavePixRepository.findByFilter(pixResponseRequestDTO.getTipoChave(),
                pixResponseRequestDTO.getValChave(),
                pixResponseRequestDTO.getDataHoraCadastro(),
                pixResponseRequestDTO.getDataHoraInativacao(),
                pixResponseRequestDTO.getNumAgencia(),
                pixResponseRequestDTO.getNumConta(),
                pixResponseRequestDTO.getNomeCorrentista(),
                pixResponseRequestDTO.getSobrenomeCorrentista()).forEach(chavePix -> list.add(PixResponseRequestDTO.builder()
                                                                                            .id(chavePix.getId())
                                                                                            .tipoChave(chavePix.getTipoChave())
                                                                                            .valChave(chavePix.getValorChave())
                                                                                            .tipoConta(chavePix.getConta().getTipoConta())
                                                                                            .numAgencia(chavePix.getConta().getNumAgencia())
                                                                                            .numConta(chavePix.getConta().getNumConta())
                                                                                            .nomeCorrentista(chavePix.getConta().getNomeCorrentista())
                                                                                            .sobrenomeCorrentista(chavePix.getConta().getSobrenomeCorrentista())
                                                                                            .dataHoraCadastro(chavePix.getDataCadastro())
                                                                                            .dataHoraInativacao(chavePix.getDataInativacao())
                                                                                            .build()));
        return list;
    }
}
