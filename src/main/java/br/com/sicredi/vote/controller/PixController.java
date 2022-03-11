package br.com.sicredi.vote.controller;

import br.com.sicredi.vote.dto.PixRequestDTO;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.service.ChavePixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/pix")
public class PixController {

    @Autowired
    private ChavePixService chavePixService;


    @PostMapping
    public ResponseEntity<?> addPix(@Valid @RequestBody PixRequestDTO pixRequestDTO) throws PixException{
        chavePixService.addPix(pixRequestDTO);
        return ResponseEntity.ok("Chave Pix adicionada.");
    }

}
