package br.com.sicredi.vote.controller;

import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixPutRequestDTO;
import br.com.sicredi.vote.dto.PixPutResponseDTO;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.service.ChavePixService;
import br.com.sicredi.vote.service.ContaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/pix")
@AllArgsConstructor
public class PixController {

    private final ChavePixService chavePixService;
    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<UUID> addPix(@Valid @RequestBody PixPostRequestDTO pixPostRequestDTO) throws PixException{
        return ResponseEntity.ok(chavePixService.addPix(pixPostRequestDTO));
    }

    @PutMapping
    public ResponseEntity<PixPutResponseDTO> addPix(@Valid @RequestBody PixPutRequestDTO putRequestDTO) throws PixException{
        return ResponseEntity.ok(contaService.atualizaConta(putRequestDTO));
    }

}
