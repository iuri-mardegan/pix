package br.com.sicredi.vote.service.interfaces;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixResponseRequestDTO;
import br.com.sicredi.vote.exception.PixException;

import java.util.List;
import java.util.UUID;

public interface ChavePixService {

    UUID addPix(PixPostRequestDTO pixRequestDTO) throws PixException;

    PixResponseRequestDTO consultaPorId(UUID id) throws PixException;

    List<PixResponseRequestDTO> consulta(PixResponseRequestDTO pixResponseRequestDTO) throws PixException;

    PixResponseRequestDTO inativaChavePix(UUID id) throws PixException;
}
