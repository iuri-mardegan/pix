package br.com.sicredi.vote.service;

import br.com.sicredi.vote.dto.PixGetResponseRequestDTO;
import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.enums.StatusChave;
import br.com.sicredi.vote.dto.enums.TipoChave;
import br.com.sicredi.vote.dto.enums.TipoConta;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.repository.ChavePixRepository;
import br.com.sicredi.vote.service.interfaces.ChavePixService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PixServiceTest {

    @Autowired
    private ChavePixService chavePixService;

    @MockBean
    private ChavePixRepository chavePixRepository;

    @Test
    public void addPixCPFTeste() throws PixException {
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(new ChavePix());
        chavePixService.addPix(pixPostRequestCPFDTOMock());
    }

    @Test
    public void addPixCNPJTeste() throws PixException {
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(new ChavePix());
        chavePixService.addPix(pixPostRequestCNPJDTOMock());
    }

    @Test
    public void addPixEmailTeste() throws PixException {
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(new ChavePix());
        chavePixService.addPix(pixPostRequestEmailDTOMock());
    }

    @Test
    public void addPixErrorTeste() throws PixException {
        when(chavePixRepository.save(any(ChavePix.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(PixException.class, () -> {
            chavePixService.addPix(pixPostRequestEmailDTOMock());
        });
    }

    @Test
    public void addPixError2Teste() throws PixException {
        List<ChavePix> list = new ArrayList<>();
        list.add(new ChavePix());
        list.add(new ChavePix());
        list.add(new ChavePix());
        list.add(new ChavePix());
        list.add(new ChavePix());
        list.add(new ChavePix());
        when(chavePixRepository.findByConta(any(Conta.class))).thenReturn(list);
        assertThrows(PixException.class, () -> {
            chavePixService.addPix(pixPostRequestEmailDTOMock());
        });
    }

    @Test
    public void addPixError3Teste() throws PixException {
        assertThrows(PixException.class, () -> {
            chavePixService.addPix(pixPostRequestEmailDTOMock());
        });
    }

    @Test
    public void addPixError4Teste() throws PixException {
        when(chavePixRepository.save(any(ChavePix.class))).thenThrow(Exception.class);
        assertThrows(PixException.class, () -> {
            chavePixService.addPix(pixPostRequestErroDTOMock());
        });
    }

    @Test
    public void addPixError5Teste() throws PixException {
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(new ChavePix());
        assertThrows(PixException.class, () -> {
            chavePixService.addPix(pixPostRequestErro2DTOMock());
        });
    }

    @Test
    public void consultaPorIdTeste() throws PixException {
        when(chavePixRepository.findById(any(UUID.class))).thenReturn(Optional.of(chavePixMock()));
        chavePixService.consultaPorId(UUID.randomUUID());
    }

    @Test
    public void consultaTeste() throws PixException {
        List<ChavePix> chavePixList = new ArrayList<>();
        chavePixList.add(chavePixMock());
        when(chavePixRepository.findByFilter(any(), anyString(),
                any(), any(),
                any(), any(),
                anyString(), anyString())).thenReturn(chavePixList);
        chavePixService.consulta(pixGetResponseRequestDTOMock());
    }

    @Test
    public void inativaChavePixTeste() throws PixException {
        when(chavePixRepository.findById(any(UUID.class))).thenReturn(Optional.of(chavePixMock()));
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(new ChavePix());
        chavePixService.inativaChavePix(UUID.randomUUID());
    }

    private PixGetResponseRequestDTO pixGetResponseRequestDTOMock() {
        return PixGetResponseRequestDTO.builder()
                .tipoChave(TipoChave.CNPJ)
                .valChave("82836894000119")
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan").build();
    }

    private PixPostRequestDTO pixPostRequestCPFDTOMock() {
        return PixPostRequestDTO.builder()
                .tipoChave(TipoChave.CPF)
                .valChave("48893784050")
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan").build();
    }

    private PixPostRequestDTO pixPostRequestCNPJDTOMock() {
        return PixPostRequestDTO.builder()
                .tipoChave(TipoChave.CNPJ)
                .valChave("82836894000119")
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan").build();
    }

    private PixPostRequestDTO pixPostRequestErroDTOMock() {
        return PixPostRequestDTO.builder()
                .valChave("abc@abc.com")
                .tipoChave(TipoChave.TELEFONE)
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan").build();
    }

    private PixPostRequestDTO pixPostRequestErro2DTOMock() {
        return PixPostRequestDTO.builder()
                .tipoChave(TipoChave.CPF)
                .valChave("abc@abc.com")
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan").build();
    }

    private PixPostRequestDTO pixPostRequestEmailDTOMock() {
        return PixPostRequestDTO.builder()
                .tipoChave(TipoChave.EMAIL)
                .valChave("abc@abc.com")
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan").build();
    }

    private ChavePix chavePixMock() {
        return ChavePix.builder()
                .tipoChave(TipoChave.CNPJ)
                .valorChave("82836894000119")
                .statusChave(StatusChave.ATIVO)
                .dataCadastro(Calendar.getInstance())
                .conta(Conta.builder()
                        .tipoConta(TipoConta.CORRENTE)
                        .numAgencia(1234L)
                        .numConta(12345678L)
                        .nomeCorrentista("Iuri")
                        .sobrenomeCorrentista("Mardegan")
                        .build())
                .build();
    }
}