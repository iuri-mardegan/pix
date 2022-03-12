package br.com.sicredi.vote.service.interfaces;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixPutRequestDTO;
import br.com.sicredi.vote.dto.PixPutResponseDTO;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.model.Conta;

public interface ContaService {
    Conta insereOuBuscaConta(PixPostRequestDTO pixRequestDTO) throws PixException;
    PixPutResponseDTO atualizaConta(PixPutRequestDTO putRequestDTO) throws PixException;
}
