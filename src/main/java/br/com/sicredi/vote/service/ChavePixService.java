package br.com.sicredi.vote.service;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
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
}
