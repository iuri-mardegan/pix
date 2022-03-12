package br.com.sicredi.vote.controller;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixPutRequestDTO;
import br.com.sicredi.vote.dto.PixPutResponseDTO;
import br.com.sicredi.vote.dto.PixResponseRequestDTO;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.service.interfaces.ChavePixService;
import br.com.sicredi.vote.service.interfaces.ContaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/pix")
@AllArgsConstructor
public class PixController {

    private final ChavePixService chavePixService;
    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<UUID> addPix(@Valid @RequestBody PixPostRequestDTO pixPostRequestDTO) throws PixException {
        return ResponseEntity.ok(chavePixService.addPix(pixPostRequestDTO));
    }

    @PutMapping
    public ResponseEntity<PixPutResponseDTO> updatePix(@Valid @RequestBody PixPutRequestDTO putRequestDTO) throws PixException {
        return ResponseEntity.ok(contaService.atualizaConta(putRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PixResponseRequestDTO> deletePix(@PathVariable UUID id) throws PixException {
        return ResponseEntity.ok(chavePixService.inativaChavePix(id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PixResponseRequestDTO> consultaPorId(@PathVariable UUID id) throws PixException {
        return ResponseEntity.ok(chavePixService.consultaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PixResponseRequestDTO>> consultaPorId(@RequestBody PixResponseRequestDTO pixResponseRequestDTO) throws PixException {
        return ResponseEntity.ok(chavePixService.consulta(pixResponseRequestDTO));
    }

}
