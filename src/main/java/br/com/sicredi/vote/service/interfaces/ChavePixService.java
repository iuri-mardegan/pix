package br.com.sicredi.vote.service.interfaces;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixGetResponseRequestDTO;
import br.com.sicredi.vote.exception.PixException;

import java.util.List;
import java.util.UUID;

public interface ChavePixService {

    UUID addPix(PixPostRequestDTO pixRequestDTO) throws PixException;

    PixGetResponseRequestDTO consultaPorId(UUID id) throws PixException;

    List<PixGetResponseRequestDTO> consulta(PixGetResponseRequestDTO pixResponseRequestDTO) throws PixException;

    PixGetResponseRequestDTO inativaChavePix(UUID id) throws PixException;
}
