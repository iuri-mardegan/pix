package br.com.sicredi.vote.service;

import br.com.sicredi.vote.dto.PixRequestDTO;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.repository.ContaRepository;
import br.com.sicredi.vote.repository.ChavePixRepository;
import br.com.sicredi.vote.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ChavePixService {

    @Autowired
    private ChavePixRepository chavePixRepository;
    @Autowired
    private ContaRepository contaRepository;

    public void addPix(PixRequestDTO pixRequestDTO) throws PixException {
        validaChave(pixRequestDTO);

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
                        .build());

            } catch (DataIntegrityViolationException e) {
                throw new PixException("Campos de conta preenchidos incorretamente");
            }
        } else {
            conta = contaOptional.get();
        }

        if (chavePixRepository.findByConta(conta).size() >= 5)
            throw new PixException("Limite de chave excedido!");

        try {
            chavePixRepository.save(ChavePix.builder()
                    .tipoChave(pixRequestDTO.getTipoChave())
                    .valorChave(pixRequestDTO.getValChave())
                    .conta(conta)
                    .dataCadastro(Calendar.getInstance())
                    .build());

        } catch (DataIntegrityViolationException e) {
            throw new PixException("Campos de chave preenchidos incorretamente");
        } catch (Exception e) {
            throw new PixException(e.getMessage());
        }
    }

    private void validaChave(PixRequestDTO pixRequestDTO) throws PixException {
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
